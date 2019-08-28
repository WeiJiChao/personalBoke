package com.atshunxiang.personal.controller;

import com.atshunxiang.personal.bean.BoKeDeleteRequest;
import com.atshunxiang.personal.bean.BoKeNewRequest;
import com.atshunxiang.personal.entity.BoKe;
import com.atshunxiang.personal.service.BoKeActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jw
 * @date 2019/8/21 15:51
 * @company 舜翔众邦
 * @desc
 */
@RestController
@RequestMapping("/boKe")
@CrossOrigin
public class BoKeActionController {

    @Autowired
    private BoKeActionService boKeActionService;

    /**
     * 增加博客
     * @param boKeNewRequest
     */
    @RequestMapping("/add")
    public  void   addBoKe(@RequestBody  BoKeNewRequest boKeNewRequest){
        System.out.println(boKeNewRequest.toString());
        boKeActionService.add(boKeNewRequest);
    }

    /**
     * 删除博客
     * @param boKeDeleteRequest
     */
    @RequestMapping("/deleteBoKe")
    public  void  deleteBoKe(@RequestBody BoKeDeleteRequest boKeDeleteRequest){
        System.out.println(boKeDeleteRequest.getBokeId());
        boKeActionService.deleteBoke(boKeDeleteRequest);
    }


    /**
     * 列表博客
     */
    @RequestMapping("/list")
    public ResponseEntity boKeList() throws Exception {
        Map map = new HashMap();
        List<BoKe> list = boKeActionService.list();
        System.out.println(list);
        map.put("list", list);
         return new ResponseEntity( map, HttpStatus.OK);
    }




}
