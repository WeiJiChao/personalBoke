package com.jwBlog.security.controller;

import com.jwBlog.frame.aop.Log;
import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.security.entity.vo.PassVo;
import com.jwBlog.security.service.SecurityUserService;
import com.jwBlog.security.utils.UserSystemUtils;
import com.jwBlog.shiro.UserUtils;
import com.jwBlog.utils.dp.MapUtils;
import com.jwBlog.utils.dp.StringUtils;
import com.jwBlog.utils.encrypt.AesEncryptUtil;
import com.jwBlog.utils.encrypt.DesJs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("api")
public class UserController {

    @Value("${server.url}")
    private String host;

    @Autowired
    private SecurityUserService securityUserService;
    private static final String ENTITY_NAME = "user";

    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUser(@PathVariable int id){
        return new ResponseEntity(securityUserService.queryObject(id), HttpStatus.OK);
    }

    @Log("查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(SecurityUserEntity userDTO, Pageable pageable){
        return new ResponseEntity(securityUserService.queryListByPage(MapUtils.objectToMap(userDTO),pageable), HttpStatus.OK);
    }

    @Log("新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SecurityUserEntity resources, HttpServletRequest request){
        if (resources.getId() != null) {
            throw new BadRequestException("新增用户不能含有ID数据");
        }
        if (!securityUserService.checkCode(0,resources.getCode())) {
            throw new BadRequestException("用户名重复");
        }
        SecurityUserEntity entity = securityUserService.save(resources);

        HashMap<String,String> map = new HashMap();
        map.put("code",resources.getCode());
        map.put("name",resources.getName());
        map.put("realName",resources.getRealName());
        map.put("status",resources.getActive());
        map.put("tenantId","0");
        UserSystemUtils.saveUser(host,map);

        return new ResponseEntity(entity, HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SecurityUserEntity resources, HttpServletRequest request){
        if (resources.getId() == null) {
            throw new BadRequestException("修改用户ID不能为空");
        }
        String userCode=securityUserService.queryObject(resources.getId()).getCode();
        if (!userCode.equals(resources.getCode())&&!securityUserService.checkCode(resources.getId(),resources.getCode())) {
            throw new BadRequestException("用户名重复");
        }
        securityUserService.update(resources);
        HashMap<String,String> map = new HashMap();
        map.put("code",resources.getCode());
        map.put("codeOld",userCode);
        map.put("name",resources.getName());
        map.put("realName",resources.getRealName());
        map.put("status",resources.getActive());

        UserSystemUtils.updateUser(host,map);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable int id, HttpServletRequest request){
        String userCode=securityUserService.queryObject(id).getCode();
        securityUserService.delete(id);
        UserSystemUtils.deleteUser(host,userCode);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证密码
     * @param pass
     * @return
     */
    @GetMapping(value = "/users/validPass/{pass}")
    public ResponseEntity validPass(@PathVariable String pass, HttpServletRequest request){
//        SecurityUserEntity user=securityUserService.queryObject(UserUtils.getCurrentUserId());
        Map map = new HashMap();
//        if(user.getPassword().equals(SecurityUserEntity.passwordSecurity(pass,user.getSalt()))){
        Integer tenantId=UserSystemUtils.checkUserPass(host,UserUtils.getCurrentUserCode(),pass);
        if(tenantId>-1){
            map.put("status",400);
        }else  map.put("status",200);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping(value = "/users/validPassQd")
    public ResponseEntity validPassQd(@Validated @RequestBody PassVo passVo, HttpServletRequest request){
        String pass= null;
        Map map = new HashMap();
        try {
            pass = AesEncryptUtil.desEncrypt(passVo.getPass());
            Integer tenantId=UserSystemUtils.checkUserPass(host,UserUtils.getCurrentUserCode(),pass);
            if(tenantId>-1){
                map.put("status",400);
            }else  map.put("status",200);
        } catch (Exception e) {
            map.put("status",200);
            map.put("message",e.getMessage());
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }
    /**
     * 修改密码
     * @param passVo
     * @return
     */
    @PostMapping(value = "/users/updatePass")
    public ResponseEntity updatePass(@RequestBody PassVo passVo, HttpServletRequest request) throws Exception {
        SecurityUserEntity user=securityUserService.queryObject(UserUtils.getCurrentUserId());
        String oldPass = AesEncryptUtil.desEncrypt(passVo.getPass());
        String newPass = AesEncryptUtil.desEncrypt(passVo.getNewPass());
        if (!user.getPassword().equals(SecurityUserEntity.passwordSecurity(oldPass, user.getSalt()))) {
            throw new BadRequestException("原密码不正确");
        }
        if (StringUtils.isEmpty(newPass)) {
            throw new BadRequestException("新密码不能为空");
        }
        if (user.getPassword().equals(SecurityUserEntity.passwordSecurity(newPass, user.getSalt()))) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        user.setNewPassword(newPass);
        securityUserService.updatePassword(user);

        UserSystemUtils.updatePass(host, UserUtils.getCurrentUserCode(), newPass);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 初始化密码
     * @param code
     * @return
     */
    @GetMapping(value = "/users/initPass/{code}")
    public ResponseEntity initPass(@PathVariable String code, HttpServletRequest request){
        securityUserService.resetPassWord(code);
        UserSystemUtils.resetPass(host,code);
        return new ResponseEntity(HttpStatus.OK);
    }
    /**
     * 初始化密码
     * @param users
     * @return
     */
    @PostMapping(value = "/users/initPassBatch")
    public ResponseEntity initPassBatch(@RequestBody String[] users, HttpServletRequest request){
        securityUserService.resetPassWord(users);
        UserSystemUtils.resetPass(host,StringUtils.join(",",users));
        return new ResponseEntity(HttpStatus.OK);
    }
}
