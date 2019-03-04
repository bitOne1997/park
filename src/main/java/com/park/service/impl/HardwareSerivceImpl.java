package com.park.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.BusinessCarportDAO;
import com.park.dao.HardwareDAO;
import com.park.model.BusinessCarport;
import com.park.model.Channel;
import com.park.model.Hardware;
import com.park.model.HardwareDetail;
import com.park.model.HardwareType;
import com.park.model.Status;
import com.park.service.BusinessCarportService;
import com.park.service.ChannelService;
import com.park.service.HardwareService;
import com.park.service.Utility;

@Transactional
@Service
public class HardwareSerivceImpl implements HardwareService{

	@Autowired
	private HardwareDAO hardwareDAO;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private BusinessCarportDAO businessCarportDao;
	
	//private static Log logger = LogFactory.getLog(UserController.class);
	@Override
	public List<Hardware> getHardwares() {
		return hardwareDAO.getHardwares();
	}

	@Override
	public int macToId(String mac){
		return hardwareDAO.macToId(mac);
	}
	
	@Override
	public Hardware getHardwareById(int id) {
		return hardwareDAO.getHardwareById(id);
	}
	
	@Override
	public int getHardwareCount() {
		return hardwareDAO.getHardwareCount();
	}

	@Override
	public List<HardwareDetail> getHardwareDetail(int low, int count) {
		return hardwareDAO.getHardwareDetail(low, count);
	}

	@Override
	public List<Hardware> getUnboundHardwares(int type) {
		return hardwareDAO.getUnboundHardwares(type);
	}
	
	@Override
	public int insertHardware(Hardware hardware) {
		//if(checkHardwareExist(hardware.getMac()))
		//	return 0;
		List<Hardware> hardwares=getHardwareByMacAndType(hardware.getMac(), hardware.getType());
		if (!hardwares.isEmpty()) {
			return 0;
		}
		return hardwareDAO.insertHardware(hardware);
	}

	@Override
	public int updateHardware(Hardware hardware) {
		return hardwareDAO.updateHardware(hardware);
	}

	@Override
	public int deleteHardware(int id) {
		Hardware hardware = this.getHardwareById(id);
		if(hardware.getStatus() == Status.USED.getValue()){

			if(hardware.getType() == HardwareType.CARPORT.getValue()){				
				BusinessCarport carport = businessCarportDao.getBusinessCarportByMacId(id);
				carport.setMacId(null);
				businessCarportDao.updateBusinessCarport(carport);
			}else if(hardware.getType() == HardwareType.CHANNEL.getValue()){
				int channelId = channelService.getChannelIdByMacId(id);
				Channel channel = channelService.getChannelsById(channelId);
				channel.setMac(null);
				channelService.updateChannel(channel);
			}else{
				return 0;
			}
		}
		return hardwareDAO.deleteHardware(id);
	}

	@Override
	public List<Hardware> getUnusedHardwares() {
		return hardwareDAO.getUnusedHardwares();
	}

	@Override
	public boolean bindHardware(int id) {
		if(id < 0)
			return true;
		Hardware hardware = this.getHardwareById(id);
		if(hardware.getStatus() == Status.USED.getValue())
			return false;
		hardware.setStatus(Status.USED.getValue());
		int ret = hardwareDAO.updateHardware(hardware);
		return ret > 0;
	}

	@Override
	public boolean changeHardwareStatus(Integer id, int status) {
		if(id== null || id < 0)
			return true;
		Hardware hardware = this.getHardwareById(id);
		if (hardware==null) {
			return false;
		}
		int ret = 0;
		if(hardware.getStatus() != status){
			hardware.setStatus(status);
			ret = this.updateHardware(hardware);
		}
		return ret > 0;
	}

	@Override
	public List<Hardware> searchHardware(String mac) {
		// TODO Auto-generated method stub
		
		return hardwareDAO.searchHardware(mac);
	}

	
	@Override
	public List<Map<String, Object>> getInfoByMac(String mac) {
		// TODO Auto-generated method stub
		List<Hardware> hardwares=getHardwareByMac(mac);
		int type=0;
		if (!hardwares.isEmpty()) {
			type=hardwares.get(0).getType();
		}
	//	List<Map<String, Object>> list=hardwareDAO.getInfoByMac(mac);
		if (type!=0) {
			return hardwareDAO.getInfoByMac(mac);
		} else {
			return hardwareDAO.getInfoByMacCarport(mac);
		}
		
	}

	@Override
	public int macToType(String mac) {
		// TODO Auto-generated method stub
		return hardwareDAO.macToType(mac);
	}

	@Override
	public boolean checkHardwareExist(String mac) {
		List<Hardware> result = searchHardware(mac);
		if(result == null || result.size() == 0)
			return false;
		else 
			return true;
	}

	@Override
	public List<Hardware> searchHardwareByKeywords(String mac) {
		// TODO Auto-generated method stub
		return hardwareDAO.searchHardwareByKeywords(mac);
	}

	@Override
	public List<Hardware> getHardwareByMac(String mac) {
		// TODO Auto-generated method stub
		return hardwareDAO.getHardwareByMac(mac);
	}

	@Override
	public List<Hardware> getHardwareByMacAndType(String mac, Integer type) {
		// TODO Auto-generated method stub
		return hardwareDAO.getHardwareByMacAndType(mac, type);
	}

	



}
