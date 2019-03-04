package com.park.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.Feecriteriontopark;

public interface FeecriteriontoparkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feecriteriontopark record);

    int insertSelective(Feecriteriontopark record);

    Feecriteriontopark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feecriteriontopark record);

    int updateByPrimaryKey(Feecriteriontopark record);
    
    List<Map<String, Object>> getByPark(@Param("parkId") int parkId);
    
    List<Feecriteriontopark> getByParkAndType(@Param("parkId") int parkId,@Param("carType")int carType);
}