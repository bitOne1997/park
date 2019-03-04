package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.User;
import com.park.model.UserDetail;

@Repository
public interface UserDAO {

	List<User> getUsers();
	
	public List<UserDetail> getUserDetail(@Param("low")int low, @Param("count")int count);
	
	public int getUserCount();
	
	int insertUser(User userItem);
	
	String getUserPassword(String userName);
	
	int getUserCountByUserName(String userName);
	
	int getUserCountByNumber(String number);
	
	int getUserIdByNumber(String number);
	
	public User getUserByUsername(String username);
	
	public int changeUserPassword(String username, String password);
	
	public int updateUser(User user);
}
