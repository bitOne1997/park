package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ParkcarauthorityMapper;
import com.park.model.Parkcarauthority;
import com.park.service.ParkCarAuthorityService;
@Service
public class ParkCarAuthorityServiceImpl implements ParkCarAuthorityService {

	@Autowired
	ParkcarauthorityMapper parkcarautorityMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parkcarauthority record) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.insert(record);
	}

	@Override
	public int insertSelective(Parkcarauthority record) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.insertSelective(record);
	}

	@Override
	public Parkcarauthority selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parkcarauthority record) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parkcarauthority record) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parkcarauthority> getByParkId(Integer parkId) {
		// TODO Auto-generated method stub
		List<Parkcarauthority> parkcarauthorities=parkcarautorityMapper.getByParkId(parkId);
		if (parkcarauthorities.isEmpty()) {
			InitRecords(parkId);
		}
		return parkcarautorityMapper.getByParkId(parkId);
	}

	@Override
	public List<Parkcarauthority> getByStartAndCount(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return parkcarautorityMapper.getByStartAndCount(start, count);
	}

	@Override
	public int InitRecords(Integer parkId) {
		// TODO Auto-generated method stub
		Parkcarauthority parkcarauthority0=new Parkcarauthority();
		parkcarauthority0.setChannel((byte) 0);
		parkcarauthority0.setParkid(parkId);
		Parkcarauthority parkcarauthority1=new Parkcarauthority();
		parkcarauthority1.setChannel((byte) 1);
		parkcarauthority1.setParkid(parkId);
		parkcarautorityMapper.insertSelective(parkcarauthority0);
		parkcarautorityMapper.insertSelective(parkcarauthority1);
		return 0;
	}

}
