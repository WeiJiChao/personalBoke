package com.atshunxiang.personal.bean;

/**
 * @author jw
 * @date 2019/8/21 11:01
 * @company 舜翔众邦
 * @desc
 */
public class LoginRequest {

    private  String userName;

    private  String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
