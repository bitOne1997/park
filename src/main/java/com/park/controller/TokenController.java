package com.park.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.service.TokenService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/token")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value = "/{tokenId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String token(@PathVariable int tokenId){
		Map<String, Object> body = new HashMap<String, Object>();
		String ret = tokenService.getToken(tokenId);
		body.put("token", ret);
		return Utility.createJsonMsg(1001 , "sucessfully", body);
	}
}
