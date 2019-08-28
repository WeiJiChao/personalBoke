package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityUserRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityUserRoleDao extends BaseDao<SecurityUserRoleEntity> {
    /**
     * 根据用户ID，获取角色ID列表
     */
    List<String> queryRoleIdList(Integer userId);

    /**
     * 根据用户list批量删除用户角色中间表
     * @param users
     * @return
     */
    int deleteBatchByUserId(Integer[] users);

    /**
     * 根据角色list批量删除用户角色中间表
     * @param roles
     * @return
     */
    int deleteBatchByRoleId(Integer[] roles);
}
