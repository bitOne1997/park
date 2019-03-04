package com.park.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Similarcarnumber;

public interface SimilarCarNumberService {
	int deleteByPrimaryKey(Integer id);

    int insert(Similarcarnumber record);

    int insertSelective(Similarcarnumber record);

    Similarcarnumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Similarcarnumber record);

    int updateByPrimaryKey(Similarcarnumber record);
    
    List<Similarcarnumber> selectBySimilarCarNumber(String similarNumber);
    
    List<Similarcarnumber> selectBySimilarCarNumberAndPark(String similarNumber,int parkId);
    
    List<Similarcarnumber> selectByRealCarNumber(String realNumber);
    
    List<Similarcarnumber> selectByPark(int parkId);
}
