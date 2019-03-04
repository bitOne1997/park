package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.StreetDAO;
import com.park.model.Street;
import com.park.service.StreetService;
@Transactional
@Service
public class StreetServiceImpl implements StreetService {

	@Autowired
	private StreetDAO streetDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return streetDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Street record) {
		// TODO Auto-generated method stub
		return streetDao.insert(record);
	}

	@Override
	public int insertSelective(Street record) {
		// TODO Auto-generated method stub
		return streetDao.insertSelective(record);
	}

	@Override
	public Street selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return streetDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Street record) {
		// TODO Auto-generated method stub
		return streetDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Street record) {
		// TODO Auto-generated method stub
		return streetDao.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return streetDao.getCount();
	}

	@Override
	public List<Street> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return streetDao.getByStartAndCount(start, count);
	}

	@Override
	public List<Street> getByArea(int areaId) {
		// TODO Auto-generated method stub
		return streetDao.getByArea(areaId);
	}


	@Override
	public int deleteByPrimaryKeyId(String array) {
		// TODO Auto-generated method stub
		String[] idss=array.split(",");
		int[] id=new int[idss.length];
		for(int i=0;i<idss.length;i++){
			id[i]=Integer.parseInt(idss[i]);
		}
		int count=streetDao.deleteByPrimaryKeyId(id);
		return count;
	}

}
