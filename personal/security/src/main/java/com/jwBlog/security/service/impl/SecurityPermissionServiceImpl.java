package com.jwBlog.security.service.impl;


import com.jwBlog.frame.PageUtil;
import com.jwBlog.security.dao.SecurityPermissionDao;
import com.jwBlog.security.dao.SecurityRoleDao;
import com.jwBlog.security.dao.SecurityRolePermissionDao;
import com.jwBlog.security.entity.*;
import com.jwBlog.security.service.SecurityPermissionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("securityPermissionService")
public class SecurityPermissionServiceImpl implements SecurityPermissionService {
	@Autowired
	private SecurityPermissionDao securityPermissionDao;
	@Autowired
	private SecurityRoleDao securityRoleDao;
	@Autowired
	private SecurityRolePermissionDao securityRolePermissionDao;

	@Override
	public SecurityPermissionEntity queryObject(Integer id){
		return securityPermissionDao.queryObject(id);
	}

	@Override
	public List<SecurityPermissionEntity> queryList(Map<String, Object> map){
		List<SecurityPermissionEntity> list= securityPermissionDao.queryList(map);
		for(SecurityPermissionEntity securityPermissionEntity:list){
			securityPermissionEntity.setRoles(securityRoleDao.queryByPermissionId(securityPermissionEntity.getId(), SecurityRoleEntity.active_usable));
		}
		return list;
	}
	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(securityPermissionDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);
		List<SecurityPermissionEntity> list= securityPermissionDao.queryList(map);
		for(SecurityPermissionEntity securityPermissionEntity:list){
			securityPermissionEntity.setRoles(securityRoleDao.queryByPermissionId(securityPermissionEntity.getId(), SecurityRoleEntity.active_usable));
		}
		return PageableExecutionUtils.getPage(list, pageable, () -> {
			return securityPermissionDao.queryTotal(map);
		});
	}
	@Override
	public List<SecurityPermissionEntity> queryListByBean(SecurityPermissionEntity entity) {
		return securityPermissionDao.queryListByBean(entity);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return securityPermissionDao.queryTotal(map);
	}

	@Override
	public SecurityPermissionEntity save(SecurityPermissionEntity securityModule){
		securityPermissionDao.save(securityModule);
//		saveRoleMenu(securityModule);
		return  securityModule;
	}

	@Override
	public int update(SecurityPermissionEntity securityPermissionEntity){
//		saveRoleMenu(securityPermissionEntity);
		return securityPermissionDao.update(securityPermissionEntity);
	}

	public void saveRoleMenu(SecurityPermissionEntity securityPermission){
		securityRolePermissionDao.deleteByPermission(securityPermission.getId());
		if(securityPermission.getRoles().size() == 0)return;
		List<SecurityRolePermissionEntity> roleModules=new ArrayList<>();
		for(SecurityRoleEntity role:securityPermission.getRoles()){
			roleModules.add(new SecurityRolePermissionEntity(role.getId(), securityPermission.getId()));
		}
		securityRolePermissionDao.saveBatch(roleModules);
	}

	@Override
	public int delete(Integer id){
		return securityPermissionDao.delete(id);
	}

	@Override
	public int deleteBatch(Integer[] ids){
		return securityPermissionDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

	@Override
	public List<SecurityPermissionEntity> queryByUserId(Integer userId) {
		return securityPermissionDao.queryByUserId(userId,SecurityPermissionEntity.active_usable);
	}

	@Override
	public List<SecurityPermissionEntity> queryListParentId(Integer parentId) {
		return securityPermissionDao.queryListParentId(parentId);
	}

	@Override
	public List<SecurityPermissionEntity> queryMenuListUser(Integer userId) {
		List<SecurityPermissionEntity> menuList=null;
		if(userId == 0){
			menuList = securityPermissionDao.queryList(new HashMap<>());
		}else {
			menuList = securityPermissionDao.queryByUserId(userId,SecurityPermissionEntity.active_usable);
		}
		List<Integer> menuIds = new ArrayList<Integer>();
		for (SecurityPermissionEntity menu:menuList){
			menuIds.add(menu.getId());
		}
		//查询出根菜单
		List<SecurityPermissionEntity> rootMenus = queryMenuByParentId(0, menuIds);
		//递归查询出所有子资源的子资源
		List<SecurityPermissionEntity> treeMenus = getTreeMenus(rootMenus, menuIds);
		return treeMenus;
	}
	/**
	 * 根据上级父id，查询出下级所有该用户已授权资源
	 * @param parentId 父id
	 * @param menuIds 授权资源ids
	 * @return
	 */
	public List<SecurityPermissionEntity> queryMenuByParentId(Integer parentId, List<Integer> menuIds){
		//根据父Id,查询所有下级资源
		List<SecurityPermissionEntity> menuEntities = securityPermissionDao.queryListParentId(parentId);
		List<SecurityPermissionEntity> reMenus= new ArrayList<>();
		for (SecurityPermissionEntity menu:menuEntities){
			//如果下级资源在用户授权资源中,则添加
			if(menuIds.contains(menu.getId())){
				reMenus.add(menu);
			}
		}
		return reMenus;
	}

	/**
	 * 递归查询出所有菜单的子菜单，子菜单的所有子菜单 只包括用户授权的资源
	 * @param resouces 源菜单
	 * @param menuIds 用户所有授权资源
	 * @return
	 */
	public List<SecurityPermissionEntity> getTreeMenus(List<SecurityPermissionEntity> resouces, List<Integer> menuIds){
		List<SecurityPermissionEntity> treeMenus = new ArrayList<>();
		for (SecurityPermissionEntity menu:resouces){
			List<SecurityPermissionEntity> childMenus = queryMenuByParentId(menu.getId(), menuIds);
			menu.setChildren(getTreeMenus(childMenus, menuIds));
			treeMenus.add(menu);
		}
		return treeMenus;
	}

	@Override
	public boolean checkName(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "name") String name) {
		return securityPermissionDao.checkName(id,parentId,name)<1;
	}

	@Override
	public boolean checkAlias(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "alias") String alias) {
		return securityPermissionDao.checkAlias(id, parentId, alias)<1;
	}

	@Override
	public Object buildTableTree(List<SecurityPermissionEntity> permissionList) {
		List<SecurityPermissionEntity> trees = new ArrayList<SecurityPermissionEntity>();

		for (SecurityPermissionEntity permission : permissionList) {
			if (0==permission.getPid()) {
				trees.add(permission);
			}
			for (SecurityPermissionEntity it : permissionList) {
				if (it.getPid()==permission.getId()) {
					if (permission.getChildren() == null) {
						permission.setChildren(new ArrayList<SecurityPermissionEntity>());
					}
					permission.getChildren().add(it);
				}
			}
		}
		Integer totalElements = permissionList!=null?permissionList.size():0;
		Map map = new HashMap();
		map.put("content",trees.size() == 0?permissionList:trees);
		map.put("totalElements",totalElements);
		return map;
	}

	@Override
	public Object buildPermissionTree(List<SecurityPermissionEntity> permissionList) {
		List<Map<String,Object>> list = new LinkedList<>();
		permissionList.forEach(permission -> {
					if (permission!=null){
						List<SecurityPermissionEntity> permissions = securityPermissionDao.queryListParentId(permission.getId());
						Map<String,Object> map = new HashMap<>();
						map.put("id",permission.getId());
						map.put("label",permission.getAlias());
						if(permissionList!=null && permissionList.size()!=0){
							map.put("children",buildPermissionTree(permissions));
						}
						list.add(map);
					}
				}
		);
		return list;
	}
}
