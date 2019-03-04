package com.park.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.UdpconnectorinfoMapper;
import com.park.model.Udpconnectorinfo;
import com.park.model.Udpconnectors;
import com.park.service.UdpConnectorInfoService;
@Service
public class UdpConnectorsInfoServiceImpl implements UdpConnectorInfoService {

	@Autowired
	UdpconnectorinfoMapper udpConnectorInfoMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Udpconnectorinfo record) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(Udpconnectorinfo record) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.insertSelective(record);
	}

	@Override
	public Udpconnectorinfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Udpconnectorinfo record) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Udpconnectorinfo record) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Udpconnectorinfo> getByMac(String mac) {
		// TODO Auto-generated method stub
		return udpConnectorInfoMapper.getByMac(mac);
	}

	@Override
	public void handleUdpConnect(Udpconnectors udpconnector,String mac) {
		// TODO Auto-generated method stub
		Udpconnectorinfo udpconnectorinfo=new Udpconnectorinfo();
		if (mac.equals("")||mac==null) {
			return;
		}
		List<Udpconnectorinfo> udpconnectorinfos=udpConnectorInfoMapper.getByMac(mac);
		if (udpconnectorinfos.isEmpty()) {
			udpconnectorinfo.setIp(udpconnector.getIp());
			udpconnectorinfo.setPort(udpconnector.getPort());
			udpconnectorinfo.setMac(mac);
			udpconnectorinfo.setDate(new Date());
			insert(udpconnectorinfo);
		}
		else {
			udpconnectorinfo=udpconnectorinfos.get(0);
			udpconnectorinfo.setIp(udpconnector.getIp());
			udpconnectorinfo.setPort(udpconnector.getPort());
			udpconnectorinfo.setDate(new Date());
			updateByPrimaryKeySelective(udpconnectorinfo);
		}
	}

}
