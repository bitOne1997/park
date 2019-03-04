package com.park.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;
import com.park.service.impl.FormatTime;

@Controller
public class CouponsController {
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	FormatTime formatTime;
	
	@RequestMapping(value="coupons")
	public String coupon(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "coupons";
	}
	@RequestMapping(value="blackList")
	public String blackList(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "blackList";
	}
	@RequestMapping(value="testFormatDate",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testFormatDate(){
		Map<String,String> dates=formatTime.format("2017-09-10 13:23:21","2017-09-12 17:23:21","24","0");
		return Utility.createJsonMsg(1001, "ok", dates);
	}
	
}
