package com.atshunxiang.personal.dao;



import com.atshunxiang.personal.entity.BoKe;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface DAO {

	 List<BoKe> list();

	 void add(String bokeTitle, String bokeBody, Date date, String recordStatus);

	 void deleteBoke(Integer bokeId, Date date, String recordStatus);
}
