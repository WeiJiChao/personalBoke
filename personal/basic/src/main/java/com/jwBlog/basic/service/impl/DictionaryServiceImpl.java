package com.jwBlog.basic.service.impl;

import com.jwBlog.basic.dao.DictionaryDao;
import com.jwBlog.basic.entity.DictionaryEntity;
import com.jwBlog.basic.service.DictionaryService;
import com.jwBlog.frame.PageUtil;
import com.jwBlog.utils.dp.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public DictionaryEntity queryObject(Integer id){
		return dictionaryDao.queryObject(id);
	}

	@Override
	public List<DictionaryEntity> queryList(Map<String, Object> map){
		List<DictionaryEntity> list= dictionaryDao.queryList(map);
		return list;
	}

	@Override
	public List<DictionaryEntity> queryListByBean(DictionaryEntity entity) {
		return dictionaryDao.queryListByBean(entity);
	}
	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(dictionaryDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);
		List<DictionaryEntity> list= dictionaryDao.queryList(map);
		return PageableExecutionUtils.getPage(list, pageable, () -> {
			return dictionaryDao.queryTotal(map);
		});
	}
	@Override
	public int queryTotal(Map<String, Object> map){
		return dictionaryDao.queryTotal(map);
	}

	@Override
	public DictionaryEntity save(DictionaryEntity dictionaryEntity){
		dictionaryDao.save(dictionaryEntity);
		return  dictionaryEntity;
	}

	@Override
	public int update(DictionaryEntity dictionaryEntity){
		return dictionaryDao.update(dictionaryEntity);
	}

	@Override
	public int delete(Integer id){
		return dictionaryDao.delete(id);
	}

	@Override
	public int deleteBatch(Integer[] ids){
		return dictionaryDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

	@Override
	public String getKeyByGroupAndTitle(String groupName, String title) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupName",groupName);
		map.put("confTitle",title);
		List<DictionaryEntity> list= dictionaryDao.queryList(map);
		return list==null||list.size()<1?"":list.get(0).getConfValue();
	}

	@Override
	public HashMap<String, Object> getConfByGroup(String groupName) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupName",groupName);
		List<DictionaryEntity> list= dictionaryDao.queryList(map);
		HashMap<String, Object> rtnMap=new HashMap<String, Object>();
		if(list!=null&&list.size()>0){
			for(DictionaryEntity dic:list){
				rtnMap.put(dic.getConfTitle(),dic.getConfValue());
			}
		}
		return rtnMap;
	}
}
