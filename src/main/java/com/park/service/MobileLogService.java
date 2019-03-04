package com.park.service;

import org.springframework.stereotype.Service;

import com.park.model.Mobilelog;
public interface MobileLogService {
	  int deleteByPrimaryKey(Integer id);

	    int insert(Mobilelog record);

	    int insertSelective(Mobilelog record);

	    Mobilelog selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Mobilelog record);

	    int updateByPrimaryKey(Mobilelog record);
}
