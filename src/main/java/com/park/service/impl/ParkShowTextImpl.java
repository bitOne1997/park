package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ParkshowtextMapper;
import com.park.model.Parkshowtext;
import com.park.service.ParkShowTextService;
@Service
public class ParkShowTextImpl implements ParkShowTextService {

	@Autowired
	ParkshowtextMapper parkshowtextMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parkshowtext record) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.insert(record);
	}

	@Override
	public int insertSelective(Parkshowtext record) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.insertSelective(record);
	}

	@Override
	public int deleteByPark(Integer parkId) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.deleteByPark(parkId);
	}

	@Override
	public Parkshowtext selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parkshowtext record) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parkshowtext record) {
		// TODO Auto-generated method stub
		return parkshowtextMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parkshowtext> getByPark(Integer parkId) {
		// TODO Auto-generated method stub
		List<Parkshowtext> parkshowtexts=parkshowtextMapper.getByPark(parkId);
		if (parkshowtexts.isEmpty()) {
			Parkshowtext parkshowtext0=new Parkshowtext();
			parkshowtext0.setChannel((byte) 0);
			parkshowtext0.setParkid(parkId);
			Parkshowtext parkshowtext1=new Parkshowtext();
			parkshowtext1.setChannel((byte) 1);
			parkshowtext1.setParkid(parkId);
			parkshowtextMapper.insertSelective(parkshowtext0);
			parkshowtextMapper.insertSelective(parkshowtext1);
			return parkshowtextMapper.getByPark(parkId);
		}
		return parkshowtexts;
	}

}
