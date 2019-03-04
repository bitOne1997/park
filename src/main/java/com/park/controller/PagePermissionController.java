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
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.Role;
import com.park.service.AuthorityService;
import com.park.service.PagePermissionService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/page/permission")
public class PagePermissionController {
	
	@Autowired
	PagePermissionService pagePermissService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value="roles",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	public String roles(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "role";	
	}
	
	@RequestMapping(value="role/allocate",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	public String allocateRoles(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "allocateRole";	
	}
	
	@RequestMapping(value="pages",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	public String pages(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "page";	
	}
	
	@RequestMapping(value="role/get",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getRoles(){
		List<Role> roles = pagePermissService.getRoles();
		return Utility.createJsonMsg(1001, "successfully", roles);
	}
	
	@RequestMapping(value="role/get/{userId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getUserRole(@PathVariable int userId){
		List<Role> roles = pagePermissService.getUserRole(userId);
		return Utility.createJsonMsg(1001, "successfully", roles);
	}
	
	@RequestMapping(value="role/detail/{userId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getUserRoleDetail(@PathVariable int userId){
		List<Role> allocatedRoles = pagePermissService.getUserRole(userId);
		List<Role> roles = pagePermissService.getRoles();
		
		Set<Integer> allocatedSet = new HashSet<Integer>();
		for(Role role : allocatedRoles){
			allocatedSet.add(role.getId());
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(Role role: roles){
			Map<String , Object> pageMap = new HashMap<String, Object>();
			pageMap.put("id", role.getId());
			pageMap.put("name", role.getName());
		    if(allocatedSet.contains(role.getId()))
		    	pageMap.put("allocated", true);
		    else
		    	pageMap.put("allocated", false);
		    result.add(pageMap);
		    			    
		}
		
		return Utility.createJsonMsg(1001, "successfully", result);
	}
	
	
	
	@RequestMapping(value="role/create",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String createRole(@RequestBody Role role){
		int result = pagePermissService.createRole(role);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="role/update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String updateRole(@RequestBody Role role){
		int result = pagePermissService.updateRole(role);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="role/delete/{roleId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String deleteRole(@PathVariable int roleId){
		int result = pagePermissService.deleteRole(roleId);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="page/get",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getPage(){
		List<Page> result = pagePermissService.getPage();
		return Utility.createJsonMsg(1001, "successfully", result);
	}
	
	@RequestMapping(value="role/page/{roleId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getRolePage(@PathVariable int roleId){
		List<Page> result = pagePermissService.getRolePage(roleId);
		return Utility.createJsonMsg(1001, "successfully", result);
	}
	
	@RequestMapping(value="role/pageDetail/{roleId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getRolePageDetail(@PathVariable int roleId){
		List<Page> rolePages = pagePermissService.getRolePage(roleId);
		List<Page> pages = pagePermissService.getPage();
		
		Set<Integer> rolePageSet = new HashSet<Integer>();
		for(Page page : rolePages){
			rolePageSet.add(page.getId());
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(Page page : pages){
			Map<String , Object> pageMap = new HashMap<String, Object>();
			pageMap.put("id", page.getId());
			pageMap.put("uri", page.getUri());
		    if(rolePageSet.contains(page.getId()))
		    	pageMap.put("allocated", true);
		    else
		    	pageMap.put("allocated", false);
		    result.add(pageMap);
		    			    
		}
		
		return Utility.createJsonMsg(1001, "successfully", result);
	}
	
	
	@RequestMapping(value="page/create",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String createPage(@RequestBody Page page){
		int result = pagePermissService.createPage(page);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="page/update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String updateRole(@RequestBody Page page){
		int result = pagePermissService.updatePage(page);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="page/delete/{pageId}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String deletePage(@PathVariable int pageId){
		int result = pagePermissService.deletePage(pageId);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	
	
	@RequestMapping(value="role/page/update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String deleteRolePage(@RequestBody Map<String, Object> args){
		int roleId = (int)args.get("roleId");
		List<Integer> pageIds = (List<Integer>)args.get("pageIds");
		
		int result = pagePermissService.updateRolePage(roleId, pageIds);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value="role/user/update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String createUserRole(@RequestBody Map<String, Object> args){
		int userId = (int) args.get("userId");
		List<Integer> roleIds = (List<Integer>) args.get("roleIds");
		
		int result = pagePermissService.updateUserRole(userId, roleIds);
		if(result > 0)
			return Utility.createJsonMsg(1001, "successfully");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
}
