package com.jwBlog.monitor.dao;

import com.jwBlog.monitor.entity.VisitsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VisitsDao {
    int save(VisitsEntity visitsEntity);
    void update(VisitsEntity visitsEntity);
    VisitsEntity findByDate(String date);
    /**
     * 获得一个时间段的记录
     * @param dateFrom
     * @param dateTo
     * @return
     */
    List<VisitsEntity> findAllVisits(@Param(value = "dateFrom") String dateFrom, @Param(value = "dateTo") String dateTo);

}
