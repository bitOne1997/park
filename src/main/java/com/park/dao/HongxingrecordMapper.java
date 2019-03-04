package com.park.dao;

import com.park.model.Hongxingrecord;

public interface HongxingrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hongxingrecord record);

    int insertSelective(Hongxingrecord record);

    Hongxingrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hongxingrecord record);

    int updateByPrimaryKey(Hongxingrecord record);
}