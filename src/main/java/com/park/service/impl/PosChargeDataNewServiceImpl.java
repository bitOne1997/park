package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.park.dao.PoschargedataMapper;
import com.park.model.PoschargedataNew;
import com.park.service.PosChargeDataNewService;

public class PosChargeDataNewServiceImpl implements PosChargeDataNewService {

	@Autowired
	PoschargedataMapper poschargeDataMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(PoschargedataNew record) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.insert(record);
	}

	@Override
	public int insertSelective(PoschargedataNew record) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.insertSelective(record);
	}

	@Override
	public PoschargedataNew selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(PoschargedataNew record) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(PoschargedataNew record) {
		// TODO Auto-generated method stub
		return poschargeDataMapper.updateByPrimaryKey(record);
	}

}
