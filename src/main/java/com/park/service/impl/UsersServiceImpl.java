package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.UsersMapper;
import com.park.model.Users;
import com.park.service.UsersService;
@Transactional
@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersMapper userMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Users record) {
		// TODO Auto-generated method stub
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(Users record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}

	@Override
	public Users selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Users record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Users record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userMapper.getCount();
	}

	@Override
	public List<Users> getByCount(Integer start, Integer count) {
		// TODO Auto-generated method stub
		return userMapper.getByCount(start, count);
	}

	@Override
	public List<Users> getByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		return userMapper.getByUserNameAndPassword(userName, password);
	}

	@Override
	public List<Users> getByUserName(String userName) {
		// TODO Auto-generated method stub
		return userMapper.getByUserName(userName);
	}

}
