package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Parkext;

public interface ParkextMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Parkext record);

    int insertSelective(Parkext record);

    Parkext selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Parkext record);

    int updateByPrimaryKey(Parkext record);
    
    List<Parkext> selectByParkid(@Param("parkId")int parkId);
}