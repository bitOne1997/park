package com.park.service;

import java.util.List;

import com.park.model.Alipayinfo;

public interface AlipayInfoService {

	int deleteByPrimaryKey(Integer id);

    int insert(Alipayinfo record);

    int insertSelective(Alipayinfo record);

    Alipayinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Alipayinfo record);

    int updateByPrimaryKey(Alipayinfo record);
    
    List<Alipayinfo> getbyParkId(int parkId);
    
    List<Alipayinfo> getbyOutParkKey(String outParkKey);
    
    List<Alipayinfo> getByCount(Integer start,Integer count);
}
