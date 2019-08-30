package com.jwBlog.personal.service.imple;

import com.jwBlog.personal.bean.BoKeDeleteRequest;
import com.jwBlog.personal.bean.BoKeNewRequest;
import com.jwBlog.personal.dao.BoKeDAO;
import com.jwBlog.personal.entity.BoKe;
import com.jwBlog.personal.service.BoKeActionService;
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
    private BoKeDAO dao;

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
