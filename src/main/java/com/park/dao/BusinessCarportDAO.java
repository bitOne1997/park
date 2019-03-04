package com.park.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.BusinessCarport;
import com.park.model.BusinessCarportDetail;
import com.park.model.PosChargeData;


@Repository
public interface BusinessCarportDAO {

	public List<BusinessCarport> getBusinessCarports();
	
	public List<BusinessCarport> getBusinessCarportByParkId(int parkId, int low, int count);
	
	public BusinessCarport getBusinessCarportById(int id);
	
	public BusinessCarport getBusinessCarportByMacId(int macId);
	
	public int getParkLeftPort(int parkId);
	
	public int getBusinessCarportCount();
	
	public int checkMacExist(int parkId, int macId);
	
	public int getParkBusinessCarportCount(int parkId);
	
	public List<BusinessCarportDetail> getBusinessCarportDetail(@Param("low")int low, @Param("count")int count);
	
	public List<BusinessCarportDetail> getParkBusinessCarportDetail(@Param("low")int low, @Param("count")int count, @Param("parkId")int parkId);
	
	public int updateBusinessCarportStatus(int macId, int status, Date date);
	
	public int insertBusinessCarport(BusinessCarport businessCarport);
	
	public int insertBusinessCarportNum(@Param("parkId")int parkid,@Param("carportnum")int carportnum);
	
	public int updateBusinessCarport(BusinessCarport businessCarport);
	
	public int deleteBusinessCarport(int id);
	
}
