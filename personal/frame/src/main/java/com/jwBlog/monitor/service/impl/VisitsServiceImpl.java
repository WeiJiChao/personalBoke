package com.jwBlog.monitor.service.impl;

import com.jwBlog.logging.dao.LogDao;
import com.jwBlog.monitor.dao.VisitsDao;
import com.jwBlog.monitor.entity.VisitsEntity;
import com.jwBlog.monitor.service.VisitsService;
import com.jwBlog.utils.dp.StringUtilsNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-12-13
 */
@Slf4j
@Service("visitsService")
public class VisitsServiceImpl implements VisitsService {

    @Autowired
    private VisitsDao visitsDao;

    @Autowired
    private LogDao logDao;

    @Override
    public void save() {
        LocalDate localDate = LocalDate.now();
        VisitsEntity visitsEntity=visitsDao.findByDate(localDate.toString());
        if(visitsEntity == null || visitsEntity.getId() == null || visitsEntity.getId() == 0){
            visitsEntity = new VisitsEntity();
            visitsEntity.setWeekDay(StringUtilsNew.getWeekDay());
            visitsEntity.setPvCounts(1L);
            visitsEntity.setIpCounts(1L);
            visitsEntity.setDate(localDate.toString());
            visitsDao.save(visitsEntity);
        }
    }

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        VisitsEntity visits = visitsDao.findByDate(localDate.toString());
        visits.setPvCounts(visits.getPvCounts()+1);
        long ipCounts = logDao.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        visitsDao.update(visits);
    }

    @Override
    public Object get() {
        Map map = new HashMap();
        LocalDate localDate = LocalDate.now();
        VisitsEntity visits = visitsDao.findByDate(localDate.toString());
        List<VisitsEntity> list = visitsDao.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        long recentVisits = 0, recentIp = 0;
        for (VisitsEntity data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits",visits.getPvCounts() == null?0:visits.getPvCounts());
        map.put("newIp",visits.getIpCounts() == null?0:visits.getIpCounts());
        map.put("recentVisits",recentVisits);
        map.put("recentIp",recentIp);
        return map;
    }

    @Override
    public Object getChartData() {
        Map map = new HashMap();
        LocalDate localDate = LocalDate.now();
        List<VisitsEntity> list = visitsDao.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        map.put("weekDays",list.stream().map(VisitsEntity::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData",list.stream().map(VisitsEntity::getPvCounts).collect(Collectors.toList()));
        map.put("ipData",list.stream().map(VisitsEntity::getIpCounts).collect(Collectors.toList()));
        return map;
    }
}
