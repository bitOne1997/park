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

import com.github.pagehelper.PageHelper;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Hardwareinfo;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.HardwareInfoService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("hardwareinfo")
public class HardwareInfoController {

	@Autowired
	private HardwareInfoService hardwareInfoService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private AuthorityService authService;
	//新加硬件信息页面
	@RequestMapping(value = "/hardwareinfo", produces = {"application/json;charset=UTF-8"})
	public String hardwareinfo(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		boolean isAdmin = false;
		if(user != null){
			modelMap.addAttribute("user", user);
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
			return "hardwareinfo";
	}
	

	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String  insert(@RequestBody Hardwareinfo hardwareinfo) {
		Map<String, Object> result=new HashMap<>();
		int num=hardwareInfoService.insertSelective(hardwareinfo);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String  delete(@RequestBody Map<String, Object> args) {
		Map<String, Object> result=new HashMap<>();
		int id=(int) args.get("id");
		int num=hardwareInfoService.deleteByPrimaryKey(id);
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
	public String  update(@RequestBody Hardwareinfo hardwareinfo) {
		Map<String, Object> result=new HashMap<>();
		int num=hardwareInfoService.updateByPrimaryKeySelective(hardwareinfo);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getAll",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String  getAll() {
		Map<String, Object> result=new HashMap<>();
		List<Hardwareinfo> hardwareinfos=hardwareInfoService.getAll();		
		if (!hardwareinfos.isEmpty()) {
			result.put("status", 1001);
			result.put("body", hardwareinfos);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getByPage",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByPage(@RequestBody Map<String, Object> args) {
		Map<String, Object> result=new HashMap<>();		
		int start=(int) args.get("start");
		int count=(int) args.get("count");
		PageHelper.startPage(start,count);
		List<Hardwareinfo> hardwareinfos=hardwareInfoService.getAll();
		if (!hardwareinfos.isEmpty()) {
			result.put("status", 1001);
			result.put("body", hardwareinfos);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getByParkId",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByParkId(@RequestBody Map<String, Object> args) {
		Map<String, Object> result=new HashMap<>();
		int parkId=(int) args.get("parkId");
		List<Hardwareinfo> hardwareinfos=hardwareInfoService.getByParkId(parkId);	
		if (!hardwareinfos.isEmpty()) {
			result.put("status", 1001);
			result.put("body", hardwareinfos);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
