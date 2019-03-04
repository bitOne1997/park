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
import com.park.model.Parkcarauthority;
import com.park.service.AuthorityService;
import com.park.service.ParkCarAuthorityService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("carAuthority")
public class ParkCarAuthorityController {

	@Autowired
	private ParkCarAuthorityService carAuthorityService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private ParkService parkService;
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
		return "parkCarAuthority";
	}
	@RequestMapping(value="getByPark",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByPark(@RequestBody Map<String, Object> args){
		int parkId=(int) args.get("parkId");
		List<Parkcarauthority> parkcarauthorities=carAuthorityService.getByParkId(parkId);

		Map<String, Object> result=new HashMap<>();
		if (!parkcarauthorities.isEmpty()) {
			result.put("status", 1001);
			result.put("body", parkcarauthorities);
		}
		else {
		
			carAuthorityService.InitRecords(parkId);
			result.put("body", carAuthorityService.getByParkId(parkId));
			result.put("status", 1001);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Parkcarauthority parkcarauthority){
		int num=carAuthorityService.insertSelective(parkcarauthority);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String update(@RequestBody Parkcarauthority parkcarauthority){
		int num=carAuthorityService.updateByPrimaryKeySelective(parkcarauthority);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="updateRows",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String updateRows(@RequestBody List<Parkcarauthority> parkcarauthority){
		int num=0;
		for (Parkcarauthority parkcarauthority2 : parkcarauthority) {
			num +=carAuthorityService.updateByPrimaryKeySelective(parkcarauthority2);
		}
		
		Map<String, Object> result=new HashMap<>();
		if (num==2) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String delete(@RequestBody Map<String, Object> args){
		int id=(int) args.get("id");
		Map<String, Object> result=new HashMap<>();
		int num=carAuthorityService.deleteByPrimaryKey(id);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
