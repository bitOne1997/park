package com.park.service;

import java.util.List;
import java.util.Map;

import com.park.model.Monthuserpark;

public interface MonthUserParkService {
	   int deleteByPrimaryKey(Integer id);

	    int insert(Monthuserpark record);

	    int insertSelective(Monthuserpark record);

	    Monthuserpark selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Monthuserpark record);

	    int updateByPrimaryKey(Monthuserpark record);
	    
	    int deleteByUserIdAndParkId(Monthuserpark record);
	    
	    List<Map<String, Object>> getOwnParkName(int userId);
	    
	    List<Map<String, Object>> getUsersByParkId(int parkId);
}
