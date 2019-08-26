package com.atshunxiang.personal.service.imple;

import com.atshunxiang.personal.bean.BoKeDeleteRequest;
import com.atshunxiang.personal.bean.BoKeNewRequest;
import com.atshunxiang.personal.dao.DAO;
import com.atshunxiang.personal.entity.BoKe;
import com.atshunxiang.personal.service.BoKeActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author jw
 * @date 2019/8/21 17:18
 * @company 舜翔众邦
 * @desc
 */
@Service
public class BoKeActionServiceImple implements BoKeActionService {

    @Autowired
    private DAO dao;

    @Override
    public List<BoKe> list() throws Exception {
        return dao.list();
    }

    @Override
    public void add(BoKeNewRequest boKeNewRequest) {
        String bokeBody = boKeNewRequest.getBokeBody();
        String bokeTitle = boKeNewRequest.getBokeTitle();
        Date date = new Date();
        String recordStatus = "1";
        System.out.println(bokeTitle);
        System.out.println(bokeBody);
        dao.add(bokeTitle ,bokeBody , date , recordStatus );


    }

    @Override
    public void deleteBoke(BoKeDeleteRequest boKeDeleteRequest) {
        Integer bokeId = boKeDeleteRequest.getBokeId();
        Date date = new Date();
        String recordStatus = "0";
        dao.deleteBoke(bokeId , date ,recordStatus);
    }

}
