package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.UserParkDAO;
import com.park.model.UserPark;
import com.park.service.UserParkService;

@Transactional
@Service
public class UserParkServiceImpl implements UserParkService{

	@Autowired
	private UserParkDAO userParkDAO; 
	
	@Override
	public List<Integer> getOwnParkId(int userId) {
		
		return userParkDAO.getOwnParkId(userId);
	}

	@Override
	public List<String> getOwnParkName(int userId) {
		// TODO Auto-generated method stub
		return userParkDAO.getOwnParkName(userId);
	}

	@Override
	public int insertUserParkMap(UserPark userParkMap) {
		// TODO Auto-generated method stub
		return userParkDAO.insertUserParkMap(userParkMap);
	}

	@Override
	public int deleteUserParkMap(int userId) {
		// TODO Auto-generated method stub
		return userParkDAO.deleteUserParkMap(userId);
	}

}
