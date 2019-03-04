package com.park.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.park.dao.AccessDAO;
import com.park.dao.BusinessCarportDAO;
import com.park.dao.HardwareDAO;
import com.park.model.Access;
import com.park.model.AccessDetail;
import com.park.model.BusinessCarport;
import com.park.model.Channel;
import com.park.model.ChannelType;
import com.park.model.Constants;
import com.park.model.Park;
import com.park.service.AccessService;
import com.park.service.ChannelService;
import com.park.service.HardwareService;
import com.park.service.ParkService;
import com.park.service.Utility;

@Transactional
@Service
public class AccessServiceImpl implements AccessService{
	
	@Autowired
	private AccessDAO accessDAO;
	
	@Autowired
	private HardwareDAO hardwareDAO;
	
	@Autowired
	private BusinessCarportDAO businessCarportDAO;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ParkService parkService;
	
	
	private String findAccessTable(int parkId){
		
		int parkIdHash = parkId % Constants.ACCESSTABLESCOUNT;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month=calendar.get(Calendar.MONTH);
		String table =  "access_"+String.format("%04d", parkIdHash)+"_"+String.format("%02d",month);
		return table;
	}

	private String findAccessTable(int parkId, String date){
		
		int parkIdHash = parkId % Constants.ACCESSTABLESCOUNT;
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int month=calendar.get(Calendar.MONTH);
		String table =  "access_"+String.format("%04d", parkIdHash)+"_"+String.format("%02d",month);
		return table;
		
	}
	
	private String findTableWithMonth(int parkId,int month){
		int parkIdHash = parkId % Constants.ACCESSTABLESCOUNT;
		String table =  "access_"+String.format("%04d", parkIdHash)+"_"+String.format("%02d",month);
		return table;
	}
	@Override
	public List<AccessDetail> getAccessDetail(int low, int count, Integer parkId) {
		if(parkId == null || parkId.intValue() < 0)
			return new ArrayList<AccessDetail>();
		else
		{
			return accessDAO.getParkAccessDetail(low, count, parkId.intValue(),findAccessTable(parkId));
			}
	}

	@Override
	public int getAccessCount(Integer parkId) {
		if(parkId == null || parkId.intValue() < 0)
			return 0;
		else
			return accessDAO.getParkAccessCount(parkId.intValue(), findAccessTable(parkId));
	}

	
	@Override
	public Map<String, Map<Integer, Integer>> getHourCountByPark(int parkId, String date) {
		List<Map<String, Object>> rets = accessDAO.getHourCountByPark(parkId, date,findAccessTable(parkId, date));
		Map<String, Map<Integer, Integer>> body = new HashMap<String, Map<Integer, Integer>>();
		
		Map<Integer, Integer> exitMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> entranceMap = new HashMap<Integer, Integer>();
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			
			if((int)item.get("channelFlag") == ChannelType.EXIT.getValue()){
				 exitMap.put((int)item.get("hour"), Integer.parseInt(item.get("count").toString()));
			}else{
				entranceMap.put((int)item.get("hour"), Integer.parseInt(item.get("count").toString()));
			}	
		}		
		body.put("exit", exitMap);
		body.put("entrance", entranceMap);
		return body;
	}

@Override
	public Map<String, Map<Integer, Integer>> getHourCountByChannel(int parkId, String date) {
		
		List<Map<String, Object>> rets = accessDAO.getHourCountByChannel(parkId, date,findAccessTable(parkId, date));
		Map<String, Map<Integer, Integer>> body = new HashMap<String, Map<Integer, Integer>>();
		
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			int macId = (int)item.get("macId");
			String channelname;
			if ((int)item.get("channelFlag")==0) {
				channelname="出口";
			}
			else {
				channelname="入口";
			}
			String channelId=channelname+":"+item.get("channelId").toString();
	//		String mac = hardwareDAO.getHardwareById(macId).getMac();
			if(!body.containsKey(channelId)){
				body.put(channelId, new HashMap<Integer, Integer>());
			}
			Map<Integer, Integer> macMap = body.get(channelId);
			macMap.put((int)item.get("hour"), Integer.parseInt(item.get("count").toString()));
				
		}
		
		return body;
	}

	@Override
	public Map<String, Map<Integer, Integer>> getDayCountByPark(int parkId, String date) {
		
		List<Map<String, Object>> rets =accessDAO.getDayCountByPark(parkId, date, findAccessTable(parkId, date));
		Map<String, Map<Integer, Integer>> body = new HashMap<String, Map<Integer, Integer>>();
		
		Map<Integer, Integer> exitMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> entranceMap = new HashMap<Integer, Integer>();
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			
			if((int)item.get("channelFlag") == ChannelType.EXIT.getValue()){
				 exitMap.put((int)item.get("day"), Integer.parseInt(item.get("count").toString()));
			}else{
				entranceMap.put((int)item.get("day"), Integer.parseInt(item.get("count").toString()));
			}
			
		}
		body.put("exit", exitMap);
		body.put("entrance", entranceMap);
		return body;
	}

