package com.park.service;

import java.util.List;

import com.park.model.ApiUser;

public interface ApiUserService {
	public int count();
	public List<ApiUser> get(int start, int len);
	public int update(ApiUser user);
	public int insert(ApiUser user);
	public int delete(int id);
}
