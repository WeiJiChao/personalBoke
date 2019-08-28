package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityRolePermissionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRolePermissionDao extends BaseDao<SecurityRolePermissionEntity> {
    List<String> queryListByRoleId(Integer roleId);
    void deleteByRole(int roleId);
    void deleteByPermission(int permissionId);
}
