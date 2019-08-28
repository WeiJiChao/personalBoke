package com.jwBlog.security.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.security.entity.SecurityUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SecurityUserDao extends BaseDao<SecurityUserEntity> {
    /**
     * 根据登陆用户查询有效的用户
     * @param loginName
     * @return
     */
    SecurityUserEntity queryByLoginName(String loginName);
    /**
     * 更新密码
     * @param params key:passWord 密码， key:id 主键id
     * @return
     */
    int updatePassword(Map<String, Object> params);

    /**
     * 批量重置密码
     * @param params key:passWord 密码， key:sid 主键ids
     * @return
     */
    int resetPassWordBatch(Map<String, Object> params);

    /**
     * 批量重置密码
     * @param params key:passWord 密码， key:sid 主键ids
     * @return
     */
    int resetPassWord(Map<String, Object> params);
    /**
     * 批量更新用户状态
     * @param params key:ids 用户ids
     * @return
     */
    int updateBatchStatus(Map<String, Object> params);
    /**
     * 检查登录名是否重复
     * @param id
     * @param code
     * @return
     */
    Integer checkCode(@Param(value = "id") Integer id, @Param(value = "code") String code);
}
