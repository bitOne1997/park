package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.HongxingrecordMapper;
import com.park.model.Hongxingrecord;
import com.park.service.HongxingRecordService;
@Service
public class HongxingRecordServiceImpl implements HongxingRecordService {

	@Autowired
	HongxingrecordMapper hongxingRecordMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Hongxingrecord record) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(Hongxingrecord record) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.insertSelective(record);
	}

	@Override
	public Hongxingrecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Hongxingrecord record) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Hongxingrecord record) {
		// TODO Auto-generated method stub
		return hongxingRecordMapper.updateByPrimaryKey(record);
	}

}
