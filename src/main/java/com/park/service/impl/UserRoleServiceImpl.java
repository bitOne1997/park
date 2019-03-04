package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.UserRoleDAO;
import com.park.service.UserRoleService;

@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleDAO userRoleDao;

	@Override
	public int deleteUserRoleMap(int userId) {
		// TODO Auto-generated method stub
		return userRoleDao.deleteUserRoleMap(userId);
	}
	
	
}
