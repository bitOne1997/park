package com.park.service;

import java.util.List;

import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;

import com.park.model.Lepayrecord;

public interface LepayRecordService {
	
	  	int deleteByPrimaryKey(Integer id);

	    int insert(Lepayrecord record);

	    int insertSelective(Lepayrecord record);

	    Lepayrecord selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Lepayrecord record);

	    int updateByPrimaryKey(Lepayrecord record);
	    
	    int getAmount();
	    
	    List<Lepayrecord> getByCount(int start,int count);
	    
	    Lepayrecord getByOutTradeNo(String outTradeNo);
}
