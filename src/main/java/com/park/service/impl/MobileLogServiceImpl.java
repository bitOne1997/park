package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.MobilelogMapper;
import com.park.model.Mobilelog;
import com.park.service.MobileLogService;
@Service
public class MobileLogServiceImpl implements MobileLogService {

	@Autowired
	MobilelogMapper mobileLogMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mobileLogMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Mobilelog record) {
		// TODO Auto-generated method stub
		return mobileLogMapper.insert(record);
	}

	@Override
	public int insertSelective(Mobilelog record) {
		// TODO Auto-generated method stub
		return mobileLogMapper.insertSelective(record);
	}

	@Override
	public Mobilelog selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mobileLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Mobilelog record) {
		// TODO Auto-generated method stub
		return mobileLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Mobilelog record) {
		// TODO Auto-generated method stub
		return mobileLogMapper.updateByPrimaryKey(record);
	}

}
