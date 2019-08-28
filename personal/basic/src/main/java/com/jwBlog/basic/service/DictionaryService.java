package com.jwBlog.basic.service;

import com.jwBlog.base.service.BaseService;
import com.jwBlog.basic.entity.DictionaryEntity;

import java.util.HashMap;

/**
* @author dongxu
* @date 2019-02-19
*/
public interface DictionaryService extends BaseService<DictionaryEntity> {
    String getKeyByGroupAndTitle(String groupName, String title);
    HashMap<String, Object> getConfByGroup(String groupName);
}