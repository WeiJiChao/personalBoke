package com.atshunxiang.personal.bean;

/**
 * @author jw
 * @date 2019/8/22 12:25
 * @company 舜翔众邦
 * @desc
 */
public class BoKeNewRequest {

    private String bokeTitle;

    private  String bokeBody;

    public String getBokeTitle() {
        return bokeTitle;
    }

    public void setBokeTitle(String bokeTitle) {
        this.bokeTitle = bokeTitle;
    }

    public String getBokeBody() {
        return bokeBody;
    }

    public void setBokeBody(String bokeBody) {
        this.bokeBody = bokeBody;
    }

    @Override
    public String toString() {
        return "BoKeNewRequest{" +
                "bokeTitle='" + bokeTitle + '\'' +
                ", bokeBody='" + bokeBody + '\'' +
                '}';
    }
}
