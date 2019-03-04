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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Feecriteriontopark;
import com.park.model.Monthuserpark;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.FeecriterionToParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("feecriterionToPark")
public class FeecriterionToParkController {
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private FeecriterionToParkService feecriterionToParkService;
	
	@RequestMapping(value = "/index", produces = {"application/json;charset=UTF-8"})
	public String feeCriterionToPark(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "feecriterionToPark";		
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Feecriteriontopark record){
		Map<String, Object> result=new HashMap<>();
		int num=feecriterionToParkService.insertSelective(record);
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
	public String update(@RequestBody Feecriteriontopark record){
		Map<String, Object> result=new HashMap<>();
		int num=feecriterionToParkService.updateByPrimaryKeySelective(record);
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
	public String delete(@RequestBody Map<String, Object> args){
		int id=(int) args.get("id");
		int num=feecriterionToParkService.deleteByPrimaryKey(id);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete/{id}",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String delete2(@PathVariable("id")int id){
		int num=feecriterionToParkService.deleteByPrimaryKey(id);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="getByPark",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByPark(@RequestBody Map<String, Object> args){
		int parkId=(int) args.get("parkId");
		List<Map<String, Object>> feecriteriontoparks=feecriterionToParkService.getByPark(parkId);
		Map<String, Object> result=new HashMap<>();
		if (!feecriteriontoparks.isEmpty()) {
			result.put("status", 1001);
			result.put("body", feecriteriontoparks);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}@RequestMapping(value="getByParkAndType",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByParkAndType(@RequestBody Map<String, Object> args){
		int parkId=(int) args.get("parkId");
		int carType=(int) args.get("carType");
		List<Feecriteriontopark> feecriteriontoparks=feecriterionToParkService.getByParkAndType(parkId, carType);
		Map<String, Object> result=new HashMap<>();
		if (!feecriteriontoparks.isEmpty()) {
			result.put("status", 1001);
			result.put("body", feecriteriontoparks);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
