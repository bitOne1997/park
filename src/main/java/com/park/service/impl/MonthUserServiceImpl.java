package com.park.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.MonthuserDAO;
import com.park.model.Monthuser;
import com.park.service.MonthUserService;
@Transactional
@Service
public class MonthUserServiceImpl implements MonthUserService {

	@Autowired
	private MonthuserDAO monthUserDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return monthUserDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Monthuser record) {
		// TODO Auto-generated method stub
		return monthUserDao.insert(record);
	}

	@Override
	public int insertSelective(Monthuser record) {
		// TODO Auto-generated method stub
		return monthUserDao.insertSelective(record);
	}

	@Override
	public Monthuser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return monthUserDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Monthuser record) {
		// TODO Auto-generated method stub
		return monthUserDao.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return monthUserDao.getCount();
	}

	@Override
	public List<Monthuser> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return monthUserDao.getByStartAndCount(start, count);
	}

	@Override
	public int updateByPrimaryKeySelective(Monthuser record) {
		// TODO Auto-generated method stub
		return monthUserDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Monthuser> getByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return monthUserDao.getByCardNumber(cardNumber);
	}

	@Override
	public int getCountByParkId(int parkId) {
		// TODO Auto-generated method stub
		return monthUserDao.getCountByParkId(parkId);
	}

	@Override
	public List<Monthuser> getByParkIdAndCount(int parkId, int start, int count) {
		// TODO Auto-generated method stub
		return monthUserDao.getByParkIdAndCount(parkId, start, count);
	}

	@Override
	public List<Monthuser> getByStartAndCountOrder(int start, int count, int type) {
		// TODO Auto-generated method stub
		return monthUserDao.getByStartAndCountAndOrder(start, count, type);
	}

	@Override
	public List<Monthuser> getByParkIdAndCountOrder(int parkId, int start, int count, int type) {
		// TODO Auto-generated method stub
		return monthUserDao.getByParkIdAndCountOrder(parkId, start, count, type);
	}

	@Override
	public List<Monthuser> getByUsernameAndPark(String username, int parkId) {
		// TODO Auto-generated method stub
		return monthUserDao.getByUsernameAndPark(username, parkId);
	}

	@Override
	public List<Monthuser> getByCarnumberAndPark(String carnumber, int parkId) {
		// TODO Auto-generated method stub
		List<Monthuser> monthusers =new ArrayList<>();
		try {
			monthusers= monthUserDao.getByCarnumberAndPark(carnumber, parkId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return monthusers;
	}

	@Override
	public List<Monthuser> getByUserNameAndParkAndPort(String username, int parkId, String portNumber) {
		// TODO Auto-generated method stub
		return monthUserDao.getByUserNameAndParkAndPort(username, parkId, portNumber);
	}

	@Override
	public List<Monthuser> getByParkAndPort(int parkId, String portNumber) {
		// TODO Auto-generated method stub
		return monthUserDao.getByParkAndPort(parkId, portNumber);
	}

	@Override
	public Map<String, Object> statisticsInfo(int parkId, int type) {
		// TODO Auto-generated method stub
		return monthUserDao.statisticsInfo(parkId, type);
	}

	@Override
	public List<Map<String, Object>> getMonthuserCountsByDateRangeAndPark(int parkId, Date startDate, Date endDate,
			int maxCount) {
		// TODO Auto-generated method stub
		return monthUserDao.getMonthuserCountsByDateRangeAndPark(parkId, startDate, endDate, maxCount);
	}

	@Override
	public List<Monthuser> getLast3Number(String lastNumber, int parkId) {
		// TODO Auto-generated method stub
		return monthUserDao.getLast3Number(lastNumber, parkId);
	}

	@Override
	public Monthuser getByPlateNumberById(int id) {
		// TODO Auto-generated method stub
		return monthUserDao.getByPlateNumberById(id);
	}


	@Override
	public List<Monthuser> getByPlateNumberBytype(String platenumber, int type, String owner, String certificatetype) {
		// TODO Auto-generated method stub
		return monthUserDao.getByPlateNumberBytype(platenumber, type, owner, certificatetype);
	}

	@Override
	public Monthuser selectById(int parkId,int type,String owner, Date starttime, Date endtime, String platenumber) {
		// TODO Auto-generated method stub
		return monthUserDao.selectById(parkId, type,owner,starttime, endtime, platenumber);
	}

	@Override
	public List<Monthuser> getByPlateNumberAndParkId(int parkId, String platenumber) {
		// TODO Auto-generated method stub
		return monthUserDao.getByPlateNumberAndParkId(parkId, platenumber);
	}

}
