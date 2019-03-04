package com.park.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO {
	public int deleteUserRoleMap(int userId);
}
