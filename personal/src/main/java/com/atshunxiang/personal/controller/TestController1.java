package com.atshunxiang.personal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jw
 * @date 2019/8/21 10:47
 * @company 舜翔众邦
 * @desc
 */
@RestController
@RequestMapping("/test")
public class TestController1 {

    @GetMapping("t")
    public String test(){
        return "hello world";
    }
}
