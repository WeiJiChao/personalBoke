package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityRoleModuleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityRoleModuleDao extends BaseDao<SecurityRoleModuleEntity> {
    List<String> queryListByRoleId(Integer roleId);
    void deleteByRole(int roleId);
    void deleteByModule(int moduleId);
}
