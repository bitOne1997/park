package com.park.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.park.model.Outsideparkinfo;

public interface OutsideParkInfoService {
	public List<Map<String, Object>> getInfoZoneCenter(int start, int count);

	public List<Map<String, Object>> getInfoArea(int zoneid);

	public List<Map<String, Object>> getInfoStreet(int areaid);

	public List<Map<String, Object>> getInfoPark(int streetid);

	int deleteByPrimaryKey(Integer id);

	int insert(Outsideparkinfo record);

	int insertSelective(Outsideparkinfo record);

	Outsideparkinfo selectByPrimaryKey(Integer id);
	
	Outsideparkinfo getByParkidAndDate(int parkId);
	
	Outsideparkinfo getByParkidAndDate(int parkId,Date date);

	int updateByPrimaryKeySelective(Outsideparkinfo record);

	int updateByPrimaryKey(Outsideparkinfo record);
	
	public void insertDayParkInfo();
}
