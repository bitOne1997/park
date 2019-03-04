package com.park.service;

import java.util.List;

import com.park.model.UserPark;

public interface UserParkService {

	public List<Integer> getOwnParkId(int userId);
	
	public List<String> getOwnParkName(int userId);
	
	public int insertUserParkMap(UserPark userParkMap);
	
	public int deleteUserParkMap(int userId);
}
