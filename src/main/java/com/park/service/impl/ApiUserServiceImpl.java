package com.park.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.ApiUsersDAO;
import com.park.model.ApiUser;
import com.park.model.Token;
import com.park.service.ApiUserService;
import com.park.service.TokenService;
import com.park.service.Utility;

@Transactional
@Service
public class ApiUserServiceImpl implements ApiUserService{

	@Autowired
	ApiUsersDAO apiUserDao;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public int count() {
		return apiUserDao.count();
	}

	@Override
	public List<ApiUser> get(int start, int len) {
		List<ApiUser> ret = apiUserDao.get(start, len);
		return ret != null ? ret : new ArrayList<ApiUser>();
	}

	@Override
	public int update(ApiUser user) {
		return apiUserDao.update(user);
	}

	@Override
	public int insert(ApiUser user) {
		
		int tokenId = tokenService.insertToken();
		if(tokenId <= 0)
			return 0;
		user.setTokenId(tokenId);
		return apiUserDao.insert(user);
	}

	@Override
	public int delete(int id) {
		return apiUserDao.delete(id);
	}

}
