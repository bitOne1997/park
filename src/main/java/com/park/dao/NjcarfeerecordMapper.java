package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Njcarfeerecord;

public interface NjcarfeerecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Njcarfeerecord record);

    int insertSelective(Njcarfeerecord record);

    Njcarfeerecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Njcarfeerecord record);

    int updateByPrimaryKey(Njcarfeerecord record);
    
    List<Njcarfeerecord> selectByCarNumber(@Param("carNumber")String carNumber);
    
    List<Njcarfeerecord> selectByTradeNumber(@Param("tradeNumber")String tradeNumber);
    
    List<Njcarfeerecord> selectByCount(@Param("count")int count);
}