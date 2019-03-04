package com.park.service;

import java.util.List;

import com.park.model.AlipayChargeInfo;
import com.park.model.Alipayrecord;
import com.park.model.PosChargeData;

public interface AlipayrecordService {
	 
		int deleteByPrimaryKey(Integer id);

	    int insert(Alipayrecord record);

	    int insertSelective(Alipayrecord record);

	    Alipayrecord selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Alipayrecord record);

	    int updateByPrimaryKey(Alipayrecord record);
	    
	    List<Alipayrecord> getByOutTradeNO(String outTradeNO);
	    
	    List<Alipayrecord> getByAliTradeNO(String aliTradeNo);
	    
	    List<Alipayrecord> getByPosChargeId(int poschargeId );
	    
	    AlipayChargeInfo getChargeDataByCarNumber(String carNumber) throws Exception;
}
