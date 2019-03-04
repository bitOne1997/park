package com.park.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.FeecriteriontoparkMapper;
import com.park.model.Feecriteriontopark;
import com.park.service.FeecriterionToParkService;
@Service
public class FeecriterionToParkServiceImpl implements FeecriterionToParkService {

	@Autowired
	private FeecriteriontoparkMapper feecriterionToParkMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Feecriteriontopark record) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.insert(record);
	}

	@Override
	public int insertSelective(Feecriteriontopark record) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.insertSelective(record);
	}

	@Override
	public Feecriteriontopark selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Feecriteriontopark record) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Feecriteriontopark record) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> getByPark(int parkId) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.getByPark(parkId);
	}

	@Override
	public List<Feecriteriontopark> getByParkAndType(int parkId, int carType) {
		// TODO Auto-generated method stub
		return feecriterionToParkMapper.getByParkAndType(parkId, carType);
	}

}
