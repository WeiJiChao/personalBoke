package com.jwBlog.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.jwBlog.frame.PageUtil;
import com.jwBlog.security.dao.SecurityModuleDao;
import com.jwBlog.security.dao.SecurityRoleDao;
import com.jwBlog.security.dao.SecurityRoleModuleDao;
import com.jwBlog.security.entity.SecurityModuleEntity;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import com.jwBlog.security.entity.SecurityRoleEntity;
import com.jwBlog.security.entity.SecurityRoleModuleEntity;
import com.jwBlog.security.entity.vo.MenuMetaVo;
import com.jwBlog.security.entity.vo.MenuVo;
import com.jwBlog.security.service.SecurityModuleService;
import com.jwBlog.security.service.SecurityPermissionService;
import com.jwBlog.security.service.SecurityRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("securityModuleService")
public class SecurityModuleServiceImpl implements SecurityModuleService {
	@Autowired
	private SecurityModuleDao securityModuleDao;
	@Autowired
	private SecurityRoleDao securityRoleDao;
	@Autowired
	private SecurityRoleModuleDao securityRoleModuleDao;

	@Override
	public SecurityModuleEntity queryObject(Integer id){
		return securityModuleDao.queryObject(id);
	}

	@Override
	public List<SecurityModuleEntity> queryList(Map<String, Object> map){
		List<SecurityModuleEntity> list= securityModuleDao.queryList(map);
		for(SecurityModuleEntity securityModuleEntity:list){
			securityModuleEntity.setRoles(securityRoleDao.queryByModuleId(securityModuleEntity.getId(), SecurityRoleEntity.active_usable));
		}
		return list;
	}

