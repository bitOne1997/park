package com.park.service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.MonthuserparkDAO;
import com.park.model.Monthuserpark;
import com.park.service.MonthUserParkService;
@Transactional
@Service
public class MonthUserParkServiceImpl implements MonthUserParkService {
@Autowired
private MonthuserparkDAO monthUserParkDAO;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Monthuserpark record) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.insert(record);
	}

	@Override
	public int insertSelective(Monthuserpark record) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.insertSelective(record);
	}

	@Override
	public Monthuserpark selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Monthuserpark record) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Monthuserpark record) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> getOwnParkName(int userId) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.getOwnParkName(userId);
	}

	@Override
	public int deleteByUserIdAndParkId(Monthuserpark record) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.deleteByUserIdAndParkId(record);
	}

	@Override
	public List<Map<String, Object>> getUsersByParkId(int parkId) {
		// TODO Auto-generated method stub
		return monthUserParkDAO.getUsersByParkId(parkId);
	}

}
