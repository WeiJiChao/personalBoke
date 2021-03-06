package com.jwBlog.personal.service;

import com.jwBlog.personal.bean.BoKeDeleteRequest;
import com.jwBlog.personal.bean.BoKeNewRequest;
import com.jwBlog.personal.entity.BoKe;

import java.util.List;

/**
 * @author jw
 * @date 2019/8/21 15:53
 * @company 舜翔众邦
 * @desc
 */

public interface BoKeActionService {


    /**
     * 博客列表
     */
    List<BoKe> list() throws Exception;


    /**
     * 博客新增
     */
    void add(BoKeNewRequest boKeNewRequest);

    void deleteBoke(BoKeDeleteRequest boKeDeleteRequest);
}
