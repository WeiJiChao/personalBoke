package com.atshunxiang.personal.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @author jw
 * @date 2019/8/21 17:09
 * @company 舜翔众邦
 * @desc 实体类
 */
public class BoKe {

    private  Integer id;

    private  String title;

    private  String body;

//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private Date deleteDate;

    private  String recordStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }


    @Override
    public String toString() {
        return "BoKe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createDate=" + createDate +
                ", deleteDate=" + deleteDate +
                ", recordStatus='" + recordStatus + '\'' +
                '}';
    }
}
