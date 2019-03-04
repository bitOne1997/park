package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.PagePermissionDAO;
import com.park.model.Page;
import com.park.model.PagePermission;
import com.park.model.Role;
import com.park.model.UserRole;
import com.park.service.PagePermissionService;

@Transactional
@Service
public class PagePermissionServiceImpl implements PagePermissionService{

	@Autowired
	PagePermissionDAO pageDao;
	
	@Override
	public List<Role> getRoles() {
		return pageDao.getRoles();
	}


	@Override
	public int createRole(Role role) {
		return pageDao.createRole(role);
	}

	@Override
	public int updateRole(Role role) {
		return pageDao.updateRole(role);
	}

	@Override
	public int deleteRole(int roleId) {
		return pageDao.deleteRole(roleId);
	}

	@Override
	public List<Page> getPage() {
		return pageDao.getPage();
	}

	@Override
	public int createPage(Page page) {
		return pageDao.createPage(page);
	}

	@Override
	public int updatePage(Page page) {
		return pageDao.updatePage(page);
	}

	@Override
	public int deletePage(int pageId) {
		return pageDao.deletePage(pageId);
	}

	
	@Override
	public int deleteRolePage(int roleId) {
		return pageDao.deleteRolePage(roleId);
	}
	
	
	@Override
	public int updateRolePage(int roleId, List<Integer> pageIds) {
		this.deleteRolePage(roleId);
		for(Integer pageId : pageIds){
			PagePermission pagePerm = new PagePermission();
			pagePerm.setPageId(pageId);
			pagePerm.setRoleId(roleId);
			this.createRolePage(pagePerm);
			
		}
		
		return 1;
	}

	@Override
	public int updateUserRole(int userId, List<Integer> roleIds) {
		try {
			this.deleteUserRole(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for(Integer roleId : roleIds){
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			this.createUserRole(userRole);
		}
		return 1;
	}

	
	@Override
	public int deleteUserRole(int userId) {
		return pageDao.deleteUserRole(userId);
	}


	@Override
	public int createRolePage(PagePermission item) {
		return pageDao.createRolePage(item);
	}


	@Override
	public int createUserRole(UserRole item) {
		return pageDao.createUserRole(item);
	}


	@Override
	public List<Page> getRolePage(int role) {
		return pageDao.getRolePage(role);
	}


	@Override
	public List<Role> getUserRole(int userId) {
		return pageDao.getUserRole(userId);
	}

}
