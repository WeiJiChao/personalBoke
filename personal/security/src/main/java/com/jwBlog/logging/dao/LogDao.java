package com.jwBlog.logging.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.logging.entity.LogEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends BaseDao<LogEntity> {
    /**
     * 获取一个时间段的IP记录
     * @param dateFrom
     * @param dateTo
     * @return
     */
    Long findIp(@Param(value = "dateFrom") String dateFrom, @Param(value = "dateTo") String dateTo);
}
