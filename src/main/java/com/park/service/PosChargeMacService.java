package com.park.service;

import java.util.List;

import com.park.model.Poschargemac;

public interface PosChargeMacService {

	 int deleteByPrimaryKey(Integer id);

	    int insert(Poschargemac record);

	    int insertSelective(Poschargemac record);

	    Poschargemac selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Poschargemac record);

	    int updateByPrimaryKey(Poschargemac record);
	    
	    int updateByPosChargeId(Poschargemac record);
	    
	    List<Poschargemac> selectByMac(int macId,int count);
}
