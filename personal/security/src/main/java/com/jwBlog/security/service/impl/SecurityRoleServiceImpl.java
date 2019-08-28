package com.jwBlog.security.service.impl;

import com.jwBlog.frame.PageUtil;
import com.jwBlog.security.dao.*;
import com.jwBlog.security.entity.*;
import com.jwBlog.security.service.SecurityRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("securityRoleService")
public class SecurityRoleServiceImpl implements SecurityRoleService {
	@Autowired
	private SecurityRoleDao securityRoleDao;
	@Autowired
	private SecurityModuleDao securityModuleDao;
	@Autowired
	private SecurityPermissionDao securityPermissionDao;
	@Autowired
	private SecurityRoleModuleDao securityRoleModuleDao;
	@Autowired
	private SecurityRolePermissionDao securityRolePermissionDao;
	@Autowired
	private SecurityUserRoleDao securityUserRoleDao;

	@Override
	public SecurityRoleEntity queryObject(Integer id){
		return securityRoleDao.queryObject(id);
	}
	
	@Override
	public List<SecurityRoleEntity> queryList(Map<String, Object> map){
		return securityRoleDao.queryList(map);
	}
	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(securityRoleDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);

		List<SecurityRoleEntity> list= securityRoleDao.queryList(map);
		for(SecurityRoleEntity securityRoleEntity:list){
			securityRoleEntity.setMenus(securityModuleDao.queryByRoleId(securityRoleEntity.getId(), SecurityRoleEntity.active_usable));
			securityRoleEntity.setPermissions(securityPermissionDao.queryByRoleId(securityRoleEntity.getId(), SecurityRoleEntity.active_usable));
		}
		return PageableExecutionUtils.getPage(list, pageable, () -> {
			return securityRoleDao.queryTotal(map);
		});
	}
    @Override
    public List<SecurityRoleEntity> queryListByBean(SecurityRoleEntity entity) {
        return securityRoleDao.queryListByBean(entity);
    }
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return securityRoleDao.queryTotal(map);
	}
	
	@Override
	public SecurityRoleEntity save(SecurityRoleEntity securityRole){
//		SecurityUserEntity currentUser = UserUtils.getCurrentUser();
//		securityRole.setCreateUser(currentUser.getCode());
//		securityRole.setCreateTime(DateUtils.toString(Constants.param_default_dateFormat));
		securityRoleDao.save(securityRole);
		saveRolePermission(securityRole);
		saveRoleMenu(securityRole);
		return securityRole;
	}
	
	@Override
	public int update(SecurityRoleEntity securityRole){
//		SecurityUserEntity currentUser = UserUtils.getCurrentUser();
		saveRolePermission(securityRole);
		saveRoleMenu(securityRole);
		return securityRoleDao.update(securityRole);
	}

	public void saveRoleMenu(SecurityRoleEntity role){
		securityRoleModuleDao.deleteByRole(role.getId());
		if(role.getMenus().size() == 0)return;
		List<SecurityRoleModuleEntity> roleModules=new ArrayList<>();
		for(SecurityModuleEntity menu:role.getMenus()){
			roleModules.add(new SecurityRoleModuleEntity(role.getId(),menu.getId()));
		}
		securityRoleModuleDao.saveBatch(roleModules);
	}
	public void saveRolePermission(SecurityRoleEntity role){
		securityRolePermissionDao.deleteByRole(role.getId());
		if(role.getPermissions().size() == 0)return;
		List<SecurityRolePermissionEntity> roleModules=new ArrayList<>();
		for(SecurityPermissionEntity menu:role.getPermissions()){
			roleModules.add(new SecurityRolePermissionEntity(role.getId(),menu.getId()));
		}
		securityRolePermissionDao.saveBatch(roleModules);
	}
	@Override
	public int delete(Integer id){
        return securityRoleDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Integer[] ids){
		securityRoleModuleDao.deleteBatch(ids);
		securityUserRoleDao.deleteBatchByRoleId(ids);
        return securityRoleDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

	@Override
	public List<SecurityRoleEntity> queryByUserId(Integer userId, String status) {
		return securityRoleDao.queryByUserId(userId,status);
	}

	@Override
	public int updateBatchStatus(Integer[] ids, String status) {
		Map<String,Object> params = new HashMap<>();
//		SecurityUserEntity currentUser = UserUtils.getCurrentUser();
		params.put("ids",ids);
//		if(SecurityRoleEntity.active_usable.equals(status)){
//			params.put("usableTime",DateUtils.toString(Constants.param_default_dateTimeFormat));
//			params.put("usableUser",currentUser.getCode());
//		}else{
//			params.put("forbiddenTime",DateUtils.toString(Constants.param_default_dateTimeFormat));
//			params.put("forbiddenUser",currentUser.getCode());
//		}
		params.put("active",status);
		return securityRoleDao.updateBatchStatus(params);
	}

	@Override
	public boolean checkName(@Param(value = "id") Integer id, @Param(value = "name") String name) {
		return securityRoleDao.checkName(id,name)<1;
	}

	@Override
	public Object getRoleTree() {
		List<SecurityRoleEntity> roleList = securityRoleDao.queryList(new HashMap());
		List<Map<String, Object>> list = new ArrayList<>();
		for (SecurityRoleEntity role : roleList) {
			Map<String, Object> map = new HashMap<>();
			map.put("id",role.getId());
			map.put("label",role.getName());
			list.add(map);
		}
		return list;
	}
}
