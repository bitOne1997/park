package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.AuthUser;
import com.park.model.AuthUserDetail;

@Repository
public interface AuthorityDAO {
	
	public List<AuthUser> getUsers();
	
	public List<AuthUser> getUsersByCount(@Param("start")int start,@Param("count")int count);
	
	public int getUserCount();
	
	public String getUserPasswd(String username);
	
	public AuthUser getUser(String username);
	
	public int insertUser(AuthUser user);
	
	public int deleteUser(int id);
	
	public int updateUser(AuthUser user);
	
	public List<AuthUser> getParkByNameandParkId(@Param("username")String username);
	
	
}
