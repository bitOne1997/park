package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.SimilarcarnumberMapper;
import com.park.model.Similarcarnumber;
import com.park.service.SimilarCarNumberService;
@Service
public class SimilarCarNumberServiceImpl implements SimilarCarNumberService {

	@Autowired
	SimilarcarnumberMapper similarcarnumberMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Similarcarnumber record) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.insert(record);
	}

	@Override
	public int insertSelective(Similarcarnumber record) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.insertSelective(record);
	}

	@Override
	public Similarcarnumber selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Similarcarnumber record) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Similarcarnumber record) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Similarcarnumber> selectBySimilarCarNumber(String similarNumber) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.selectBySimilarCarNumber(similarNumber);
	}

	@Override
	public List<Similarcarnumber> selectByRealCarNumber(String realNumber) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.selectByRealCarNumber(realNumber);
	}

	@Override
	public List<Similarcarnumber> selectByPark(int parkId) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.selectByPark(parkId);
	}

	@Override
	public List<Similarcarnumber> selectBySimilarCarNumberAndPark(String similarNumber, int parkId) {
		// TODO Auto-generated method stub
		return similarcarnumberMapper.selectBySimilarCarNumberAndPark(similarNumber, parkId);
	}

}
