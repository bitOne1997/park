package com.park.service;

import java.util.List;

import com.park.model.Njcarfeerecord;

public interface NjCarFeeRecordService {
	  int deleteByPrimaryKey(Integer id);

	    int insert(Njcarfeerecord record);

	    int insertSelective(Njcarfeerecord record);

	    Njcarfeerecord selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Njcarfeerecord record);

	    int updateByPrimaryKey(Njcarfeerecord record);
	    
	    List<Njcarfeerecord> selectByCarNumber(String carNumber);
	    
	    List<Njcarfeerecord> selectByTradeNumber(String tradeNumber);
	    
	    List<Njcarfeerecord> selectByCount(int count);
}
