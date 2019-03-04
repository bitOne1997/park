package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Alipayinfo;

public interface AlipayinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Alipayinfo record);

    int insertSelective(Alipayinfo record);

    Alipayinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Alipayinfo record);

    int updateByPrimaryKey(Alipayinfo record);
    
    List<Alipayinfo> getbyParkId(@Param("parkId")int parkId);
    
    List<Alipayinfo> getbyOutParkKey(@Param("outParkKey")String outParkKey);
    
    List<Alipayinfo> getbyCount(@Param("start")Integer start,@Param("count")Integer count);
}