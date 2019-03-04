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

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.Street;
import com.park.service.AuthorityService;
import com.park.service.ParkService;
import com.park.service.StreetService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;


@Controller
@RequestMapping("street")
public class StreetController {
	@Autowired
	private AuthorityService authService;
	@Autowired
	private StreetService streetService;
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
		return "street";
	}
	/**
	 * street表
	 * 根据区areaId获取对应街道（获取出所有街道）
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value="getByAreaid/{areaId}",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByAreaId(@PathVariable("areaId")int areaId){
		Map<String, Object> result=new HashMap<>();
		List<Street> streets=streetService.getByArea(areaId);
		if (streets!=null) {
			result.put("status", 1001);
			result.put("body", streets);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Street street){
		Map<String, Object> result=new HashMap<>();
		int num=streetService.insert(street);
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
		int num=streetService.deleteByPrimaryKey(id);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	//多选删除
	@RequestMapping(value="deleteAll/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public Object deleteAll(@PathVariable("id")String id){
		Map<String, Object> result=new HashMap<>();
		int deleteByPrimaryKeyId = streetService.deleteByPrimaryKeyId(id);
		if(deleteByPrimaryKeyId>=1){
			result.put("status", 1001);
		}
		else{
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String update(@RequestBody Street steet){
		Map<String, Object> result=new HashMap<>();
		int num=streetService.updateByPrimaryKeySelective(steet);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	/**
	 * street表
	 * 根据id获取到街道
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectByPrimaryKey/{id}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String selectByPrimaryKey(@PathVariable("id")int id){
		Map<String, Object> result=new HashMap<>();
		Street street=streetService.selectByPrimaryKey(id);
		if (street!=null) {
			result.put("status", 1001);
			result.put("body", street);
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
		int count=streetService.getCount();
		result.put("count", count);
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/getByStartAndCount",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByStartAndCount(@RequestParam("start")int start,@RequestParam("count")int count, HttpSession session){
		Map<String, Object> result=new HashMap<>();
	//	List<Street> streets=streetService.getByStartAndCount(start, count);
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if(username != null){
			AuthUser user = authService.getUserByUsername(username);
			if(user.getRole() == AuthUserRole.ADMIN.getValue()){
				result.put("status", 1001);
				result.put("body", streetService.getByStartAndCount(0, 200));
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
		
		if (streetsResult!=null) {
			result.put("status", 1001);
			result.put("body", streetsResult);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	
}
