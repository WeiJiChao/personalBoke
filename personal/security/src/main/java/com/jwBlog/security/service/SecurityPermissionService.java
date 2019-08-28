package com.jwBlog.security.service;

import com.jwBlog.base.service.BaseService;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SecurityPermissionService extends BaseService<SecurityPermissionEntity> {
    /**
     *根据登陆用户Id,查询出所有授权菜单 按钮 登陆授权
     * @param userId
     * @return
     */
    List<SecurityPermissionEntity> queryByUserId(Integer userId);

    /**
     * 根据父菜单Id查询菜单
     * @param parentId
     * @return
     */
    List<SecurityPermissionEntity> queryListParentId(Integer parentId);

    /**
     * 获取用户菜单列表 主页查询用户菜单用
     * @param userId
     * @return
     */
    List<SecurityPermissionEntity> queryMenuListUser(Integer userId);

    /**
     * 检查名称是否重复
     * @param id
     * @param parentId
     * @param name
     * @return
     */
    boolean checkName(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "name") String name);
    /**
     * 检查编码是否重复
     * @param id
     * @param parentId
     * @param alias
     * @return
     */
    boolean checkAlias(@Param(value = "id") Integer id, @Param(value = "parentId") Integer parentId, @Param(value = "alias") String alias);

    Object buildTableTree(List<SecurityPermissionEntity> permissionList);

    Object buildPermissionTree(List<SecurityPermissionEntity> permissionList);
}
