package com.park.dao;

import com.park.model.Njmonthuser;

public interface NjmonthuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Njmonthuser record);

    int insertSelective(Njmonthuser record);

    Njmonthuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Njmonthuser record);

    int updateByPrimaryKey(Njmonthuser record);
}