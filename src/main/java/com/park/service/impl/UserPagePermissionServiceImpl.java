package com.park.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.UserPagePermissionDAO;
import com.park.model.Page;
import com.park.service.UserPagePermissionService;

@Transactional
@Service
public class UserPagePermissionServiceImpl implements UserPagePermissionService{

	@Autowired
	UserPagePermissionDAO pageDao;
	
	@Override
	public Set<Page> getUserPage(int userId) {
		
		return pageDao.getUserPage(userId);
	}

}
