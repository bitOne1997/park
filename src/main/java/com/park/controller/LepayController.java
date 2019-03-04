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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Lepayrecord;
import com.park.model.Page;
import com.park.service.AuthorityService;
import com.park.service.LepayRecordService;
import com.park.service.LepayService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("lepay")
public class LepayController {
	@Autowired
	private AuthorityService authService;	
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private LepayRecordService lepayrecordService;
	@RequestMapping(value="/pay",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String pay(@RequestBody Map<String, Object> args){
		Integer amount=(Integer)args.get("amount");
		int type=(int) args.get("type");
		Map<String, Object> result=new HashMap<>();
		LepayService lepayService=new LepayService();		
		Map<String, Object> data=lepayService.saomaPay((long)amount, type);
		if (data!=null) {
			result.put("status", 1001);
			result.put("body", data);
		}
		else {
			result.put("status", 1002);
		}		
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="/quickPay",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String quickPay(@RequestBody Map<String, Object> args){
		Integer amount=(Integer)args.get("amount");
		int type=(int) args.get("type");
		Map<String, Object> result=new HashMap<>();
		LepayService lepayService=new LepayService();		
		Map<String, Object> data=lepayService.quickPay((long)amount);
		if (data!=null) {
			result.put("status", 1001);
			result.put("body", data);
		}
		else {
			result.put("status", 1002);
		}		
		return Utility.gson.toJson(result);
	}
	
	@RequestMapping(value="/payByPosChargeDataId",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String payByPosChargeDataId(@RequestBody Map<String, Object> args){
		Integer amount=(Integer)args.get("amount");
		int type=(int) args.get("type");
		int poschargeId=(int) args.get("poschargedataId");
		Map<String, Object> result=new HashMap<>();
		LepayService lepayService=new LepayService();		
		Map<String, Object> data=lepayService.saomaPayByPosdataId((long)amount, type,poschargeId);
		if (data!=null) {
			result.put("status", 1001);
			result.put("body", data);
		}
		else {
			result.put("status", 1002);
		}		
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "/record", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String parks(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
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
		return "lepayRecord";
	}
	@RequestMapping(value="/record/getAmount",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getAmount(){
		Integer result=lepayrecordService.getAmount();
		return Utility.createJsonMsg(1001, "success", result);
	}
	@RequestMapping(value="/record/getByCount",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String getByCount(@RequestBody Map<String, String> args){		
		Integer start=Integer.parseInt(args.get("start"));
		Integer count=Integer.parseInt(args.get("count"));
		List<Lepayrecord> lepayrecords=lepayrecordService.getByCount(start, count);
		return Utility.createJsonMsg(1001, "success", lepayrecords);
	}
}
