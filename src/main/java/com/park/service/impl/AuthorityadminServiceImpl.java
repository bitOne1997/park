package com.park.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.AuthorityadminMapper;
import com.park.model.Authorityadmin;
import com.park.service.AuthorityadminService;
@Service
public class AuthorityadminServiceImpl implements AuthorityadminService {

	@Autowired
	AuthorityadminMapper authorityadminMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return authorityadminMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Authorityadmin record) {
		// TODO Auto-generated method stub
		return authorityadminMapper.insert(record);
	}

	@Override
	public int insertSelective(Authorityadmin record) {
		// TODO Auto-generated method stub
		return authorityadminMapper.insertSelective(record);
	}

	@Override
	public Authorityadmin selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return authorityadminMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Authorityadmin record) {
		// TODO Auto-generated method stub
		return authorityadminMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Authorityadmin record) {
		// TODO Auto-generated method stub
		return authorityadminMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Authorityadmin> getByToken(String token) {
		// TODO Auto-generated method stub
		return authorityadminMapper.getByToken(token);
	}

	@Override
	public List<Authorityadmin> getAll() {
		// TODO Auto-generated method stub
		return authorityadminMapper.getAll();
	}

}
