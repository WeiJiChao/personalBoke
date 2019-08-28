package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityPermissionDao extends BaseDao<SecurityPermissionEntity> {
    /**
     *根据登陆用户Id,查询出所有授权权限
     * @param userId
     * @return
     */
    List<SecurityPermissionEntity> queryByUserId(@Param(value = "userId") Integer userId, @Param(value = "active") String active);
    /**
     *根据橘角色Id,查询出所有授权权限
     * @param roleId
     * @return
     */
    List<SecurityPermissionEntity> queryByRoleId(@Param(value = "roleId") Integer roleId, @Param(value = "active") String active);
    /**
     * 根据父权限Id查询权限
     * @param parenId
     * @return
     */
    List<SecurityPermissionEntity> queryListParentId(Integer parenId);

    /**
     * 检查名称是否重复
     * @param id
     * @param parentId
     * @param name
     * @return
     */
    Integer checkName(@Param(value = "id") Integer id, @Param(value = "pid") Integer parentId, @Param(value = "name") String name);
    /**
     * 检查别名是否重复
     * @param id
     * @param parentId
     * @param alias
     * @return
     */
    Integer checkAlias(@Param(value = "id") Integer id, @Param(value = "pid") Integer parentId, @Param(value = "alias") String alias);
}
