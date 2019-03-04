package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ParkextMapper;
import com.park.model.Parkext;
import com.park.service.ParkExtService;
@Service
public class ParkExtServiceImpl implements ParkExtService {
	@Autowired
	ParkextMapper parkextMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkextMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parkext record) {
		// TODO Auto-generated method stub
		return parkextMapper.insert(record);
	}

	@Override
	public int insertSelective(Parkext record) {
		// TODO Auto-generated method stub
		return parkextMapper.insertSelective(record);
	}

	@Override
	public Parkext selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkextMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parkext record) {
		// TODO Auto-generated method stub
		return parkextMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parkext record) {
		// TODO Auto-generated method stub
		return parkextMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parkext> selectByParkid(int parkId) {
		// TODO Auto-generated method stub
		return parkextMapper.selectByParkid(parkId);
	}

}
