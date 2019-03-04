package com.park.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Alipayinfo;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Parkshowtext;
import com.park.service.AlipayInfoService;
import com.park.service.AuthorityService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@RequestMapping("alipayInfo")
@Controller
public class AlipayInfoController {
	
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	AlipayInfoService alipayinfoService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
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
		return "alipayInfo";
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Alipayinfo alipayinfo){
		int num=alipayinfoService.insertSelective(alipayinfo);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@PathVariable("id") int id){
		int num=alipayinfoService.deleteByPrimaryKey(id);
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
	public String update(@RequestBody Alipayinfo alipayinfo){
		int num=alipayinfoService.updateByPrimaryKeySelective(alipayinfo);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getbypark",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getbypark(@RequestBody Map<String, Object> args){
		Integer parkId=(Integer) args.get("parkId");
		List<Alipayinfo> alipayinfos=alipayinfoService.getbyParkId(parkId);
		Map<String, Object> result=new HashMap<>();
		if (!alipayinfos.isEmpty()) {
			result.put("status", 1001);
			result.put("body", alipayinfos);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="getbyCount",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getbyCount(@RequestBody Map<String, Object> args){
		Integer start=(Integer) args.get("start");
		Integer count=(Integer) args.get("count");
		List<Alipayinfo> alipayinfos=alipayinfoService.getByCount(start, count);
		Map<String, Object> result=new HashMap<>();
		if (!alipayinfos.isEmpty()) {
			result.put("status", 1001);
			result.put("body", alipayinfos);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
