package com.park.dao;

import com.park.model.PoschargedataNew;

public interface PoschargedataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PoschargedataNew record);

    int insertSelective(PoschargedataNew record);

    PoschargedataNew selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PoschargedataNew record);

    int updateByPrimaryKey(PoschargedataNew record);
}