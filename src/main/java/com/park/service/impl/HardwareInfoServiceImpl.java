package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.HardwareinfoMapper;
import com.park.model.Hardwareinfo;
import com.park.service.HardwareInfoService;
@Service
public class HardwareInfoServiceImpl implements HardwareInfoService {

	@Autowired
	HardwareinfoMapper hardwareinfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Hardwareinfo record) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.insert(record);
	}

	@Override
	public int insertSelective(Hardwareinfo record) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.insertSelective(record);
	}

	@Override
	public Hardwareinfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Hardwareinfo record) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Hardwareinfo record) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Hardwareinfo> getAll() {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.getAll();
	}

	@Override
	public List<Hardwareinfo> getByParkId(int parkId) {
		// TODO Auto-generated method stub
		return hardwareinfoMapper.getByParkId(parkId);
	}

}
