package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.NjmonthuserMapper;
import com.park.model.Njmonthuser;
import com.park.service.NjMonthUserService;
@Service
public class NjMonthUserServiceImpl implements NjMonthUserService {

	@Autowired
	NjmonthuserMapper njMonthUserMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Njmonthuser record) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.insert(record);
	}

	@Override
	public int insertSelective(Njmonthuser record) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.insertSelective(record);
	}

	@Override
	public Njmonthuser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Njmonthuser record) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Njmonthuser record) {
		// TODO Auto-generated method stub
		return njMonthUserMapper.updateByPrimaryKey(record);
	}

}
