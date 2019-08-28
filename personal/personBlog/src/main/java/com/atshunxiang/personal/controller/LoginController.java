package com.atshunxiang.personal.controller;

import com.atshunxiang.personal.bean.LoginRequest;
import com.atshunxiang.personal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jw
 * @date 2019/8/21 10:56
 * @company 舜翔众邦
 * @desc
 */
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public Boolean login(@RequestBody LoginRequest loginRequest){
        Boolean  b = false;
        System.out.println(loginRequest.getPassWord());
        String password = loginRequest.getPassWord();
        String userName = loginRequest.getUserName();
//
        if (userName.equals("jiwei") && password.equals("jiwei123")){
            b = true;
        }
        return  b;
    }

}
