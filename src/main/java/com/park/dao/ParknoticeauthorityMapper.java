package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Parknoticeauthority;

public interface ParknoticeauthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Parknoticeauthority record);

    int insertSelective(Parknoticeauthority record);

    Parknoticeauthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Parknoticeauthority record);

    int updateByPrimaryKey(Parknoticeauthority record);
    
    List<Parknoticeauthority> getByParkId(@Param("parkId")int parkId);
}