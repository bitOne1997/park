package com.park.service;

import java.util.List;
import java.util.Map;

import com.park.model.Feecriteriontopark;

public interface FeecriterionToParkService {

	int deleteByPrimaryKey(Integer id);

    int insert(Feecriteriontopark record);

    int insertSelective(Feecriteriontopark record);

    Feecriteriontopark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feecriteriontopark record);

    int updateByPrimaryKey(Feecriteriontopark record);
    
    List<Map<String, Object>> getByPark(int parkId);
    
    List<Feecriteriontopark> getByParkAndType(int parkId,int carType);
}
