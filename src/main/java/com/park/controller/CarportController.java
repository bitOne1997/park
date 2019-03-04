package com.park.controller;


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
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Carport;
import com.park.model.CarportStatusDetail;
import com.park.model.Page;
import com.park.model.ParkNews;
import com.park.service.AuthorityService;
import com.park.service.CarportService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class CarportController {

	@Autowired
	private CarportService carportService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private AuthorityService authService;
	
	private static Log logger = LogFactory.getLog(UserController.class);
	
	
	@RequestMapping(value = "/carport/{Id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String carportDetail(@PathVariable int Id, ModelMap modelMap, HttpServletRequest request){
		Carport carport = carportService.getCarportById(Id);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(carport != null){
			retMap.put("status", "1001");
			retMap.put("message", "get carport success");
			logger.info("get carport success: " + carport.toString());
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "get carport fail");
			logger.info("get carport fail: null" );
		}
		retMap.put("body", carport);
		return Utility.gson.toJson(retMap);
	
		
	}
	
	@RequestMapping(value = "/insert/carport", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertCarport(@RequestBody Carport carport){			
		int ret = carportService.insertCarport(carport);
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		if(ret > 0){
			retMap.put("status", "1001");
			retMap.put("message", "inset success");
			logger.info("insert carport success: " + carport.toString());
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "inset fail");
			logger.info("insert carport fail: " + carport.toString());
		}
		return Utility.gson.toJson(retMap);
		
	}
	
	@RequestMapping(value = "/carports", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String carportsList(HttpServletRequest request){
		List<Carport> carports = carportService.getCarports();
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(carports != null){
			retMap.put("status", "1001");
			retMap.put("message", "get carport success");
			logger.info("get carports success: " + carports.toString());
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "get carport fail");
			logger.info("get carports fail ");
		}
		retMap.put("body", carports);
		return Utility.gson.toJson(retMap);	
	}
	
	@RequestMapping(value="/carportstatus")
	public String carportstatus(){
		return "carportStatusShow";
	}
	@RequestMapping(value="/qhq_carportstatus")
	public String qhq_carportstatus(ModelMap modelMap, HttpServletRequest request, HttpSession session){
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
		
		return "qhq_carport";
	}
	
	@RequestMapping(value = "/specify/carports", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getSpecifyCarports(@RequestBody Map<String, Object> args, HttpServletRequest request){
		
		int start = (int)args.get("start");
		int counts = (int)args.get("counts");
		
		String orderInfo = (String)(args.get("sort"));
		String[] orderArray = orderInfo.split(" ");
		
		List<Carport> carports = null;
		
		String queryCondition = " ";
		
		boolean cityCondition = args.containsKey("city");
		if(cityCondition){
			queryCondition += "city=\"";
			queryCondition += (String) args.get("city");
			queryCondition += "\" ";
		}
		
		boolean filterCondition = args.containsKey("filter");
		
		if(filterCondition){	
			String filter = (String)args.get("filter");
			if(cityCondition)
				queryCondition += " and ";		
			if(filter.equals("out")){
				queryCondition += "isout = 1";
			}else if(filter.equals("in")){
				queryCondition += "isout= 0";
			}else if(filter.equals("mine")){
				queryCondition += "uid=";
				queryCondition += (int)args.get("uid");
			}
		}
		
		if(filterCondition || cityCondition){
			carports = carportService.getConditionCarports(start, counts, orderArray[0], orderArray[1], queryCondition);
		}else{
			carports = carportService.getSpecifyCarports(start, counts, orderArray[0], orderArray[1]);
		}
			
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(carports != null){
			retMap.put("status", "1001");
			retMap.put("message", "get carport success");
			logger.info("get carports success: " + carports.toString());
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "get carport fail");
			logger.info("get carports fail ");
		}
		retMap.put("body", carports);
		return Utility.gson.toJson(retMap);	
	}
	
	@RequestMapping(value = "/carportExpense", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getSpecifyCarports1(@RequestBody Map<String, Object> args, HttpServletRequest request){
		
		String name = args.get("parkName").toString();
		String carportNumber = args.get("carportNumber").toString();
		String cardNumber = args.get("cardNumber").toString();
		int type = (int)args.get("type");
		
		double sum = carportService.calExpense(name, carportNumber, cardNumber, type);
		
		if(sum > 0)
			return Utility.createJsonMsg(1002, "get expense success", sum);
		else
			return Utility.createJsonMsg(1001, "insert expense success");
			
	}
	

}
