package com.park.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.Posdata;
@Repository
public interface PosdataDAO {
	int insert(Posdata record);

	List<Posdata> selectAll();

	List<Posdata> selectPosdataByPage(@Param("low") int low, @Param("count") int count);

	int getPosdataCount();
	
	int getPosdataCountByPark(@Param("parkName") String parkName);

	public List<Posdata> selectPosdataByParkAndRange(@Param("parkName") String parkName,
			@Param("startDay") Date startDay, @Param("endDay") Date endDay);

	public List<Posdata> getPosdataByCarportAndRange(@Param("parkName") String parkName,
			@Param("carportid") String carportid, @Param("startDay") Date startDay, @Param("endDay") Date endDay);

	public List<Posdata> getCarportData(int carportId, Date startDay, Date endDay);

	public List<Map<String, Object>> getCountByCard();

	public List<Posdata> getParkData(int parkId, Date startDay, Date endDay);

	public List<Posdata> selectPosdataByPageAndPark(@Param("parkName") String parkName, @Param("low") int low,
			@Param("count") int count);
}