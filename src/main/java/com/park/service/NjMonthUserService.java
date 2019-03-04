package com.park.service;

import com.park.model.Njmonthuser;

public interface NjMonthUserService {
	int deleteByPrimaryKey(Integer id);

    int insert(Njmonthuser record);

    int insertSelective(Njmonthuser record);

    Njmonthuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Njmonthuser record);

    int updateByPrimaryKey(Njmonthuser record);
}
