package com.park.service;

import java.util.List;

import com.park.model.Street;

public interface StreetService {
	 	int deleteByPrimaryKey(Integer id);
	 	
	 	//多选删除
	 	int deleteByPrimaryKeyId(String array);

	    int insert(Street record);

	    int insertSelective(Street record);

	    Street selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Street record);

	    int updateByPrimaryKey(Street record);
	    
	    int getCount();
	    
	    List<Street> getByStartAndCount(int start,int count);
	    
	    List<Street> getByArea(int areaId);
}
