package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.Page;
import com.park.model.PagePermission;
import com.park.model.Role;
import com.park.model.UserRole;

@Repository
public interface PagePermissionDAO {
	
	List<Role> getRoles();

	List<Role> getUserRole(int userId);

	int createRole(Role role);

	int updateRole(Role role);

	int deleteRole(int roleId);

	List<Page> getPage();

	int createPage(Page page);

	int updatePage(Page page);

	int deletePage(int pageId);

	//int updateRolePage(PagePermission item);

	//int updateUserRole(UserRole item);

	int deleteRolePage(int roleId);

	int deleteUserRole(int userId);

	int createRolePage(PagePermission item);

	int createUserRole(UserRole item);

	List<Page> getRolePage(int role);



}
