package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.AreaDAO;
import com.park.model.Area;
import com.park.service.AreaService;
@Transactional
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDAO areaDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return areaDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Area record) {
		// TODO Auto-generated method stub
		return areaDao.insert(record);
	}

	@Override
	public int insertSelective(Area record) {
		// TODO Auto-generated method stub
		return areaDao.insertSelective(record);
	}

	@Override
	public Area selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return areaDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Area record) {
		// TODO Auto-generated method stub
		return areaDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Area record) {
		// TODO Auto-generated method stub
		return areaDao.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return areaDao.getCount();
	}

	@Override
	public List<Area> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return areaDao.getByStartAndCount(start, count);
	}

	@Override
	public List<Area> getByZoneCenterId(int zoneid) {
		// TODO Auto-generated method stub
		return areaDao.getByZoneCenterId(zoneid);
	}

}
