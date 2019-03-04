package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.PosDAO;
import com.park.model.Pos;
import com.park.model.posdataReceive;
import com.park.service.PosService;
@Transactional
@Service
public class PosServiceImpl implements PosService {
	
	@Autowired
	private PosDAO posDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return posDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Pos record) {
		// TODO Auto-generated method stub
		return posDao.insert(record);
	}

	@Override
	public int insertSelective(Pos record) {
		// TODO Auto-generated method stub
		return posDao.insertSelective(record);
	}

	@Override
	public Pos selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return posDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Pos record) {
		// TODO Auto-generated method stub
		return posDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Pos record) {
		// TODO Auto-generated method stub
		return posDao.updateByPrimaryKey(record);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return posDao.getCount();
	}

	@Override
	public List<Pos> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return posDao.getByStartAndCount(start, count);
	}

	@Override
	public List<Pos> getByNum(String num) {
		// TODO Auto-generated method stub
		return posDao.getByNum(num);
	}

	@Override
	public List<Pos> getByParkNameAndNumber(String parkName, String num) {
		// TODO Auto-generated method stub
		return posDao.getByParkNameAndNumber(parkName, num);
	}

	@Override
	public List<Pos> getByParkId(Integer parkId) {
		// TODO Auto-generated method stub
		return posDao.getByParkId(parkId);
	}

	

}
