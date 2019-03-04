package com.park.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.GongzxRecord;
import com.park.model.PosChargeData;


@Repository
public interface GongzxRecordDao {
	public int gongcount();
	
	public List<GongzxRecord> getByParkusername(String userName);
	
	public List<GongzxRecord> getByParkAuthority(String userName);
	
	public List<GongzxRecord> getByParkadmin(String userName);
	
	public List<GongzxRecord> getByParkDatetime(@Param("carNumber")String carNumber,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public List<GongzxRecord> getPageByParkId(@Param("parkId")int parkId,@Param("start")int start,@Param("count")int count);
	
	public List<GongzxRecord> getPageByPark(@Param("username")String username,@Param("start")int start,@Param("count")int count);
	
	public List<GongzxRecord> getPageByParkusername(@Param("username")String username,@Param("start")int start,@Param("count")int count);
	
	public List<GongzxRecord> getByCarNumber(@Param("carNumber")String carNumber);
	
	public List<GongzxRecord> getByCarNumberAndPN(@Param("carNumber")String carNumber,@Param("parkName")String parkName);
	
	public List<GongzxRecord> getByCarNumberAndPark(@Param("carNumber")String carNumber,@Param("parkId")int parkId);
	
	public List<GongzxRecord> getByParkName(@Param("parkName")String parkName);
	
	public int update(GongzxRecord record);
	
	public int insert(GongzxRecord record);
	
	public List<GongzxRecord> getByRange(@Param("parkId")int parkId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//查询收费总金额统计
	public String getByDateAndParkCount(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	public List<GongzxRecord> selectPosdataByParkAndRange(@Param("startDay") Date startDay, @Param("endDay") Date endDay,@Param("parkId")int parkId);
}
