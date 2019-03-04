package com.park.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.park.model.Area;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.Street;
import com.park.model.Zonecenter;
import com.park.service.AreaService;
import com.park.service.AuthorityService;
import com.park.service.ParkService;
import com.park.service.StreetService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;
import com.park.service.ZoneCenterService;

@Controller
@RequestMapping("zoneCenter")
public class ZoneCenterController {
	@Autowired
	private ZoneCenterService zoneCenterService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private ParkService parkService;
	@Autowired
	private StreetService streetService;
	@Autowired
	private AreaService areaService;
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
		return "zoneCenter";
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Zonecenter zoneCenter){
		Map<String, Object> result=new HashMap<>();
		int num=zoneCenterService.insert(zoneCenter);
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
	public String delete(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		int num=zoneCenterService.deleteByPrimaryKey(id);
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
	public String update(@RequestBody Zonecenter zoneCenter){
		Map<String, Object> result=new HashMap<>();
		int num=zoneCenterService.updateByPrimaryKeySelective(zoneCenter);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="selectByPrimaryKey/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String selectByPrimaryKey(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		Zonecenter zoneCenter=zoneCenterService.selectByPrimaryKey(id);
		if (zoneCenter!=null) {
			result.put("status", 1001);
			result.put("body", zoneCenter);
		}
		else{
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getCount",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getCount(){
		Map<String, Object> result=new HashMap<>();
		int count=zoneCenterService.getCount();
		result.put("count", count);
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByStartAndCount",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByStartAndCount(@RequestParam("start")int start,@RequestParam("count")int count,HttpSession session){
		String username=(String) session.getAttribute("username");
		
		Map<String, Object> result=new HashMap<>();
		List<Zonecenter> zoneCenters=zoneCenterService.getByStartAndCount(start, count);
		
		//String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null){
			AuthUser user = authService.getUserByUsername(username);
			if(user.getRole() == AuthUserRole.ADMIN.getValue()){
				result.put("status", 1001);
				result.put("body", zoneCenterService.getByStartAndCount(0, 200));
				return Utility.gson.toJson(result);
			}
			else {
				parkList = parkService.filterPark(parkList, username);
			}
		}
			
		Set<Street> streetsResult=new HashSet<>();
		for (Park parktmp : parkList) {
			/*if (parktmp.getType()!=3) {
				continue;
			}*/
			Street street=streetService.selectByPrimaryKey(parktmp.getStreetId());
			if (!streetsResult.contains(street)) {
				streetsResult.add(street);
			}
		}
		Set<Area> areasResult=new HashSet<>();
		for (Street streettmp : streetsResult) {
			Area area=areaService.selectByPrimaryKey(streettmp.getAreaid());
			if (!areasResult.contains(area)) {
				areasResult.add(area);
			}
		}
		Set<Zonecenter> zonecentersResult=new HashSet<>();
		for (Area areaTmp : areasResult) {
			Zonecenter zonecenter=zoneCenterService.selectByPrimaryKey(areaTmp.getZoneid());
			if (!zonecentersResult.contains(zonecenter)) {
				zonecentersResult.add(zonecenter);
			}
		}
		
		if (zonecentersResult!=null) {
			result.put("status", 1001);
			result.put("body", zonecentersResult);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByUserName",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByUserName(@RequestParam("start")int start,@RequestParam("count")int count,HttpSession session){
		String username=(String) session.getAttribute("username");
		Map<String, Object> result=new HashMap<>();
		List<Zonecenter> zoneCenters=zoneCenterService.getByUserName(username);
		if (zoneCenters!=null) {
			result.put("status", 1001);
			result.put("body", zoneCenters);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
