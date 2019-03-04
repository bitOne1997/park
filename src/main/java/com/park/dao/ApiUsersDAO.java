package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.ApiUser;

@Repository
public interface ApiUsersDAO {
	
	public int count();
	public List<ApiUser> get(int start, int len);
	public int update(ApiUser user);
	public int insert(ApiUser user);
	public int delete(int id);
}
