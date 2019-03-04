package com.park.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Users;
import com.park.service.UsersService;
import com.park.service.Utility;

@Controller
@RequestMapping("users")
public class UsersController {
	@Autowired
	UsersService usersService;
	
	@RequestMapping(value = "/getUsersCount", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getUsersCount() {
		int count=usersService.getCount();
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("count", count);
		ret.put("status", "1001");
		ret.put("message", "get user detail success");
		ret.put("body", Utility.gson.toJson(body));
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getUserDetail(@RequestParam("start") int start,
			@RequestParam("count") int count) {
		List<Users> users=usersService.getByCount(start, count);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (users != null) {
			ret.put("status", "1001");
			ret.put("message", "get user detail success");
			ret.put("body", Utility.gson.toJson(users));
		} else {
			ret.put("status", "1002");
			ret.put("message", "get user detail fail");
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insert(@RequestBody Users users){
		int num=usersService.insertSelective(users);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (num==1) {
			ret.put("status", 1001);
		}
		else {
			ret.put("status", 1002);
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String update(@RequestBody Users users){
		int userId=users.getId();
		Users usersOrigin=usersService.selectByPrimaryKey(userId);
		int num=usersService.updateByPrimaryKeySelective(users);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (num==1) {
			ret.put("status", 1001);
		}
		else {
			ret.put("status", 1002);
		}
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String update(@RequestBody Map<String, String> args){
		String userName=args.get("userName");
		String password=args.get("password");
		List<Users> users=usersService.getByUserNameAndPassword(userName, password);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!users.isEmpty()) {
			ret.put("status", 1001);
			ret.put("body", users.get(0));
		}
		else {
			ret.put("status", 1002);
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/getByUserName", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getByUserName(@RequestBody Map<String, String> args){
		String userName=args.get("userName");
		List<Users> users=usersService.getByUserName(userName);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!users.isEmpty()) {
			ret.put("status", 1001);
			ret.put("body", users);
		}
		else {
			ret.put("status", 1002);
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/reCharge", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String reCharge(@RequestBody Map<String, Object> args){
		Map<String, Object> ret = new HashMap<String, Object>();
		double money=(double) args.get("money");
		int userId=(int) args.get("userId");
		Users users=usersService.selectByPrimaryKey(userId);
		if (users==null) {
			ret.put("status", 1002);
			ret.put("message", "no users");
			return Utility.gson.toJson(ret);
		}
		users.setBalance((float) (users.getBalance()+money));
		int num=usersService.updateByPrimaryKey(users);
		
		if (num==1) {
			ret.put("status", 1001);
		}
		else {
			ret.put("status", 1002);
			ret.put("message", "failed");
		}
		return Utility.gson.toJson(ret);
	}
}
