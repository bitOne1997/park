package com.park.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Similarcarnumber;
import com.park.service.AuthorityService;
import com.park.service.SimilarCarNumberService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("similarcarnumber")
public class SimilarCarNumberController {
	@Autowired
	SimilarCarNumberService similarcarnumberService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	@RequestMapping(value="")
	public String index(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "similarCarNumber";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String insert(@RequestBody Similarcarnumber record) {
		int num=similarcarnumberService.insertSelective(record);
		
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String update(@RequestBody Similarcarnumber record) {
		int num=similarcarnumberService.updateByPrimaryKeySelective(record);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "/getByPark", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String getByPark(@RequestBody Map<String, Object> args) {
		int parkId=(int) args.get("parkId");
		List<Similarcarnumber> similarcarnumbers=similarcarnumberService.selectByPark(parkId);
		Map<String, Object> result=new HashMap<>();
		if (similarcarnumbers.isEmpty()) {
			result.put("status", 1002);
			return Utility.gson.toJson(result);
		}
		result.put("status", 1001);
		result.put("body", similarcarnumbers);
		return Utility.gson.toJson(result);
		
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String delete(@RequestBody Map<String, Object> args) {
		int id=(int) args.get("id");
		int num=similarcarnumberService.deleteByPrimaryKey(id);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}

}
