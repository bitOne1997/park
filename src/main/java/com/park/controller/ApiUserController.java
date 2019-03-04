package com.park.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.ApiUser;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.ApiUserService;
import com.park.service.AuthorityService;

import com.park.service.UserPagePermissionService;
import com.park.service.Utility;
import com.jpush.Jpush;

@Controller
@RequestMapping("/api/user")
public class ApiUserController {
	
	@Autowired
	private ApiUserService apiUserService;

	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private AuthorityService authService;
	
	@RequestMapping(value = "/index", produces = {"application/json;charset=UTF-8"})
	public String apiUserIndex(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "apiUser";		
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String count(){
		Map<String, Object> body = new HashMap<String, Object>();
		int count = apiUserService.count();
		body.put("count", count);
		return Utility.createJsonMsg(1001, "get count sucessfully", body);
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String get(@RequestBody Map<String, Object> args){
		Map<String, Object> body = new HashMap<String, Object>();
		int start = (Integer)args.get("start");
		int len = (Integer)args.get("len");
		List<ApiUser> users = apiUserService.get(start, len);
		body.put("users", users);
		return Utility.createJsonMsg(1001, "sucessfully", body);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String update(@RequestBody ApiUser user){
		int ret = apiUserService.update(user);
		return Utility.createJsonMsg(ret > 0 ? 1001 : 1002, ret > 0 ? "sucessfully" : "failed");
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insert(@RequestBody ApiUser user){
		
		int ret = apiUserService.insert(user);
		return Utility.createJsonMsg(ret > 0 ? 1001 : 1002, ret > 0 ? "sucessfully" : "failed");
	}
	

	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody 
	public String delete(@PathVariable int id){
		int ret = apiUserService.delete(id);
		return Utility.createJsonMsg(ret > 0 ? 1001 : 1002, ret > 0 ? "sucessfully" : "failed");
	}
	@RequestMapping(value = "/testjpush", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody 
	public void testjpush() throws InterruptedException{
		List<String> audiences=new ArrayList<>();
		audiences.add("2017");
		Jpush.SendPushToAudiences(audiences,"carportStatusChanged");
	}
	@RequestMapping(value = "/testjpushextras", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody 
	public void testjpushWithExtra() throws InterruptedException{
		List<String> audiences=new ArrayList<>();
		audiences.add("2017");
		Map<String, String> extras=new HashMap<>();
		extras.put("parkId", "160");
		extras.put("portNum", "23");
		Jpush.SendPushToAudiencesWithExtras(audiences,extras,"wechatPaidChanged");
	}
}
