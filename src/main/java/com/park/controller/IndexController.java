package com.park.controller;


import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.UserPagePermissionService;

@Controller
public class IndexController {

	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String accessIndex1(ModelMap modelMap,HttpSession session){
		
		String username=(String) session.getAttribute("username");
		
		if (username==null) {
			return "login";
		}
		else {
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
			return "clouddata";
		}
	}
	
	
	
	@RequestMapping(value = "authority", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public String authority(ModelMap modelMap, @RequestParam("username") String username,@RequestParam("password") String password,HttpSession session,HttpServletRequest request){
		if(authService.checkUserAccess(username, password)){
			session.setAttribute("username", username);			
			//String url=request.getServletPath();
			AuthUser user = authService.getUserByUsername(username);			
			if(user != null){
				session.setAttribute("userId", user.getId());
	//			modelMap.addAttribute("user", user);
				boolean isAdmin = false;
				if(user.getRole() == AuthUserRole.ADMIN.getValue())
					isAdmin=true;
	//			modelMap.addAttribute("isAdmin", isAdmin);

			}
			String redirectUrl=(String) session.getAttribute("redirectUrl");
			//返回上一次访问的页面
//			if (redirectUrl!=null&&!redirectUrl.contains("login")&&redirectUrl.length()>7) {
//				redirectUrl=redirectUrl.substring(5);
//				return "redirect:"+redirectUrl;
//			}
			return "redirect:clouddata";
		}else{
			return "redirect:login";
		}	
	}
	
	
	@RequestMapping(value = "urlLogin/{username}/{password}/", method = RequestMethod.GET)
	public String urlLogin(ModelMap modelMap, @PathVariable("username") String username,@PathVariable("password") String password,HttpSession session,HttpServletRequest request){
		if(authService.checkUserAccess(username, password)){
			session.setAttribute("username", username);			
			//String url=request.getServletPath();
			AuthUser user = authService.getUserByUsername(username);			
			if(user != null){
				session.setAttribute("userId", user.getId());
	//			modelMap.addAttribute("user", user);
				boolean isAdmin = false;
				if(user.getRole() == AuthUserRole.ADMIN.getValue())
					isAdmin=true;
	//			modelMap.addAttribute("isAdmin", isAdmin);

			}
			String redirectUrl=(String) session.getAttribute("redirectUrl");
			if (redirectUrl!=null&&!redirectUrl.contains("login")&&redirectUrl.length()>7) {
				redirectUrl=redirectUrl.substring(5);
				return "redirect:"+redirectUrl;
			}
			return "clouddata";
		}else{
			return "login";
		}	
	}
	
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request, HttpServletResponse response){
		return "login";
	}
	
	@RequestMapping(value = "demo")
	public String demo(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "demo";
	}
	
	@RequestMapping(value = "uploadTest")
	public String uploadTest(){
		
		return "uploadTest";
	}
	@RequestMapping("/finance")
	public String finance(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "finance";
	}
	@RequestMapping("/operation")
	public String operation(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "operation";
	}
	@RequestMapping("/data")
	public String data(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "data";
	}
	@RequestMapping("/finance2")
	public String finance2(){
		return "finance2";
	}
	@RequestMapping("/operation2")
	public String operation2(){
		return "operation2";
	}
	@RequestMapping("/parkingInfo")
	public String data2(ModelMap modelMap,HttpSession session){
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
		return "parkingInfo";
	}
	/*@RequestMapping("/parkingInfo2")
	public String data22(ModelMap modelMap,HttpSession session){
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
		return "parkingInfo2";
	}*/
	@RequestMapping("/generalDataView")
	public String generalDataView(ModelMap modelMap,HttpSession session){
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
		return "generalDataView";
	}
	
	
	@RequestMapping("/generalDataView2")
	public String generalDataView2(ModelMap modelMap,HttpSession session){
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
		return "generalDataView2";
	}
	
	
	@RequestMapping("/outsideParkStatus1")
	public String outsideParkStatus1(){
		return "outsideParkStatus1";
	}
	@RequestMapping("/platformShow")
	public String platformShow(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "platformShow";
	}
	//new page
	@RequestMapping("/presentData")
	public String presentData(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "presentData";
	}
	@RequestMapping("/outsideParkStatus2")
	public String outsideParkStatus2(ModelMap modelMap,HttpSession session){
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
		return "outsideParkStatus2";
	}
	@RequestMapping("/clouddata")
	public String clouddata(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "clouddata";
	}
	@RequestMapping("/platformShowbk")
	public String platformShowbk(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "platformShowbk";
	}
	@RequestMapping("/parkBigData")
	public String parkBigData(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		return "parkBigData";
	}
}
