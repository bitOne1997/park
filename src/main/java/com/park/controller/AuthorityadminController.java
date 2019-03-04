package com.park.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Authorityadmin;
import com.park.service.AuthorityadminService;
import com.park.service.Utility;

@Controller
@RequestMapping("authorityadmin")
public class AuthorityadminController {
	@Autowired
	AuthorityadminService authorityadminService;
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Authorityadmin authorityadmin){
		Map<String, Object> result=new HashMap<>();
		int num=authorityadminService.insert(authorityadmin);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getByToken",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByToken(@RequestBody Map<String, Object> args){
		Map<String, Object> result=new HashMap<>();
		String token=(String) args.get("token");
		List<Authorityadmin> authorityadmins=authorityadminService.getByToken(token);
		if (!authorityadmins.isEmpty()) {
			result.put("status", 1001);
			result.put("body", authorityadmins.get(0));
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String update(@RequestBody Authorityadmin authorityadmin){
		Map<String, Object> result=new HashMap<>();
		int num=authorityadminService.updateByPrimaryKeySelective(authorityadmin);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	
	
}
