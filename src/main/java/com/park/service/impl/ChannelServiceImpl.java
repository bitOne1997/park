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

import com.google.gson.Gson;
import com.park.dao.ChannelDAO;
import com.park.dao.HardwareDAO;
import com.park.model.Channel;
import com.park.model.ChannelDetail;
import com.park.model.Constants;
import com.park.model.Hardware;
import com.park.model.Status;
import com.park.service.ChannelService;

@Transactional
@Service
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private HardwareDAO hardwareDAO;
	
	public List<Channel> getChannels(){
		return channelDAO.getChannels();
	}
	
	
	public boolean insertChannel(Channel channel){
		if(channel.getMacId() < 0)
			channel.setMac(null);
		return channelDAO.insertChannel(channel) > 0;
	}
	
	public String insertChannelList(List<Channel> channels){
		Map<String, Object> map = new HashMap<String, Object>();
		int sum = 0;
		for(Channel channel : channels){
			sum += channelDAO.insertChannel(channel);
		}
		if(sum == channels.size()){
			map.put("status", "1001");
			map.put("message", "insert success");
		}else{
			map.put("status", "1002");
			map.put("message",  sum + " success " + channels.size() + " fail");
		}
		return new Gson().toJson(map);
	}
	
	public boolean updateChannel(Channel channel){
		return channelDAO.updateChannel(channel) > 0;
	}
	
	public String deleteChannel(int Id){
		Map<String, Object> map = new HashMap<String, Object>();
		if(channelDAO.deleteChannel(Id) > 0){
			map.put("status", "1001");
			map.put("message", "delete success");
		}else{
			map.put("status", "1002");
			map.put("message", "delete fail");
		}
		return new Gson().toJson(map);
	}

	@Override
	public List<ChannelDetail> getChannelDetail(int low, int count) {
		return channelDAO.getChannelDetail(low, count);
	}
	public List<ChannelDetail> getParkChannelDetail(int low, int count, Integer parkId) {
		return channelDAO.getParkChannelDetail(low, count, parkId.intValue());
	}
	@Override
	public int getchannelCount() {
		
		return channelDAO.getChannelCount();
	}

	@Override
	public int getChannelIdByMacId(int macId) {
		Hardware hardware = hardwareDAO.getHardwareById(macId);
		if(hardware.getStatus() == Status.UNUSED.getValue())
			return -1;
		return channelDAO.getChannelIdByMacId(macId);
	}

	@Override
	public int getChannelIdByMac(String mac) {
		List<Hardware> hardwares=hardwareDAO.getHardwareByMac(mac);
		return this.getChannelIdByMacId(hardwares.get(0).getId());
	}

	@Override
	public Channel getChannelsById(int id) {
		return channelDAO.getChannelById(id);
	}


	@Override
	public void updateChannelDateByMac(String mac) {
		// TODO Auto-generated method stub
		channelDAO.updateDateByHardwareMac(mac);
	}


	@Override
	public List<ChannelDetail> getChannelDetailByDate(String startday, String endday) {
		// TODO Auto-generated method stub
		return channelDAO.getChannelDetailByDate(startday, endday);
	}


	@Override
	public List<ChannelDetail> getChannelDetailByKeywords(String keywords) {
		// TODO Auto-generated method stub
		return channelDAO.getChannelDetailByKeywords(keywords);
	}


	@Override
	public int getChannelCountByChannelFlag(int channelFlag) {
		// TODO Auto-generated method stub
		return channelDAO.getChannelCountByChannelFlag(channelFlag);
	}


	@Override
	public List<ChannelDetail> getChannelDetailByMacType(int low, int count, int channelFlag) {
		// TODO Auto-generated method stub
		return channelDAO.getChannelDetailByChannelFlag(low, count, channelFlag);
	}


	@Override
	public int deleteByParkId(Integer parkId) {
		// TODO Auto-generated method stub
		return channelDAO.deleteByParkId(parkId);
	}


	@Override
	public List<ChannelDetail> getByRangeDay(int parkId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return channelDAO.getByRangeDay(parkId, startDate, endDate);
	}


	@Override
	public List<ChannelDetail> getExcelByParkDay(int parkId, String date) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date startDate = sFormat.parse(date + " 00:00:00");
		Date endDate = sFormat.parse(date + " 23:59:59");
		return channelDAO.getByRangeDay(parkId, startDate, endDate);
	}


	@Override
	public List<ChannelDetail> getExcelByAllParkDay() throws ParseException {
		// TODO Auto-generated method stub
		return channelDAO.getByRangeAllDay();
	}


	@Override
	public List<ChannelDetail> getByRangeAllDay() {
		// TODO Auto-generated method stub
		return channelDAO.getByRangeAllDay();
	}

}
