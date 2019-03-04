package com.park.service;

import java.util.List;

import com.park.model.Hardwareinfo;

public interface HardwareInfoService {

	    int deleteByPrimaryKey(Integer id);

	    int insert(Hardwareinfo record);

	    int insertSelective(Hardwareinfo record);

	    Hardwareinfo selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(Hardwareinfo record);

	    int updateByPrimaryKey(Hardwareinfo record);
	    
	    List<Hardwareinfo> getAll();
	    
	    List<Hardwareinfo> getByParkId(int parkId);
}
