package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.NjcarfeerecordMapper;
import com.park.model.Njcarfeerecord;
import com.park.service.NjCarFeeRecordService;
@Service
public class NjCarFeeRecordServiceImpl implements NjCarFeeRecordService {
	@Autowired
	NjcarfeerecordMapper njCarFeeRecordMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Njcarfeerecord record) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(Njcarfeerecord record) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.insertSelective(record);
	}

	@Override
	public Njcarfeerecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Njcarfeerecord record) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Njcarfeerecord record) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Njcarfeerecord> selectByCarNumber(String carNumber) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.selectByCarNumber(carNumber);
	}

	@Override
	public List<Njcarfeerecord> selectByTradeNumber(String tradeNumber) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.selectByTradeNumber(tradeNumber);
	}

	@Override
	public List<Njcarfeerecord> selectByCount(int count) {
		// TODO Auto-generated method stub
		return njCarFeeRecordMapper.selectByCount(count);
	}

}
