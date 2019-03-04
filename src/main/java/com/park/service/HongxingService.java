package com.park.service;

import java.util.Map;

public interface HongxingService {

	public Map<String, Object> getFeeByCarNumber(String carNumber,String parkKey);
	
	public String creatPayOrder(String orderNo,String parkKey);
	
	public Boolean payOrderNotify(String paidMoney,String OrderNo,String Order,String parkKey);
}
