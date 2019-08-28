package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityModuleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityModuleDao extends BaseDao<SecurityModuleEntity> {
    /**
     *根据登陆用户Id,查询出所有授权菜单
     * @param userId
     * @return
     */
    List<SecurityModuleEntity> queryByUserId(@Param(value = "userId") Integer userId, @Param(value = "active") String active);
    /**
     *根据角色Id,查询出所有授权菜单
     * @param roleId
     * @return
     */
    List<SecurityModuleEntity> queryByRoleId(@Param(value = "roleId") Integer roleId, @Param(value = "active") String active);

    /**
     * 根据父菜单Id查询菜单
     * @param parenId
     * @return
     */
    List<SecurityModuleEntity> queryListParentId(Integer parenId);
    /**
     * 检查名称是否重复
     * @param id
     * @param parentId
     * @param name
     * @return
     */
    Integer checkName(@Param(value = "id") Integer id, @Param(value = "pid") Integer parentId, @Param(value = "name") String name);
    /**
     * 检查编码是否重复
     * @param id
     * @param parentId
     * @param code
     * @return
     */
    Integer checkCode(@Param(value = "id") Integer id, @Param(value = "pid") Integer parentId, @Param(value = "code") String code);
}
