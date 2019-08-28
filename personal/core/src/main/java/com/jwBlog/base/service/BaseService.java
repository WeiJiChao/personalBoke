package com.jwBlog.base.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 */
public interface BaseService<T> {

	T queryObject(Integer id);

	List<T> queryList(Map<String, Object> map);
	Page queryListByPage(Map<String, Object> map, Pageable pageable);

    List<T> queryListByBean(T entity);

	int queryTotal(Map<String, Object> map);

	T save(T paramDictionary);

	int update(T paramDictionary);

	int delete(Integer id);

	int deleteBatch(Integer[] ids);
	/**
	 * 获取缓存数据
	 * @return
	 */
	Map<String,List<Map<String,Object>>>  queryCacheCode();
}
