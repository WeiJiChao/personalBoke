package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SecurityRoleDao extends BaseDao<SecurityRoleEntity> {
    /**
     * 根据用户查询角色集合
     * @param userId
     * @return
     */
    List<SecurityRoleEntity> queryByUserId(@Param(value = "userId") Integer userId, @Param(value = "active") String status);

    /**
     * 根据父菜单Id查询菜单
     * @param moduleId
     * @return
     */
    List<SecurityRoleEntity> queryByModuleId(@Param(value = "moduleId") Integer moduleId, @Param(value = "active") String status);
    /**
     * 根据父菜单Id查询菜单
     * @param permissionId
     * @return
     */
    List<SecurityRoleEntity> queryByPermissionId(@Param(value = "permissionId") Integer permissionId, @Param(value = "active") String status);

    /**
     * 批量更新角色状态
     * @param params key:ids 角色ids
     * @return
     */
    int updateBatchStatus(Map<String, Object> params);
    /**
     * 检查名称是否重复
     * @param id
     * @param name
     * @return
     */
    Integer checkName(@Param(value = "id") Integer id, @Param(value = "name") String name);
}
