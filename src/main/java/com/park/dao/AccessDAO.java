package com.park.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.Access;
import com.park.model.AccessDetail;

@Repository
public interface AccessDAO {

	// public List<Access> getAccesses();

	public List<Map<String, Object>> getHourCountByPark(@Param("parkId") int parkId, @Param("date") String date,
			@Param("table") String table);

	public List<Map<String, Object>> getHourCountByChannel(@Param("parkId") int parkId, @Param("date") String date,
			@Param("table") String table);

	public List<Map<String, Object>> getDayCountByPark(@Param("parkId") int parkId, @Param("date") String date,
			@Param("table") String table);

	public List<Map<String, Object>> getChannelHourCount(@Param("macId") int macId, @Param("date") String date,
			@Param("table") String table);

	public int getParkIdByChanelId(int channelId);
	
	public Access getAccessInvalidate(@Param("parkId") int parkId, @Param("table") String table,@Param("date") String date);

	public int insertAccess(@Param("item") Access item, @Param("table") String table);

	public int getParkAccessCount(@Param("parkId") int parkId, @Param("table") String table);
	
	public int getParkAccessCountToday(@Param("parkId") int parkId, @Param("table") String table,@Param("date")String date);


	public List<AccessDetail> getParkAccessDetail(@Param("low") int low, @Param("count") int count,
			@Param("parkId") int parkId, @Param("table") String table);

	public int getAllAccessCount(@Param("xmo") int xmo, @Param("ymonth") int ymonth);

	public int getAccessCountByDate(@Param("xmo") int xmo, @Param("ymonth") int ymonth,
			@Param("accessDate") String accessDate);
}
