package com.park.service;

import java.util.List;


import com.park.model.Parkext;

public interface ParkExtService {

	   int deleteByPrimaryKey(Integer id);

	    int insert(Parkext record);

	    int insertSelective(Parkext record);

	    Parkext selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Parkext record);

	    int updateByPrimaryKey(Parkext record);
	    
	    List<Parkext> selectByParkid(int parkId);
}
