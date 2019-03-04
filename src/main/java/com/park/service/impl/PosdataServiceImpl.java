package com.park.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.PosdataDAO;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.model.Posdata;
import com.park.service.ParkService;
import com.park.service.PosChargeDataService;
import com.park.service.PosdataService;
@Transactional
@Service
public class PosdataServiceImpl implements PosdataService {
	@Autowired
	private PosdataDAO posdataDAO;
	@Autowired
	private ParkService parkService;
	@Autowired PosChargeDataService posChargeService;
	@Override
	public int insert(Posdata record) {
		// TODO Auto-generated method stub
		return posdataDAO.insert(record);
	}
	@Override
	public List<Posdata> selectAll() {
		// TODO Auto-generated method stub
		return posdataDAO.selectAll();
	}
	@Override
	public List<Posdata> selectPosdataByPage(int low, int count) {
		// TODO Auto-generated method stub
		return posdataDAO.selectPosdataByPage(low, count);
	}
	@Override
	public int getPosdataCount() {
		// TODO Auto-generated method stub
		return posdataDAO.getPosdataCount();
	}
	
	@Override
	public Map<String, Object> getCarportCharge(int carportId, Date startDay, Date endDay) {
		List<Posdata> carportData = getCarportData(carportId, startDay, endDay);
		return null;
	}
	
	private List<Posdata> getCarportData(int carportId, Date startDay, Date endDay){
		return posdataDAO.getCarportData(carportId, startDay, endDay);
		
	}
	
	private List<Posdata> getParkData(int parkId, Date startDay, Date endDay){
		return posdataDAO.getParkData(parkId, startDay, endDay);
	}
	
	@Override
	public Map<String, Object> getParkCharge(int parkId, Date startDay, Date endDay) {
	//	List<Posdata> parkData = getParkData(parkId, startDay, endDay);
		return null;
	}
	@Override
	public List<Posdata> selectPosdataByParkAndRange(String parkName, Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return posdataDAO.selectPosdataByParkAndRange(parkName, startDay, endDay);
	}
	@Override
	public Map<String, Object> getParkChargeByDay(int parkId, String day) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		Date parsedEndDay  = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		Park park = parkService.getParkById(parkId);
		String parkName=park.getName();
		
			return posChargeService.getParkChargeByDay(parkId, day);
		
//		Map<String, Object> retmap=new HashMap<>();
//		float chargeTotal=0;
//		float realReceiveMoney=0;
//		for (Posdata posdata2 : posdata) {
//			if (posdata2.getMode()==1) {
//				chargeTotal+=posdata2.getMoney().floatValue();
//				realReceiveMoney+=posdata2.getGiving().floatValue()+posdata2.getRealmoney().floatValue()-
//						posdata2.getReturnmoney().floatValue();
//			}
//			
//		}
//		retmap.put("totalMoney", chargeTotal);
//		retmap.put("realMoney", realReceiveMoney);
//		return retmap;
	}
	@Override
	public List<Posdata> getPosdataByCarportAndRange(String parkName, String carportId, Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return posdataDAO.getPosdataByCarportAndRange(parkName, carportId, startDay, endDay);
	}
	@Override
	public Map<String, Object> getCarpotChargeByDay(int parkId, String carportId, String day) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		Date parsedEndDay  = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		Park park = parkService.getParkById(parkId);
		String parkName=park.getName();
		List<Posdata> posdata=getPosdataByCarportAndRange(parkName,carportId,parsedStartDay,parsedEndDay);
		List<PosChargeData> posChargeDatas=posChargeService.selectPosdataByParkAndRangeAndCarportNumber(parsedStartDay, parsedEndDay, parkId, Integer.parseInt(carportId));
		Map<String, Object> retmap=new HashMap<>();
//		float chargeTotal=0;
//		float realReceiveMoney=0;
//		for (Posdata posdata2 : posdata) {
//			if (posdata2.getMode()==1) {
//				chargeTotal+=posdata2.getMoney().floatValue();
//				realReceiveMoney+=posdata2.getGiving().floatValue()+posdata2.getRealmoney().floatValue()-
//						posdata2.getReturnmoney().floatValue();
//			}
//			
//		}
		float chargeTotal=0;
		float realReceiveMoney=0;
		for(PosChargeData posData:posChargeDatas){
			chargeTotal+=posData.getChargeMoney();
			realReceiveMoney+=posData.getGivenMoney()+posData.getPaidMoney()-posData.getChangeMoney();
		}
		retmap.put("totalMoney", chargeTotal);
		retmap.put("realMoney", realReceiveMoney);
		return retmap;
	}
	@Override
	public List<Map<String, Object>> getCountByCard() {
		// TODO Auto-generated method stub
		return posdataDAO.getCountByCard();
	}
	@Override
	public List<Posdata> selectPosdataByPageAndPark(int parkId, int low, int count) {
		// TODO Auto-generated method stub
		Park park = parkService.getParkById(parkId);
		String parkName=park.getName();
		return posdataDAO.selectPosdataByPageAndPark(parkName, low, count);
	}
	@Override
	public int getPosdataCountByPark(int parkId) {
		// TODO Auto-generated method stub
		Park park = parkService.getParkById(parkId);
		String parkName=park.getName();
		return posdataDAO.getPosdataCountByPark(parkName);
	}

}
