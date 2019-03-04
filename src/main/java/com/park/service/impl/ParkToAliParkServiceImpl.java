package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ParktoaliparkMapper;
import com.park.model.Parktoalipark;
import com.park.service.ParkToAliparkService;
@Service
public class ParkToAliParkServiceImpl implements ParkToAliparkService {
	@Autowired
	ParktoaliparkMapper parkToAliparkMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parktoalipark record) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.insert(record);
	}

	@Override
	public int insertSelective(Parktoalipark record) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.insertSelective(record);
	}

	@Override
	public Parktoalipark selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parktoalipark record) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parktoalipark record) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parktoalipark> getByAliParkId(String aliparkingId) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.getByAliParkId(aliparkingId);
	}

	@Override
	public List<Parktoalipark> getByParkId(Integer parkId) {
		// TODO Auto-generated method stub
		return parkToAliparkMapper.getByParkId(parkId);
	}

}
