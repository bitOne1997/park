package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Similarcarnumber;

public interface SimilarcarnumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Similarcarnumber record);

    int insertSelective(Similarcarnumber record);

    Similarcarnumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Similarcarnumber record);

    int updateByPrimaryKey(Similarcarnumber record);
    
    List<Similarcarnumber> selectBySimilarCarNumber(@Param("similarNumber")String similarNumber);
    
    List<Similarcarnumber> selectBySimilarCarNumberAndPark(@Param("similarNumber")String similarNumber,@Param("parkId")int parkId);
    
    List<Similarcarnumber> selectByRealCarNumber(@Param("realNumber")String realNumber);
    
    List<Similarcarnumber> selectByPark(@Param("parkId")int parkId);
}