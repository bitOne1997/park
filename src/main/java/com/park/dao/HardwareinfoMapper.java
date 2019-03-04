package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Hardwareinfo;


public interface HardwareinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hardwareinfo record);

    int insertSelective(Hardwareinfo record);

    Hardwareinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hardwareinfo record);

    int updateByPrimaryKey(Hardwareinfo record);
    
    List<Hardwareinfo> getAll();
    
    List<Hardwareinfo> getByParkId(@Param("parkId") int parkId);
}