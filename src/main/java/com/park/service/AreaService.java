package com.park.service;

import java.util.List;

import com.park.model.Area;

public interface AreaService {
	    int deleteByPrimaryKey(Integer id);

	    int insert(Area record);

	    int insertSelective(Area record);

	    Area selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Area record);

	    int updateByPrimaryKey(Area record);
	    
	    int getCount();
	    
	    List<Area> getByStartAndCount(int start,int count);
	    
	    List<Area> getByZoneCenterId(int zoneid);
}
