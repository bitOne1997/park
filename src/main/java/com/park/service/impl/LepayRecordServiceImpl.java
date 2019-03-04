package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.LepayrecordMapper;
import com.park.model.Lepayrecord;
import com.park.service.LepayRecordService;
@Service
public class LepayRecordServiceImpl implements LepayRecordService {

	@Autowired
	private LepayrecordMapper lepayrecordMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Lepayrecord record) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.insert(record);
	}

	@Override
	public int insertSelective(Lepayrecord record) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.insertSelective(record);
	}

	@Override
	public Lepayrecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Lepayrecord record) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Lepayrecord record) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return lepayrecordMapper.getAmount();
	}

	@Override
	public List<Lepayrecord> getByCount(int start, int count) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.getByCount(start, count);
	}

	@Override
	public Lepayrecord getByOutTradeNo(String outTradeNo) {
		// TODO Auto-generated method stub
		return lepayrecordMapper.getByOutTradeNo(outTradeNo);
	}

}
