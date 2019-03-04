package com.park.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.Posdata;

public interface PosdataService {
	public int insert(Posdata record);

	public List<Posdata> selectAll();

	public List<Posdata> selectPosdataByPage(int low, int count);

	public List<Posdata> selectPosdataByPageAndPark(int parkId, int low, int count);

	public int getPosdataCount();

	public int getPosdataCountByPark(int parkId);

	public List<Posdata> selectPosdataByParkAndRange(String parkName, Date startDay, Date endDay);

	public List<Posdata> getPosdataByCarportAndRange(String parkName, String carportId, Date startDay, Date endDay);

	public Map<String, Object> getCarportCharge(int carportId, Date startDay, Date endDay);

	public Map<String, Object> getParkCharge(int parkId, Date startDay, Date endDay);

	public List<Map<String, Object>> getCountByCard();

	public Map<String, Object> getParkChargeByDay(int parkId, String day);

	public Map<String, Object> getCarpotChargeByDay(int parkId, String carportId, String day);
}
