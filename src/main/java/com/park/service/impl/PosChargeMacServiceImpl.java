package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.PoschargemacMapper;
import com.park.model.Poschargemac;
import com.park.service.PosChargeMacService;
@Service
public class PosChargeMacServiceImpl implements PosChargeMacService {
	
	@Autowired
	PoschargemacMapper poschargemacMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return poschargemacMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Poschargemac record) {
		// TODO Auto-generated method stub
		return poschargemacMapper.insert(record);
	}

	@Override
	public int insertSelective(Poschargemac record) {
		// TODO Auto-generated method stub
		return poschargemacMapper.insertSelective(record);
	}

	@Override
	public Poschargemac selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return poschargemacMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Poschargemac record) {
		// TODO Auto-generated method stub
		return poschargemacMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Poschargemac record) {
		// TODO Auto-generated method stub
		return poschargemacMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Poschargemac> selectByMac(int macId, int count) {
		// TODO Auto-generated method stub
		return poschargemacMapper.selectByMac(macId, count);
	}

	@Override
	public int updateByPosChargeId(Poschargemac record) {
		// TODO Auto-generated method stub
		return poschargemacMapper.updateByPosChargeId(record);
	}

}
