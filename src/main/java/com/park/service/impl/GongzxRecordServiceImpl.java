package com.park.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.GongzxRecordDao;
import com.park.model.Constants;
import com.park.model.GongzxRecord;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.GongzxRecordService;
import com.park.service.ParkService;

@Transactional
@Service
public class GongzxRecordServiceImpl implements GongzxRecordService{
	@Autowired
	private GongzxRecordDao gongzxrecorddao;
	
	@Autowired
	ParkService parkService;

	//查询总记录数
	@Override
	public int gongcount() {
		// TODO Auto-generated method stub
		return gongzxrecorddao.gongcount();
	}
	
	//根据parkId查询记录分页
	@Override
	public List<GongzxRecord> getPageByParkId(int parkId, int start, int count) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getPageByParkId(parkId, start, count);
	}
	
	//用户名等于 admin查询记录
	@Override
	public List<GongzxRecord> getPageByPark(String username, int start, int count) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getPageByPark(username, start, count);
	}
	
	//用户名不等于 admin查询记录
	@Override
	public List<GongzxRecord> getPageByParkusername(String username, int start, int count) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getPageByParkusername(username, start, count);
	}
	
	@Override
	public List<GongzxRecord> getByParkAuthority(String userName) {
		// TODO Auto-generated method stub
		List<Park> parkList = parkService.getParks();
		if (userName != null)
			parkList = parkService.filterPark(parkList, userName);
		int num = 120 / parkList.size();
		if (num < 1) {
			num = 2;
		}
		List<GongzxRecord> gongzxRecord = new ArrayList<GongzxRecord>();
		for (Park park : parkList) {
			List<GongzxRecord> tmgongzxRecord = getPageByParkId(park.getId(), 0, num);
			gongzxRecord.addAll(tmgongzxRecord);
		}
		return gongzxRecord;
	}

	//查等于admin记录
	@Override
	public List<GongzxRecord> getByParkadmin(String userName) {
		// TODO Auto-generated method stub
		List<Park> parkList = parkService.getParks();
		if (userName != null)
			parkList = parkService.filterPark(parkList, userName);
		int num = 120 / parkList.size();
		if (num < 1) {
			num = 2;
		}
		List<GongzxRecord> tmgongzxRecord = getPageByPark(userName, 0, num);
			
		return tmgongzxRecord;
	}
	//查不等于admin记录
	@Override
	public List<GongzxRecord> getByParkusername(String userName) {
		List<Park> parkList = parkService.getParks();
		if (userName != null)
			parkList = parkService.filterPark(parkList, userName);
		int num = 120 / parkList.size();
		if (num < 1) {
			num = 2;
		}
		
		List<GongzxRecord> tmgongzxRecord = getPageByParkusername(userName, 0, num);
		return tmgongzxRecord;
	}



	@Override
	public List<GongzxRecord> getByCarNumber(String carNumber) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByCarNumber(carNumber);
	}

	@Override
	public List<GongzxRecord> getByCarNumberAndPark(String carNumber, int parkId) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByCarNumberAndPark(carNumber, parkId);
	}

	@Override
	public List<GongzxRecord> getByParkName(String parkName) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByParkName(parkName);
	}

	@Override
	public int insert(GongzxRecord record) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.insert(record);
	}

	@Override
	public int update(GongzxRecord record) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.update(record);
	}
	
	//财务对账根据时间段导出
	@Override
	public List<GongzxRecord> getByParkAndDayRange(int parkId, String startDate, String endDate) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date dstartDate = sFormat.parse(startDate + " 00:00:00");
		Date dendDate = sFormat.parse(endDate + " 23:59:59");
		 List<GongzxRecord> aa= gongzxrecorddao.getByRange(parkId, dstartDate, dendDate);
		return gongzxrecorddao.getByRange(parkId, dstartDate, dendDate);
	}
	//财务对账导出
	@Override
	public List<GongzxRecord> getByRange(int parkId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByRange(parkId, startDate, endDate);
	}

	@Override
	public String getByDateAndParkCount(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByDateAndParkCount(parkId, startDate, endDate);
	}

	@Override
	public Map<String, Object> getParkChargeByDay(int parkId, String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<GongzxRecord> gongzxRecord = selectPosdataByParkAndRange(parsedStartDay, parsedEndDay, parkId);
		Map<String, Object> retmap = new HashMap<>();
		float chargeTotal = 0;
		float realpay=0;
		for (GongzxRecord gongzx : gongzxRecord) {
			chargeTotal += gongzx.getShouldCharge();
			realpay+=gongzx.getRealPay();
		}
		retmap.put("totalMoney", chargeTotal);
		retmap.put("reallPay", realpay);
		return retmap;
	}

	@Override
	public List<GongzxRecord> selectPosdataByParkAndRange(Date startDay, Date endDay, int parkId) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.selectPosdataByParkAndRange(startDay, endDay, parkId);
	}

	@Override
	public List<GongzxRecord> getByParkDatetime(String carNumber,Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByParkDatetime(carNumber,startDate, endDate);
	}

	@Override
	public List<GongzxRecord> getByCarNumberAndPN(String carNumber, String parkName) {
		// TODO Auto-generated method stub
		return gongzxrecorddao.getByCarNumberAndPN(carNumber, parkName);
	}

}
