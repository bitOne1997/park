package com.park.service;

import java.util.Set;

import com.park.model.Page;

public interface UserPagePermissionService {
	
	public Set<Page> getUserPage(int userId);

}
