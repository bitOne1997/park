package com.park.service;

import java.util.List;


import com.park.model.Parknoticeauthority;

public interface ParkNoticeAuthorityService {

	  int deleteByPrimaryKey(Integer id);

	    int insert(Parknoticeauthority record);

	    int insertSelective(Parknoticeauthority record);

	    Parknoticeauthority selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Parknoticeauthority record);

	    int updateByPrimaryKey(Parknoticeauthority record);
	    
	    List<Parknoticeauthority> getByParkId(int parkId);
}
