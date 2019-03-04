package com.park.service;

import java.util.List;

import com.park.model.Page;
import com.park.model.PagePermission;
import com.park.model.Role;
import com.park.model.UserRole;

public interface PagePermissionService {

	List<Role> getRoles();

	int createRole(Role role);

	int updateRole(Role role);

	int deleteRole(int roleId);

	List<Page> getPage();

	int createPage(Page page);

	int updatePage(Page page);

	int deletePage(int pageId);

	int createRolePage(PagePermission item);
	
	int deleteRolePage(int roleId);
	
	List<Page> getRolePage(int role);
	
	int updateRolePage(int roleId, List<Integer> pageIds);

	List<Role> getUserRole(int userId);
	
	int createUserRole(UserRole item);
	
	int deleteUserRole(int userId);
	
	int updateUserRole(int userId, List<Integer> roleIds);

}
