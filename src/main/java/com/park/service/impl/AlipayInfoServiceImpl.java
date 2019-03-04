package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.AlipayinfoMapper;
import com.park.model.Alipayinfo;
import com.park.service.AlipayInfoService;
@Service
public class AlipayInfoServiceImpl implements AlipayInfoService {

	@Autowired
	AlipayinfoMapper alipayInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Alipayinfo record) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(Alipayinfo record) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.insertSelective(record);
	}

	@Override
	public Alipayinfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Alipayinfo record) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Alipayinfo record) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Alipayinfo> getbyParkId(int parkId) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.getbyParkId(parkId);
	}

	@Override
	public List<Alipayinfo> getbyOutParkKey(String outParkKey) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.getbyOutParkKey(outParkKey);
	}

	@Override
	public List<Alipayinfo> getByCount(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return alipayInfoMapper.getbyCount(start, count);
	}

}
