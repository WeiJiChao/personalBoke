package com.jwBlog.security.service.impl;

import com.jwBlog.base.Constants;
import com.jwBlog.frame.PageUtil;
import com.jwBlog.frame.exception.MyException;
import com.jwBlog.jwt.config.JwtUser;
import com.jwBlog.security.dao.SecurityRoleDao;
import com.jwBlog.security.dao.SecurityUserDao;
import com.jwBlog.security.dao.SecurityUserRoleDao;
import com.jwBlog.security.entity.SecurityModuleEntity;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import com.jwBlog.security.entity.SecurityRoleEntity;
import com.jwBlog.security.entity.SecurityUserEntity;
import com.jwBlog.security.service.SecurityUserService;
import com.jwBlog.shiro.UserUtils;
import com.jwBlog.utils.dp.DateUtils;
import com.jwBlog.utils.dp.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("securityUserService")
public class SecurityUserServiceImpl implements SecurityUserService {
	@Autowired
	private SecurityUserDao securityUserDao;
	@Autowired
	private SecurityUserRoleDao securityUserRoleDao;
    @Autowired
    private SecurityRoleDao securityRoleDao;

	@Override
	public SecurityUserEntity queryObject(Integer id){
		return securityUserDao.queryObject(id);
	}

	@Override
	public List<SecurityUserEntity> queryList(Map<String, Object> map){
		return securityUserDao.queryList(map);
	}
	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(securityUserDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);
        List<SecurityUserEntity> list= securityUserDao.queryList(map);
        for(SecurityUserEntity securityUserEntity:list){
            securityUserEntity.setRoles(securityRoleDao.queryByUserId(securityUserEntity.getId(), SecurityRoleEntity.active_usable));
        }
		return PageableExecutionUtils.getPage(list, pageable, () -> {
			return securityUserDao.queryTotal(map);
		});
	}

    @Override
	public List<SecurityUserEntity> queryListByBean(SecurityUserEntity entity) {
		return securityUserDao.queryListByBean(entity);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return securityUserDao.queryTotal(map);
	}

	@Override
	public SecurityUserEntity save(SecurityUserEntity securityUser){
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		securityUser.setSalt(salt);
		if(!StringUtils.isEmpty(securityUser.getPassword())){
			////md5加密，加盐
			securityUser.setPassword(SecurityUserEntity.passwordSecurity(securityUser.getPassword(),salt));
		}else{
			securityUser.setPassword(SecurityUserEntity.passwordSecurity(Constants.param_default_password,salt));
		}
		//保存用户与角色关系
		JwtUser currentUser = UserUtils.getCurrentUser();
		securityUser.setCreateUser(currentUser.getId());
		securityUser.setCreateTime(DateUtils.toString(Constants.param_default_dateTimeFormat));
		securityUser.setLastPassTime(DateUtils.toString(Constants.param_default_dateTimeFormat));
		securityUser.setActive(SecurityUserEntity.active_usable);
		int userId=securityUserDao.save(securityUser);
		saveUserRole(securityUser);
		return securityUser;
	}

	@Override
	public int update(SecurityUserEntity securityUser){
		if(securityUser == null || StringUtils.isEmpty(securityUser.getId())){
			throw new MyException("更新失败，用户id不能为空!");
		}
//		securityUser.setPassword(null);
//		securityUser.setSalt(null);
		securityUser.setLastModifyUser(UserUtils.getCurrentUserId());
		securityUser.setLastModifyTime(DateUtils.toString(Constants.param_default_dateTimeFormat));
		//保存用户与角色关系
		saveUserRole(securityUser);
		securityUserDao.update(securityUser);
		return securityUser.getId();
	}

	/**
	 * 保存用更新用户公共操作
	 * @param user
	 */
	public void saveUserRole(SecurityUserEntity user){
		//先删除用户与角色关系
		securityUserRoleDao.delete(user.getId());
		//保存用户与角色关系
		if(user.getRoles()!=null&&user.getRoles().size()>0) {
			Map<String, Object> map = new HashMap<>();
			List<Integer> roleIds=new ArrayList<>();
			for(SecurityRoleEntity role:user.getRoles()){
				if(role.getId()!=null&&role.getId()>0)roleIds.add(role.getId());
			}
			map.put("roleIdList", roleIds);
			map.put("userId", user.getId());
			securityUserRoleDao.save(map);
		}
	}
	@Override
	public int delete(Integer id){
		return securityUserDao.delete(id);
	}

	@Override
	public int deleteBatch(Integer[] ids){
		securityUserRoleDao.deleteBatchByUserId(ids);
		return securityUserDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

	@Override
	public SecurityUserEntity queryByLoginName(String logName) {
		return securityUserDao.queryByLoginName(logName);
	}

	@Override
	public int updatePassword(SecurityUserEntity user) {
//		if(user == null){
//			throw new MyException("用户信息不能为空!");
//		}
//		if(StringUtils.isEmpty(user.getNewPassword())){
//			throw new MyException("新密码不能为空");
//		}
		JwtUser currentUser = UserUtils.getCurrentUser();
//		String newPassWord = SecurityUserEntity.passwordSecurity(user.getPassword(),currentUser.getSalt());
//		if(!newPassWord.equals(currentUser.getPassword())){
//			throw new MyException("密码不正确");
//		}
		Map<String,Object> params = new HashMap<>();
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		params.put("id",currentUser.getId());
		params.put("salt",salt);
		params.put("password",SecurityUserEntity.passwordSecurity(user.getNewPassword(),salt));
		return securityUserDao.updatePassword(params);
	}

	@Override
	public int updateBatchStatus(Integer[] ids, String status) {
		if(Arrays.asList(ids).contains(Constants.param_admin_user)){
			throw new MyException("不能重置超级管理员状态!");
		}
		JwtUser currentUser = UserUtils.getCurrentUser();
		Map<String,Object> params = new HashMap<>();
		params.put("ids",ids);
		if(SecurityUserEntity.active_usable.equals(status)){
			params.put("usableTime",DateUtils.toString(Constants.param_default_dateTimeFormat));
			params.put("usableUser",currentUser.getUsername());
		}else{
			params.put("forbiddenTime",DateUtils.toString(Constants.param_default_dateTimeFormat));
			params.put("forbiddenUser",currentUser.getUsername());
		}
		params.put("active",status);
		return securityUserDao.updateBatchStatus(params);
	}

	@Override
	public int resetPassWord(String[] ids) {
		Map<String,Object> params = new HashMap<>();
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		params.put("ids",ids);
		params.put("salt",salt);
		params.put("password", SecurityUserEntity.passwordSecurity(Constants.param_default_password,salt));
		return securityUserDao.resetPassWordBatch(params);
	}
	@Override
	public int resetPassWord(String code) {
		Map<String,Object> params = new HashMap<>();
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		params.put("code",code);
		params.put("salt",salt);
		params.put("password", SecurityUserEntity.passwordSecurity(Constants.param_default_password,salt));
		return securityUserDao.resetPassWord(params);
	}
	@Override
	public boolean checkCode(@Param(value = "id") Integer id, @Param(value = "code") String code) {
		return securityUserDao.checkCode(id,code)<1;
	}
}
