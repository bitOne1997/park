package com.park.service;

import java.util.List;

import com.park.model.Parktoalipark;

public interface ParkToAliparkService {

    int deleteByPrimaryKey(Integer id);

    int insert(Parktoalipark record);

    int insertSelective(Parktoalipark record);

    Parktoalipark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Parktoalipark record);

    int updateByPrimaryKey(Parktoalipark record);
    
    List<Parktoalipark> getByAliParkId(String aliparkingId);
    
    List<Parktoalipark> getByParkId(Integer parkId);
}
