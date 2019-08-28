package com.jwBlog.security.service;

import com.jwBlog.base.service.BaseService;
import com.jwBlog.security.entity.SecurityRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface SecurityRoleService  extends BaseService<SecurityRoleEntity> {
    /**
     * 根据用户id查询用户所有的可用角色
     * @param userId
     * @param status
     * @return
     */
    List<SecurityRoleEntity> queryByUserId(Integer userId, String status);

    /**
     * 批量更新用户状态
     * @param status 状态(0正常 -1禁用)
     * @return
     */
    int updateBatchStatus(Integer[] ids, String status);
    /**
     * 检查名称是否重复
     * @param id
     * @param name
     * @return
     */
    boolean checkName(@Param(value = "id") Integer id, @Param(value = "name") String name);

    Object getRoleTree();
}
