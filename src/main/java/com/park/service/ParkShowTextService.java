package com.park.service;

import java.util.List;

import com.park.model.Parkshowtext;

public interface ParkShowTextService {

	   int deleteByPrimaryKey(Integer id);

	    int insert(Parkshowtext record);

	    int insertSelective(Parkshowtext record);
	    
	    int deleteByPark(Integer parkId);

	    Parkshowtext selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Parkshowtext record);

	    int updateByPrimaryKey(Parkshowtext record);
	    
	    List<Parkshowtext> getByPark(Integer parkId);
}
