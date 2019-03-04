package com.park.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.UserPagePermissionService;

@Controller
public class LeftParkingCardController {

	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value = "/leftParkingCard", produces = {"application/json;charset=UTF-8"})
	public String getChannels(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}		
		return "leftParkingCard";
	}
}
