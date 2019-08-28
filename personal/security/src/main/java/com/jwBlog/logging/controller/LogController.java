package com.jwBlog.logging.controller;

import com.alibaba.fastjson.JSONObject;
import com.jwBlog.logging.entity.LogEntity;
import com.jwBlog.logging.service.LogService;
import com.jwBlog.utils.dp.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jie
 * @date 2018-11-24
 */
@RestController
@RequestMapping("api")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping(value = "/logs")
//    @RequiresPermissions("log:list")
    public ResponseEntity getLogs(LogEntity log, Pageable pageable){
        log.setLogType("INFO");
//        log.put("logType","INFO");
        return new ResponseEntity(logService.queryListByPage(MapUtils.objectToMap(log),pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error")
//    @RequiresPermissions("log:err")
    public ResponseEntity getErrorLogs(LogEntity log, Pageable pageable){
        log.setLogType("ERROR");
//        log.put("logType","ERROR");
        return new ResponseEntity(logService.queryListByPage(MapUtils.objectToMap(log),pageable), HttpStatus.OK);
    }
}
