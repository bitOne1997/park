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
import com.park.service.AreaService;
import com.park.service.AuthorityService;
import com.park.service.ParkService;
import com.park.service.StreetService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;


@Controller
@RequestMapping("area")
public class AreaController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private ParkService parkService;
	@Autowired
	private StreetService streetService;
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
		
		return "area";
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Area area){
		Map<String, Object> result=new HashMap<>();
		int num=areaService.insert(area);
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
		int num=areaService.deleteByPrimaryKey(id);
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
	public String update(@RequestBody Area area){
		Map<String, Object> result=new HashMap<>();
		int num=areaService.updateByPrimaryKeySelective(area);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	/**
	 * area表
	 * 根据id获取到区域
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectByPrimaryKey/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String selectByPrimaryKey(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		Area area=areaService.selectByPrimaryKey(id);
		if (area!=null) {
			result.put("status", 1001);
			result.put("body", area);
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
		int count=areaService.getCount();
		result.put("count", count);
		return Utility.gson.toJson(result);
	}
	/**
	 * area表
	 * 根据市zoneid获取到对应区域(获取到所有区域)
	 * @param zoneid
	 * @return
	 */
	@RequestMapping(value="getByZoneCenterId/{zoneid}",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByZoneCenterId(@PathVariable("zoneid")int zoneid){
		Map<String, Object> result=new HashMap<>();
		List<Area> areas=areaService.getByZoneCenterId(zoneid);
		if (areas!=null) {
			result.put("status", 1001);
			result.put("body", areas);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByStartAndCount",method={RequestMethod.POST,RequestMethod.GET},produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByStartAndCount(HttpSession session){
		Map<String, Object> result=new HashMap<>();
		
	//	List<Area> areas=areaService.getByStartAndCount(start, count);
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null){
			AuthUser user = authService.getUserByUsername(username);
			if(user.getRole() == AuthUserRole.ADMIN.getValue()){
				result.put("status", 1001);
				result.put("body", areaService.getByStartAndCount(0, 200));
				return Utility.gson.toJson(result);
			}
			else {
				parkList = parkService.filterPark(parkList, username);
			}
		}
			
		Set<Street> streetsResult=new HashSet<>();
		for (Park parktmp : parkList) {
			if (parktmp.getType()!=3) {
				continue;
			}
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
		
		if (!areasResult.isEmpty()) {
			result.put("status", 1001);
			result.put("body", areasResult);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
