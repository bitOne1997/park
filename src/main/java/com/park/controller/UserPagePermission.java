package com.park.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.park.service.UserPagePermissionService;

@Controller
public class UserPagePermission {
	
	@Autowired
	private UserPagePermissionService pageService;

}
