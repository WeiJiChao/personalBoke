package com.jwBlog.security.service;

import com.jwBlog.base.service.BaseService;
import com.jwBlog.security.entity.SecurityModuleEntity;
import com.jwBlog.security.entity.SecurityPermissionEntity;
import com.jwBlog.security.entity.SecurityUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityUserService extends BaseService<SecurityUserEntity> {
    /**
     * 根据登陆用户查询有效的用户
     * @param logName
     * @return
     */
    SecurityUserEntity queryByLoginName(String logName);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int updatePassword(SecurityUserEntity user);

    /**
     * 批量更新角色状态
     * @param status 状态(0正常 -1禁用)
     * @return
     */
    int updateBatchStatus(Integer[] ids, String status);

    /**
     * 重置密码
     * @param ids
     * @return
     */
    int resetPassWord(String[] ids);
    int resetPassWord(String id);
    /**
     * 检查登录名是否重复
     * @param id
     * @param code
     * @return
     */
    boolean checkCode(@Param(value = "id") Integer id, @Param(value = "code") String code);

}