/*	@Override
	public Map<String, Map<Integer, Integer>> getMonthCountByChannel(int parkId, int year) {
		List<Map<String, Object>> rets = accessDAO.getMonthCountByChannel(parkId, year);
		Map<String, Map<Integer, Integer>> body = new HashMap<String, Map<Integer, Integer>>();
		
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			int macId = (int)item.get("macId");
			String mac = hardwareDAO.getHardwareById(macId).getMac();
			if(!body.containsKey(mac)){
				body.put(mac, new HashMap<Integer, Integer>());
			}
			Map<Integer, Integer> macMap = body.get(mac);
			macMap.put((int)item.get("month"), Integer.parseInt(item.get("count").toString()));
		}
		return body;
	}*/
	
	@Autowired
	private HardwareService hardwareservice;
	@Override
	public Map<Integer, Integer> getChannelHourCount(String mac,int macId, String date) {
		List<Map<String, Object>> macinfos=hardwareservice.getInfoByMac(mac);
		Map<String, Object> macinfo=macinfos.get(0);
		int parkId=(int)macinfo.get("parkID");
		List<Map<String, Object>> rets = accessDAO.getChannelHourCount(macId, date,findAccessTable(parkId, date));
		Map<Integer, Integer> body = new HashMap<Integer, Integer>();
		
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			body.put((int)item.get("hour"), Integer.parseInt(item.get("count").toString()));
		}
		return body;
	}

	
/*	@Override
	public Map<Integer, Integer> getChannelMonthCount(int macId, int year) {
		List<Map<String, Object>> rets = accessDAO.getChannelMonthCount(macId, year);
		Map<Integer, Integer> body = new HashMap<Integer, Integer>();
		
		for(int i = 0 ; i < rets.size(); i++){
			Map<String, Object> item = rets.get(i);
			body.put((int)item.get("month"), Integer.parseInt(item.get("count").toString()));
		}
		return body;
	}*/
	
	public void updateParkPorts(int channelId){
		Channel channel = channelService.getChannelsById(channelId);
		Park park = parkService.getParkById(channel.getParkId());
		List<BusinessCarport> carports = businessCarportDAO.getBusinessCarportByParkId(park.getId(), 0, 1);
		if(carports == null || carports.size() == 0 || carports.get(0).getMacId() < 0){ 
			//carport has no hardware, use channel update left port
			int updateCount = (channel.getChannelFlag() == ChannelType.EXIT.getValue() ? 1 : -1);
			int leftPorts = park.getPortLeftCount() + updateCount;
			leftPorts = (leftPorts > park.getPortCount() ? park.getPortCount() : leftPorts);
			parkService.updateLeftPortCount(park.getId(), leftPorts < 0 ? 0 : leftPorts);
		}
	}
	
	@Override
	public String insertAccess(Access item){
		updateParkPorts(item.getChannelId());	
		Map<String, Object> map = new HashMap<String, Object>();
		int parkId = getParkIdByChanelId(item.getChannelId());
		if(accessDAO.insertAccess(item,findAccessTable(parkId)) > 0){
			map.put("status", "1001");
			map.put("message", "insert success");
		}else{
			map.put("status", "1002");
			map.put("message", "insert fail");
		}
		return new Gson().toJson(map);
	}

	@Override
	public String insertAccessList(List<Access> accesses){
		
		int sum = 0;
		for(Access access: accesses){
			int parkId = getParkIdByChanelId(access.getChannelId());
			sum += accessDAO.insertAccess(access,findAccessTable(parkId));
		}
		if(sum == accesses.size()){
			return Utility.createJsonMsg("1001", "insert success");
		}else{
			return Utility.createJsonMsg("1002", sum + " success " + accesses.size() + " fail");
		}
	}
	
	/*@Override
	public String updateAccess(Access access){
		Map<String, Object> map = new HashMap<String, Object>();

		if(accessDAO.updateAccess(access,table) > 0){
			map.put("status", "1001");
			map.put("message", "update success");
		}else{
			map.put("status", "1002");
			map.put("message", "update fail");
		}
		return new Gson().toJson(map);
	}*/
	
/*	@Override
	public String deleteAccess(int Id){
		Map<String, Object> map = new HashMap<String, Object>();

		if(accessDAO.deleteAccess(Id,table)> 0){
			map.put("status", "1001");
			map.put("message", "delete success");
		}else{
			map.put("status", "1002");
			map.put("message", "delete fail");
		}
		return new Gson().toJson(map); 
	}*/

	@Override
	public int getParkIdByChanelId(int channelId) {
		return accessDAO.getParkIdByChanelId(channelId);
	}

	@Override
	public int getAllAccessCount(int xmo, int ymonth) {
		// TODO Auto-generated method stub
		return accessDAO.getAllAccessCount(xmo, ymonth);
	}

	@Override
	public int getAccessCountByDate(int xmo, int ymonth, String accessDate) {
		// TODO Auto-generated method stub
		return accessDAO.getAccessCountByDate(xmo, ymonth, accessDate);
	}

	@Override
	public List<AccessDetail> getAccessForExcel(int parkId, int monthNum) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int month=calendar.get(Calendar.MONTH);
		List<AccessDetail> accessDetails=accessDAO.getParkAccessDetail(0, 900000, parkId,findTableWithMonth(parkId, month));
		for(int i=1;i<monthNum;i++){
			int monthTmp=month-i;		
			if (monthTmp>=1) {
				accessDetails.addAll(accessDAO.getParkAccessDetail(0, 900000, parkId,findTableWithMonth(parkId, monthTmp)));
			}
		}
		return accessDetails;
	}

	@Override
	public int getAccessCountToday(Integer parkId, String date) {
		// TODO Auto-generated method stub
		return accessDAO.getParkAccessCountToday(parkId.intValue(), findAccessTable(parkId),date);
	}

	@Override
	public Access getAccessInvalidate(Integer parkId, String date) {
		// TODO Auto-generated method stub
		return accessDAO.getAccessInvalidate(parkId, findAccessTable(parkId), date);
	}


}
