package com.park.service;

import java.util.List;
import java.util.Map;

import com.park.model.Access;
import com.park.model.AccessDetail;

public interface AccessService {

	public String insertAccess(Access item);

	public int getAccessCount(Integer parkId);
	
	public int getAccessCountToday(Integer parkId,String date);
	
	public Access getAccessInvalidate(Integer parkId,String date);

	public Map<String, Map<Integer, Integer>> getHourCountByPark(int parkId, String date);

	public Map<String, Map<Integer, Integer>> getHourCountByChannel(int parkId, String date);

	public Map<String, Map<Integer, Integer>> getDayCountByPark(int parkId, String date);

	public Map<Integer, Integer> getChannelHourCount(String mac, int macId, String date);

	public int getParkIdByChanelId(int channelId);

	public String insertAccessList(List<Access> accesses);

	public int getAllAccessCount(int xmo, int ymonth);

	// public String updateAccess(Access access);
	public int getAccessCountByDate(int xmo, int ymonth, String accessDate);

	public List<AccessDetail> getAccessDetail(int low, int count, Integer parkId);

	public List<AccessDetail> getAccessForExcel(int parkId, int monthNum);
}
