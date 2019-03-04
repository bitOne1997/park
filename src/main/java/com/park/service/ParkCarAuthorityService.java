package com.park.service;

import java.util.List;

import com.park.model.Parkcarauthority;

public interface ParkCarAuthorityService {

	int deleteByPrimaryKey(Integer id);

	int insert(Parkcarauthority record);

	int insertSelective(Parkcarauthority record);

	Parkcarauthority selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Parkcarauthority record);

	int updateByPrimaryKey(Parkcarauthority record);

	List<Parkcarauthority> getByParkId(Integer parkId);
	
    List<Parkcarauthority> getByStartAndCount(Integer start,Integer count);
    
    int InitRecords(Integer parkId);
}
