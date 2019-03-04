package com.park.service;


import com.park.model.Parkcarauthority2;

public interface ParkCarAuthority2Service {

    int deleteByPrimaryKey(Integer id);

    int insert(Parkcarauthority2 record);

    int insertSelective(Parkcarauthority2 record);

    Parkcarauthority2 selectByPrimaryKey(Integer id);
    
    Parkcarauthority2 selectByPark(int parkId);

    int updateByPrimaryKeySelective(Parkcarauthority2 record);

    int updateByPrimaryKey(Parkcarauthority2 record);
}
