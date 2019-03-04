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
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.ApiUser;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.model.TokenUsage;
import com.park.model.UserTokenUsage;
import com.park.service.AuthorityService;
import com.park.service.TokenUsageService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/token/usage")
public class TokenUsageController {

	@Autowired
	private TokenUsageService usageService;
	
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value = "/index", produces = {"application/json;charset=UTF-8"})
	public String tokenUsageIndex(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "tokenUsage";		
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String count(){
		Map<String, Object> body = new HashMap<String, Object>();
		int count = usageService.count();
		body.put("count", count);
		return Utility.createJsonMsg(1001, "get count sucessfully", body);
	}
	
	@RequestMapping(value = "/count/{tokenId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String tokenCount(@PathVariable int tokenId){
		Map<String, Object> body = new HashMap<String, Object>();
		int count = usageService.tokenCount(tokenId);
		body.put("count", count);
		return Utility.createJsonMsg(1001, "get count sucessfully", body);
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String get(@RequestBody Map<String, Object> args){
		Map<String, Object> body = new HashMap<String, Object>();
		int start = (Integer)args.get("start");
		int len = (Integer)args.get("len");
		List<UserTokenUsage> usages = usageService.get(start, len);
		body.put("usages", usages);
		return Utility.createJsonMsg(1001, "sucessfully", body);
		
	}
	
	@RequestMapping(value = "/item/{userId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getItem(@PathVariable int userId){
		Map<String, Object> body = new HashMap<String, Object>();
		List<UserTokenUsage> usages = usageService.getUsage(userId);
		body.put("usages", usages);
		return Utility.createJsonMsg(1001, "sucessfully", body);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insert(@RequestBody TokenUsage usage){
		int ret = usageService.insert(usage);
		return Utility.createJsonMsg(ret > 0 ? 1001 : 1002, ret > 0 ? "sucessfully" : "failed");
	}
	
	
	
}
