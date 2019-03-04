package com.park.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.Monthuserpark;

public interface MonthuserparkDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Monthuserpark record);

    int insertSelective(Monthuserpark record);

    Monthuserpark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Monthuserpark record);

    int updateByPrimaryKey(Monthuserpark record);
    
    int deleteByUserIdAndParkId(Monthuserpark record);
    
    List<Map<String, Object>> getOwnParkName(@Param("userId")int userId);
    
    List<Map<String, Object>> getUsersByParkId(@Param("parkId")int parkId);
}