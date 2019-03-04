package com.park.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.CarportStatusDetail;

public interface CarportStatusDetailDAO {
	
	public int count();
	public List<CarportStatusDetail> getByCarportId(int carportId);
	
	public List<CarportStatusDetail> limitGet(int start, int len);
	
	public List<CarportStatusDetail> getDetailByCarportId(int carportId);
	
	public CarportStatusDetail getLatestDetailByCarportId(int carportId);
	
	public List<CarportStatusDetail> getDayCarportStatusDetail(int carportId, Date day, Date nextDay);
	
	public int insert(CarportStatusDetail detail);
	
	public int update(CarportStatusDetail detail);
	
	public List<Map<String, Object>> getDetailByParkIdAndDateRange(@Param("parkId") int parkId,@Param("startDate") String startDate,@Param("endDate") String endDate);
	

}
