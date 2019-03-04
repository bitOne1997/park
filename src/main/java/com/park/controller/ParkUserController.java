package com.park.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.park.model.AuthUserDetail;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.ParkDetail;
import com.park.model.PosChargeData;
import com.park.model.User;
import com.park.model.UserDetail;
import com.park.service.AuthorityService;
import com.park.service.PagePermissionService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.UserParkService;
import com.park.service.UserRoleService;
import com.park.service.UserService;
import com.park.service.Utility;


@Controller
public class ParkUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authService;
	@Autowired
	UserParkService userParkService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ParkService parkService;
	@Autowired
	PagePermissionService pagePermissService;
	private static Log logger = LogFactory.getLog(ParkUserController.class);
	
	@RequestMapping(value = "/getParkByNameandParkId", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByNameandParkId(@RequestBody Map<String, Object> args){
		String username = (String)args.get("username");
		/*String name=(String)args.get("name");*/
//		logger.info("get park with name: " + name);
		List<AuthUser> queryname = authService.getParkByNameandParkId(username);
		List<AuthUserDetail> userDetail = new ArrayList<AuthUserDetail>();
		if(queryname != null){
			if( queryname.size() != 0){
				for(AuthUser user : queryname){
					List<String> parkName = authService.getOwnUserParkName(user.getId());
					userDetail.add(new AuthUserDetail(user, parkName));
				}
			}
		}
		return Utility.createJsonMsg("1001", "get user detail success", userDetail);
	}
	
	/*@RequestMapping(value = "/getParkByNameandParkName", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkByNameandParkName(@RequestBody Map<String, Object> args){
		String name = (String)args.get("name");
		List<ParkDetail> parkname = parkService.getParkByNameandParkName(name);
		List<AuthUserDetail> userDetail = new ArrayList<AuthUserDetail>();
		if(parkname != null){
			if( parkname.size() != 0){
				for(ParkDetail park : parkname){
					List<String> parkName = authService.getOwnUserParkName(park.getId());
					userDetail.add(new AuthUserDetail(park, parkName));
				}
			}
		}
		return Utility.createJsonMsg("1001", "get user detail success", userDetail);
	}*/
	
	@RequestMapping(value = "/parkUsers", produces = {"application/json;charset=UTF-8"})
	public String getUsers(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		List<AuthUser> userList = authService.getUsers();
		List<AuthUserDetail> userDetail = new ArrayList<AuthUserDetail>();
		for(AuthUser user : userList){
			List<String> parkName = authService.getOwnUserParkName(user.getId());
			userDetail.add(new AuthUserDetail(user, parkName));
		}
		modelMap.put("users", userDetail);
		logger.info("info test");		
		
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
//		if(isAdmin)
			return "parkUser";
//		else
	//		return "redirect:platformShow";
	}
	//新加页面
	@RequestMapping(value = "/parkUsers2", produces = {"application/json;charset=UTF-8"})
	public String getUsers2(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		List<AuthUser> userList = authService.getUsers();
		List<AuthUserDetail> userDetail = new ArrayList<AuthUserDetail>();
		for(AuthUser user : userList){
			List<String> parkName = authService.getOwnUserParkName(user.getId());
			userDetail.add(new AuthUserDetail(user, parkName));
		}
		modelMap.put("users", userDetail);
		logger.info("info test");		
		
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
//		if(isAdmin)
			return "parkUser2";
//		else
	//		return "redirect:platformShow";
	}
	
	
	@RequestMapping(value = "/getParkUserCount", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getuserCount(){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = authService.getUserCount();
		body.put("count", count);
	
		ret.put("status", "1001");
		ret.put("message", "get user detail success");
		ret.put("body", Utility.gson.toJson(body));
		
		return Utility.gson.toJson(ret);					
	}
	
	@RequestMapping(value = "/getParkUserDetail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String accessIndex(@RequestParam("low")int low, @RequestParam("count")int count){	
		List<AuthUser> userList = authService.getUsersByCount(low, count);
		List<AuthUserDetail> userDetail = new ArrayList<AuthUserDetail>();
		for(AuthUser user : userList){
			List<String> parkName = authService.getOwnUserParkName(user.getId());
			userDetail.add(new AuthUserDetail(user, parkName));
		}
		return Utility.createJsonMsg("1001", "get user detail success", userDetail);
		
	}
	
	@RequestMapping(value = "/getParkUser", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkUser(){	
		List<AuthUser> userList = authService.getUsers();
		
		return Utility.createJsonMsg("1001", "get user detail success", userList);
		
	}
	
	@RequestMapping(value = "/insert/parkUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertUser(@RequestBody Map<String, Object> argMap){
		AuthUser user = new AuthUser();
		user.setUsername((String) argMap.get("username"));
		user.setPassword((String)argMap.get("password"));
		user.setRole((int)argMap.get("role"));
		List<Integer> parkIds = new ArrayList<Integer>();
		for(Object item : (List<?>)argMap.get("parkList")){
			parkIds.add(Integer.parseInt(item.toString()));
		}
		 
		int count  = authService.insertUser(user, parkIds);
		
		if(count > 0){
			try {
				List<Integer> roleIds = new ArrayList<>();
				roleIds.add(1);
				pagePermissService.updateUserRole(user.getId(), roleIds);
				
			} catch (Exception e) {
				// TODO: handle exception
				return Utility.createJsonMsg("1002", "insert failed");
			}
			return Utility.createJsonMsg("1001", "insert success");
		}
			
		else
			return Utility.createJsonMsg("1002", "insert failed");
	}
	
	
	@RequestMapping(value = "/delete/parkUser/{Id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deletePark(@PathVariable int Id){
		 userParkService.deleteUserParkMap(Id);
		 userRoleService.deleteUserRoleMap(Id);
		 authService.deleteUser(Id);
		 return Utility.createJsonMsg("1001", "delete success");
	}

	
}
