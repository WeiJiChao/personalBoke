package com.jwBlog.security.controller;

import com.jwBlog.frame.aop.Log;
import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.security.entity.SecurityModuleEntity;
import com.jwBlog.security.service.SecurityModuleService;
import com.jwBlog.shiro.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("api")
public class MenuController {

    @Autowired
    private SecurityModuleService securityModuleService;
    private static final String ENTITY_NAME = "menu";

    @GetMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(@PathVariable int id){
        return new ResponseEntity(securityModuleService.queryObject(id), HttpStatus.OK);
    }

    /**
     * 构建前端路由所需要的菜单
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity buildMenus(){
        List<SecurityModuleEntity> menuList=securityModuleService.queryMenuListUser( UserUtils.getCurrentUserId());
        return new ResponseEntity(securityModuleService.buildMenus(menuList), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenuTree(){
        List<SecurityModuleEntity> menuList=securityModuleService.queryListParentId(0);
//        Map map = new HashMap();
//        map.put("content",menuList);
//        map.put("totalElements",menuList.size());
        return new ResponseEntity(securityModuleService.buildTree(menuList), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(@RequestParam(required = false) String name){
        HashMap<String,Object> mapParam=new HashMap<String,Object>();
        mapParam.put("name",name==null||"null".equals(name)?"":name);
        List<SecurityModuleEntity> menuList=securityModuleService.queryList(mapParam);

//        Map map = new HashMap();
//        map.put("content",menuList);
//        map.put("totalElements",menuList.size());
        return new ResponseEntity(securityModuleService.buildTableTree(menuList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SecurityModuleEntity resources){
        if (resources.getId() != 0) {
            throw new BadRequestException("新增菜单不能包含ID");
        }
        if (!securityModuleService.checkName(0,resources.getPid(),resources.getName())) {
            throw new BadRequestException("菜单名重复");
        }
        return new ResponseEntity(securityModuleService.save(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@Validated @RequestBody SecurityModuleEntity resources){
        if (resources.getId() == 0) {
            throw new BadRequestException("菜单ID不能为空");
        }
        if (!securityModuleService.checkName(resources.getId(),resources.getPid(),resources.getName())) {
            throw new BadRequestException("菜单名重复");
        }
        securityModuleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable int id){
        securityModuleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
