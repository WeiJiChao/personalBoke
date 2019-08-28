package com.jwBlog.basic.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author jie
* @date 2019-02-19
*/
@Getter
@Setter
public class DictionaryEntity implements Serializable {
    private Integer id;
    private String groupName;
    private String confTitle;
    private String confValue;
    private String status;
    private Integer sortIndex;
}