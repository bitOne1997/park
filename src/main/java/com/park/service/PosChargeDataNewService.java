package com.park.service;

import com.park.model.PoschargedataNew;

public interface PosChargeDataNewService {

	    int deleteByPrimaryKey(Integer id);

	    int insert(PoschargedataNew record);

	    int insertSelective(PoschargedataNew record);

	    PoschargedataNew selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(PoschargedataNew record);

	    int updateByPrimaryKey(PoschargedataNew record);
}
