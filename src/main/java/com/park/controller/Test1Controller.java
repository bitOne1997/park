package com.park.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.JedisClient;
import com.park.service.UserPagePermissionService;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@Controller
@RequestMapping("test1")
public class Test1Controller {
	@Autowired
	private UserPagePermissionService pageService;	
	@Autowired
	private AuthorityService authService;
	@Resource(name="jedisClient")
	private JedisClient jedisClient;
	
	@RequestMapping(value = "/redistest", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String redistest() {
	
	System.out.println(jedisClient.set("liu", "川A1LM97",60));
	System.out.println(jedisClient.set("川A1LM97", "meng2",260));
	System.out.println(jedisClient.set("liu3", "meng3",360));
	System.out.println(jedisClient.get("liu"));
	return null;
	}
	@RequestMapping(value = "/redistest2", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String redistest2() {

	return null;
	}
	@RequestMapping(value = "/auditMap", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String auditMap(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "parkAuditingMap";
	}
	@RequestMapping(value = "/auditMap2", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String auditMap2(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "parkAuditingMap";
	}
}
