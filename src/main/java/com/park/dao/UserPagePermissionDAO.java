package com.park.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.park.model.Page;

@Repository
public interface UserPagePermissionDAO {

	//public List<UserPagePermission> getUserPermission(int userId);
	
	public Set<Page> getUserPage(int userId);
}