	@Override
	public List<SecurityModuleEntity> queryListByBean(SecurityModuleEntity entity) {
		return securityModuleDao.queryListByBean(entity);
	}
	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(securityModuleDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);
		List<SecurityModuleEntity> list= securityModuleDao.queryList(map);
		for(SecurityModuleEntity securityModuleEntity:list){
			securityModuleEntity.setRoles(securityRoleDao.queryByModuleId(securityModuleEntity.getId(), SecurityRoleEntity.active_usable));
		}
		return PageableExecutionUtils.getPage(list, pageable, () -> {
			return securityModuleDao.queryTotal(map);
		});
	}
	@Override
	public int queryTotal(Map<String, Object> map){
		return securityModuleDao.queryTotal(map);
	}

	@Override
	public SecurityModuleEntity save(SecurityModuleEntity securityModule){
		securityModuleDao.save(securityModule);
		saveRoleMenu(securityModule);
		return  securityModule;
	}

	@Override
	public int update(SecurityModuleEntity securityModule){
		saveRoleMenu(securityModule);
		return securityModuleDao.update(securityModule);
	}


	public void saveRoleMenu(SecurityModuleEntity securityModule){
		securityRoleModuleDao.deleteByModule(securityModule.getId());
		if(securityModule.getRoles().size() == 0)return;
		List<SecurityRoleModuleEntity> roleModules=new ArrayList<>();
		for(SecurityRoleEntity role:securityModule.getRoles()){
			roleModules.add(new SecurityRoleModuleEntity(role.getId(), securityModule.getId()));
		}
		securityRoleModuleDao.saveBatch(roleModules);
	}

	@Override
	public int delete(Integer id){
		return securityModuleDao.delete(id);
	}

	@Override
	public int deleteBatch(Integer[] ids){
		return securityModuleDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

	@Override
	public List<SecurityModuleEntity> queryByUserId(Integer userId) {
		return securityModuleDao.queryByUserId(userId,SecurityModuleEntity.active_usable);
	}

	@Override
	public List<SecurityModuleEntity> queryListParentId(Integer parentId) {
		return securityModuleDao.queryListParentId(parentId);
	}

	@Override
	public List<SecurityModuleEntity> queryMenuListUser(Integer userId) {
		List<SecurityModuleEntity> menuList=null;
		if(userId==0){
			menuList = securityModuleDao.queryList(new HashMap());
		}else {
			menuList = securityModuleDao.queryByUserId(userId,SecurityModuleEntity.active_usable);
		}
		List<Integer> menuIds = new ArrayList<Integer>();
		for (SecurityModuleEntity menu:menuList){
			menuIds.add(menu.getId());
		}
		//查询出根菜单
		List<SecurityModuleEntity> rootMenus = queryMenuByParentId(0, menuIds);
		//递归查询出所有子资源的子资源
		List<SecurityModuleEntity> treeMenus = getTreeMenus(rootMenus, menuIds);
		return treeMenus;
	}
	/**
	 * 根据上级父id，查询出下级所有该用户已授权资源
	 * @param parentId 父id
	 * @param menuIds 授权资源ids
	 * @return
	 */
	public List<SecurityModuleEntity> queryMenuByParentId(Integer parentId, List<Integer> menuIds){
		//根据父Id,查询所有下级资源
		List<SecurityModuleEntity> menuEntities = securityModuleDao.queryListParentId(parentId);
		List<SecurityModuleEntity> reMenus= new ArrayList<>();
		for (SecurityModuleEntity menu:menuEntities){
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
	public List<SecurityModuleEntity> getTreeMenus(List<SecurityModuleEntity> resouces, List<Integer> menuIds){
		List<SecurityModuleEntity> treeMenus = new ArrayList<>();
		for (SecurityModuleEntity menu:resouces){
			List<SecurityModuleEntity> childMenus = queryMenuByParentId(menu.getId(), menuIds);
			menu.setChildren(getTreeMenus(childMenus, menuIds));
			treeMenus.add(menu);
		}
		return treeMenus;
	}

	@Override
	public boolean checkName(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "name") String name) {
		return securityModuleDao.checkName(id,parentId,name)<1;
	}

	@Override
	public boolean checkCode(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "code") String code) {
		return securityModuleDao.checkCode(id, parentId, code)<1;
	}

	@Override
	public List<MenuVo> buildMenus(List<SecurityModuleEntity> menuDTOS) {
		List<MenuVo> list = new LinkedList<>();
		menuDTOS.forEach(menuDTO -> {
					if (menuDTO!=null){
						List<SecurityModuleEntity> menuDTOList = menuDTO.getChildren();
						MenuVo menuVo = new MenuVo();
						menuVo.setName(menuDTO.getName());
						menuVo.setPath(menuDTO.getPath());
						// 如果不是外链
						if(!SecurityModuleEntity.isFrame_yes.equals(menuDTO.getIsFrame())){
							if(menuDTO.getPid() == 0){
								//一级目录需要加斜杠，不然访问 会跳转404页面
								menuVo.setPath("/" + menuDTO.getPath());
								menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
							}else if(!StrUtil.isEmpty(menuDTO.getComponent())){
								menuVo.setComponent(menuDTO.getComponent());
							}
						}
						menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon()));
						if(menuDTOList!=null && menuDTOList.size()!=0){
							menuVo.setAlwaysShow(true);
							menuVo.setRedirect("noredirect");
							menuVo.setChildren(buildMenus(menuDTOList));
							// 处理是一级菜单并且没有子菜单的情况
						} else if(menuDTO.getPid() == 0){
							MenuVo menuVo1 = new MenuVo();
							menuVo1.setMeta(menuVo.getMeta());
							// 非外链
							if(!SecurityModuleEntity.isFrame_yes.equals(menuDTO.getIsFrame())){
								menuVo1.setPath("index");
								menuVo1.setName(menuVo.getName());
								menuVo1.setComponent(menuVo.getComponent());
							} else {
								menuVo1.setPath(menuDTO.getPath());
							}
							menuVo.setName(null);
							menuVo.setMeta(null);
							menuVo.setComponent("Layout");
							List<MenuVo> list1 = new ArrayList<MenuVo>();
							list1.add(menuVo1);
							menuVo.setChildren(list1);
						}
						list.add(menuVo);
					}
				}
		);
		return list;
	}

	@Override
	public Object buildTree(List<SecurityModuleEntity> menuDTOS) {
		List<Map<String,Object>> list = new LinkedList<>();
		menuDTOS.forEach(menu -> {
				if (menu!=null){
					List<SecurityModuleEntity> menuList = securityModuleDao.queryListParentId(menu.getId());
					Map<String,Object> map = new HashMap<>();
					map.put("id",menu.getId());
					map.put("label",menu.getName());
					if(menuList!=null && menuList.size()!=0){
						map.put("children",buildTree(menuList));
					}
					list.add(map);
				}
			}
		);
		return list;
	}
	@Override
	public Object buildTableTree(List<SecurityModuleEntity> moduleList) {
		List<SecurityModuleEntity> trees = new ArrayList<SecurityModuleEntity>();
		for (SecurityModuleEntity module : moduleList) {
			if (0==module.getPid()) {
				trees.add(module);
			}
			for (SecurityModuleEntity it : moduleList) {
				if (it.getPid()==module.getId()) {
					if (module.getChildren() == null) {
						module.setChildren(new ArrayList<SecurityModuleEntity>());
					}
					module.getChildren().add(it);
				}
			}
		}
		Integer totalElements = moduleList!=null?moduleList.size():0;
		Map map = new HashMap();
		map.put("content",trees.size() == 0?moduleList:trees);
		map.put("totalElements",totalElements);
		return map;
	}

}
