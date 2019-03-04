package com.park.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Park;
import com.park.service.AuthorityService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;

@Controller
public class OutsideParkStatusController {
	@Autowired
	private ParkService parkService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;	
	@RequestMapping(value="/outsideParkStatus")
	public String outsideParkStatus(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<>();
		for (Park park : parkList) {
			if (park.getType()==3) {
				parkl.add(park);
			}
		}
		modelMap.addAttribute("parks", parkl);
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
		return "outsideParkStatus";
	}
	
	@RequestMapping(value="/outsideParkStatusAdmin")
	public String outsideParkStatusAdmin(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<>();
		for (Park park : parkList) {
			
				parkl.add(park);
			
		}
		modelMap.addAttribute("parks", parkl);
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
		return "outsideParkStatusAdmin";
	}
	
	@RequestMapping(value="/outsideParkStatusAdminWithPark/{parkId}/")
	public String outsideParkStatusAdminWithPark(@PathVariable("parkId")int parkId,ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<>();
		for (Park park : parkList) {
			if (park.getId()==parkId) {
				parkl.add(park);
			}
		}
		modelMap.addAttribute("parks", parkl);
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
		return "outsideParkStatusAdminWithPark";
	}
}
