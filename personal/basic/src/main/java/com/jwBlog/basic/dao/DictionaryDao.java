package com.jwBlog.basic.dao;

import com.jwBlog.base.dao.BaseDao;
import com.jwBlog.basic.entity.DictionaryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @date 2019-02-24
 */
@Repository
public interface DictionaryDao extends BaseDao<DictionaryEntity> {

}
