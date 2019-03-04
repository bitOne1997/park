package com.park.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.GongzxRecord;
import com.park.model.PosChargeData;


public interface GongzxRecordService {
	public int gongcount();
	
	public List<GongzxRecord> getByParkusername(String userName);
	
	public List<GongzxRecord> getByParkAuthority(String userName);
	
	public List<GongzxRecord> getByParkadmin(String userName);
	
	public List<GongzxRecord> getByParkDatetime(String carNumber,Date startDate,Date endDate);
	
	public List<GongzxRecord> getPageByParkId(int parkId,int start,int count);
	
	public List<GongzxRecord> getPageByPark(String username,int start,int count);
	
	public List<GongzxRecord> getPageByParkusername(String username,int start,int count);
	
	public List<GongzxRecord> getByCarNumber(String carNumber);
	
	public List<GongzxRecord> getByCarNumberAndPN(String carNumber,String parkName);
	
	public List<GongzxRecord> getByCarNumberAndPark(String carNumber,int parkId);
	
	public List<GongzxRecord> getByParkName(String parkName);
	
	public int insert(GongzxRecord record);
	
	public int update(GongzxRecord record);
	
	List<GongzxRecord> getByParkAndDayRange(int parkId, String startDate,String endDate) throws ParseException;
	
	public List<GongzxRecord> getByRange(int parkId,Date startDate,Date endDate);
		
	//查询收费总金额统计
	public String getByDateAndParkCount(int parkId,String startDate,String endDate);
	
	public Map<String, Object> getParkChargeByDay(int parkId, String day);
	
	public List<GongzxRecord> selectPosdataByParkAndRange(Date startDay, Date endDay,int parkId);
}
