package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Street;


public interface StreetDAO {
	
    int deleteByPrimaryKey(Integer id);
    
    //多选删除
    int deleteByPrimaryKeyId(int[] array);

    int insert(Street record);

    int insertSelective(Street record);

    Street selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Street record);

    int updateByPrimaryKey(Street record);
    
    int getCount();
    
    List<Street> getByStartAndCount(@Param("start")int start,@Param("count")int count);
    
    List<Street> getByArea(@Param("areaId")int areaId);
    
}