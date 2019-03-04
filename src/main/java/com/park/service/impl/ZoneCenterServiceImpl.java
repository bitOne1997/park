package com.park.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.ZonecenterDAO;
import com.park.model.Area;
import com.park.model.Park;
import com.park.model.Street;
import com.park.model.Zonecenter;
import com.park.service.AreaService;
import com.park.service.ParkService;
import com.park.service.StreetService;
import com.park.service.ZoneCenterService;
@Transactional
@Service
public class ZoneCenterServiceImpl implements ZoneCenterService {

	@Autowired
	private ZonecenterDAO zoneCenterDao;
	@Autowired
	ParkService parkService;
	@Autowired
	StreetService streetService;
	@Autowired
	AreaService areaService;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return zoneCenterDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Zonecenter record) {
		// TODO Auto-generated method stub
		return zoneCenterDao.insert(record);
	}

	@Override
	public int insertSelective(Zonecenter record) {
		// TODO Auto-generated method stub
		return zoneCenterDao.insertSelective(record);
	}

	@Override
	public Zonecenter selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return zoneCenterDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Zonecenter record) {
		// TODO Auto-generated method stub
		return zoneCenterDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Zonecenter record) {
		// TODO Auto-generated method stub
		return zoneCenterDao.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zoneCenterDao.getCount();
	}

	@Override
	public List<Zonecenter> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return zoneCenterDao.getByStartAndCount(start, count);
	}

	@Override
	public List<Zonecenter> getByUserName(String username) {
		// TODO Auto-generated method stub
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		Set<Zonecenter> zonecenters=new HashSet<Zonecenter>();
		for (Park park:parkList) {
			int streetId=park.getStreetId();
			Street street=streetService.selectByPrimaryKey(streetId);
			Area area=areaService.selectByPrimaryKey(street.getAreaid());
			Zonecenter zonecenter=selectByPrimaryKey(area.getZoneid());
			if (!zonecenters.contains(zonecenter)) {
				zonecenters.add(zonecenter);
			}
		}
		List<Zonecenter> zonecenters2=new ArrayList<>();
		for(Zonecenter zonecenter1:zonecenters){
			zonecenters2.add(zonecenter1);
		}
		return  zonecenters2;
	}

}
