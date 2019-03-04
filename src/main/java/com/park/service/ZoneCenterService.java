package com.park.service;

import java.util.List;

import com.park.model.Zonecenter;

public interface ZoneCenterService {
	int deleteByPrimaryKey(Integer id);

    int insert(Zonecenter record);

    int insertSelective(Zonecenter record);

    Zonecenter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Zonecenter record);

    int updateByPrimaryKey(Zonecenter record);
    
    int getCount();
    
    List<Zonecenter> getByStartAndCount(int start,int count);
    
    List<Zonecenter> getByUserName(String username);
}
