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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Repair;
import com.park.service.AuthorityService;
import com.park.service.RepairService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/repair")
public class RepairController {
@Autowired
private RepairService repairService;
@Autowired
private AuthorityService authService;

@Autowired
private UserPagePermissionService pageService;


@RequestMapping("")
public String Repair(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
	return "repair";
}
@RequestMapping(value = "/repair2", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
public String Repair2(ModelMap modelMap, HttpServletRequest request, HttpSession session){
	
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
		
	return "repair2";
}

@RequestMapping(value="/getall",produces={"application/json;charset=utf-8"})
@ResponseBody
public String getall(){
	Map<String, Object> result=new HashMap<>();
	List<Repair> repairs =repairService.getAll();
	if (repairs!=null) {
		result.put("status", 1001);
		result.put("body", repairs);
	}
	else {
		result.put("status", 1002);
	}
	return Utility.gson.toJson(result);
}
@RequestMapping(value="/insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
@ResponseBody
public String insert(@RequestBody Repair repair){
	Map<String, Object> result=new HashMap<>();
	int num=repairService.insert(repair);
	if (num==1) {
		result.put("status", 1001);
		result.put("message", "success");
	}
	else{
		result.put("status", 1002);
		result.put("message", "failure");
	}
	return Utility.gson.toJson(result);
}
@RequestMapping(value="/update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
@ResponseBody
public String update(@RequestBody Repair repair){
	Map<String, Object> result=new HashMap<>();
	int num=repairService.updateByPrimaryKey(repair);
	if (num==1) {
		result.put("status", 1001);
		result.put("message", "success");
	}
	else{
		result.put("status", 1002);
		result.put("message", "failure");
	}
	return Utility.gson.toJson(result);
}

@RequestMapping(value="/delete/{id}",produces={"application/json;charset=utf-8"})
@ResponseBody
public String delete(@PathVariable("id")Integer id){
	Map<String, Object> result=new HashMap<>();
	int num=repairService.deleteByPrimaryKey(id);
	if (num==1) {
		result.put("status", 1001);
		result.put("message", "success");
	}
	else{
		result.put("status", 1002);
		result.put("message", "failure");
	}
	return Utility.gson.toJson(result);
}
}
