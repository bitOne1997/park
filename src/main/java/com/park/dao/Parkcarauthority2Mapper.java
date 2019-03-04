package com.park.dao;

import org.apache.ibatis.annotations.Param;

import com.park.model.Parkcarauthority2;

public interface Parkcarauthority2Mapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Parkcarauthority2 record);

    int insertSelective(Parkcarauthority2 record);

    Parkcarauthority2 selectByPrimaryKey(Integer id);
    
    Parkcarauthority2 selectByPark(@Param("parkId")int parkId);

    int updateByPrimaryKeySelective(Parkcarauthority2 record);

    int updateByPrimaryKey(Parkcarauthority2 record);
}