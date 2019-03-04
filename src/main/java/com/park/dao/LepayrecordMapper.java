package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Lepayrecord;

public interface LepayrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lepayrecord record);

    int insertSelective(Lepayrecord record);

    Lepayrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lepayrecord record);

    int updateByPrimaryKey(Lepayrecord record);
    
    int getAmount();
    
    List<Lepayrecord> getByCount(@Param("start")Integer start,@Param("count")Integer count);
    
    Lepayrecord getByOutTradeNo(@Param("outTradeNo")String outTradeNo);
}