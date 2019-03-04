package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.ParkDetail;
import com.park.model.UserPark;

@Repository
public interface UserParkDAO {

	public List<Integer> getOwnParkId(int userId);
	
	public List<String> getOwnParkName(int userId);
	
	public int insertUserParkMap(UserPark userParkMap);
	
	public int deleteUserParkMap(int userId);
}
