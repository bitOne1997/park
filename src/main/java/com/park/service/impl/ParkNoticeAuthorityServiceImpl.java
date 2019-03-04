package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ParknoticeauthorityMapper;
import com.park.model.Parknoticeauthority;
@Service
public class ParkNoticeAuthorityServiceImpl implements com.park.service.ParkNoticeAuthorityService {
	
@Autowired
ParknoticeauthorityMapper parkNoticeMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parknoticeauthority record) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.insert(record);
	}

	@Override
	public int insertSelective(Parknoticeauthority record) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.insertSelective(record);
	}

	@Override
	public Parknoticeauthority selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parknoticeauthority record) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parknoticeauthority record) {
		// TODO Auto-generated method stub
		return parkNoticeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parknoticeauthority> getByParkId(int parkId) {
		// TODO Auto-generated method stub
		List<Parknoticeauthority> parknoticeauthorities=parkNoticeMapper.getByParkId(parkId);
		if (parknoticeauthorities.isEmpty()) {
			Parknoticeauthority parknoticeauthority=new Parknoticeauthority();
			parknoticeauthority.setParkid(parkId);
			parknoticeauthority.setGongshang(false);
			parknoticeauthority.setWeixin(false);
			parknoticeauthority.setAlipay(false);
			insertSelective(parknoticeauthority);
			parknoticeauthorities=parkNoticeMapper.getByParkId(parkId);
		}
		
		return parknoticeauthorities;
	}

}
