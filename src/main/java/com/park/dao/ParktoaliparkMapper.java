package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Parktoalipark;

public interface ParktoaliparkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Parktoalipark record);

    int insertSelective(Parktoalipark record);

    Parktoalipark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Parktoalipark record);

    int updateByPrimaryKey(Parktoalipark record);
    
    List<Parktoalipark> getByAliParkId(@Param("aliparkingId")String aliparkingId);
    
    List<Parktoalipark> getByParkId(@Param("parkId")Integer parkId);
}