package com.park.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.park.model.BusinessCarport;
import com.park.model.Channel;
import com.park.model.ChannelDetail;

public interface ChannelService {
	
	public List<Channel> getChannels();
	
	public Channel getChannelsById(int id);
	
	public int getchannelCount();
	
	public int getChannelCountByChannelFlag(int macType);
	
	public List<ChannelDetail> getChannelDetail(int low, int count);
	
	public List<ChannelDetail> getChannelDetailByMacType(int low, int count,int macType);
	
	public List<ChannelDetail> getChannelDetailByKeywords(String keywords);
	
	public List<ChannelDetail> getParkChannelDetail(int low, int count,Integer parkId);
	
	public List<ChannelDetail> getChannelDetailByDate(String startday,String endday);
	
	public int getChannelIdByMacId(int macId);
	
	public int getChannelIdByMac(String mac);
	
	public boolean insertChannel(Channel channel);
	
	public String insertChannelList(List<Channel> channels);
	
	public boolean updateChannel(Channel channel);
	
	public String deleteChannel(int Id);
	
	public void updateChannelDateByMac(String mac);
	
	public int deleteByParkId(Integer parkId);
	
	public List<ChannelDetail> getByRangeDay(int parkId,Date startDate,Date endDate);
	
	public List<ChannelDetail> getExcelByParkDay(int parkId,String date) throws ParseException;
	
	public List<ChannelDetail> getByRangeAllDay();
	
	public List<ChannelDetail> getExcelByAllParkDay() throws ParseException;
}
