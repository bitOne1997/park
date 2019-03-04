package com.park.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.park.model.Access;
import com.park.model.AccessDetail;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Park;
import com.park.service.AccessService;
import com.park.service.AuthorityService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;


@Controller
public class ParkChartController {
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private AccessService accessService;
	
	@RequestMapping(value = "/chart", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String parkChart(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
		List<Park> parkList = parkService.getParks();
		String username = (String) session.getAttribute("username");
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<>();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=sFormat.format(new Date())+" 00:00:00";
		for (Park park : parkList) {
			if (park.getType()!=3) {
				int count=accessService.getAccessCountToday(park.getId(), date);
				if (count>0) {
					parkl.add(park);
				}				
			}
		}
		modelMap.addAttribute("parks", parkl);		
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
		
		
		return "parkChart";
	}
	@RequestMapping(value="getValidateDataPark",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getValidateDataPark(){		
		List<Park> parkList = parkService.getParks();
		List<Park> parkl = new ArrayList<Park>();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=sFormat.format(new Date())+" 00:00:00";
		for (Park park : parkList) {
			if (park.getType()!=3) {
				int count=accessService.getAccessCountToday(park.getId(), date);
				if (count>0) {
					parkl.add(park);
				}				
			}
		}
		return Utility.createJsonMsg(1001, "success", parkl);
	}
	@RequestMapping(value="getValidateDataParkByUserName",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getValidateDataParkByUserName(@RequestBody Map<String, String> args){		
		String username=args.get("username");
		List<Park> parkList = parkService.getParks();
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<Park>();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=sFormat.format(new Date())+" 00:00:00";
		for (Park park : parkList) {
			if (park.getType()!=3) {
				int count=accessService.getAccessCountToday(park.getId(), date);
				if (count>0) {
					parkl.add(park);
				}				
			}
		}
		return Utility.createJsonMsg(1001, "success", parkList);
	}
	@RequestMapping(value="getAccessInvalidate",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	public String getAccessInvalidate(){	
		List<Park> parkList = parkService.getParks();
		List<Map<String, Object>> result=new ArrayList<>();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=sFormat.format(new Date())+" 00:00:00";
		for (Park park : parkList) {
			if (park.getType()!=3) {
				Map<String, Object> tmpData=new HashMap<>();
				Access accessDetail=accessService.getAccessInvalidate(park.getId(), date);
				tmpData.put("id", park.getId());
				tmpData.put("name", park.getName());
				tmpData.put("date", accessDetail.getDate());
				result.add(tmpData);
			}
		}
		return Utility.createJsonMsg(1001, "success", result);
	}
	@RequestMapping(value="/flow",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	public String parkFlow(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		List<Park> parkList = parkService.getParks();
		String username = (String) session.getAttribute("username");
		if(username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> parkl = new ArrayList<>();
		SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
		String date=sFormat.format(new Date())+" 00:00:00";
		for (Park park : parkList) {
			if (park.getType()!=3) {
				int count=accessService.getAccessCountToday(park.getId(), date);
				if (count>0) {
					parkl.add(park);
				}				
			}
		}
		modelMap.addAttribute("parks", parkl);
		
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
		
		return "flow";
	}
}
