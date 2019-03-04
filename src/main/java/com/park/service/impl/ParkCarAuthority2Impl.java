package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.Parkcarauthority2Mapper;
import com.park.model.Parkcarauthority2;
import com.park.service.ParkCarAuthority2Service;
@Service
public class ParkCarAuthority2Impl implements ParkCarAuthority2Service {

	@Autowired
	private Parkcarauthority2Mapper parkcarauthorityMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parkcarauthority2 record) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.insert(record);
	}

	@Override
	public int insertSelective(Parkcarauthority2 record) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.insertSelective(record);
	}

	@Override
	public Parkcarauthority2 selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.selectByPrimaryKey(id);
	}

	@Override
	public Parkcarauthority2 selectByPark(int parkId) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.selectByPark(parkId);
	}

	@Override
	public int updateByPrimaryKeySelective(Parkcarauthority2 record) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parkcarauthority2 record) {
		// TODO Auto-generated method stub
		return parkcarauthorityMapper.updateByPrimaryKey(record);
	}

}
