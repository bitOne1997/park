package com.park.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.park.service.UserPagePermissionService;


@Controller
public class ChargeController {
	
	
	
	@Autowired
	private UserPagePermissionService pageService;
	
	private static Log logger = LogFactory.getLog(ChargeController.class);
	
	@RequestMapping(value = "/getCharge", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCharge(@RequestBody Map<String, Object> argMap, HttpServletRequest request){
		String client_ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			client_ip =  request.getRemoteAddr();
		}else{
			client_ip = request.getHeader("x-forwarded-for");
		}
			  
		argMap.put("client_ip", "127.0.0.1");
		argMap.put("order_no", "test00020170606001");
		argMap.put("channel", "alipay_qr");
		argMap.put("livemode", false);
		 Map<String, String> app = new HashMap<String, String>();
	     app.put("id", "app_bX1SKKWbP4K0Ge1i");
		argMap.put("app",app);
		argMap.put("amount", 500);
		 Map<String, Object> extra = new HashMap<String, Object>();
		 argMap.put("extra", extra);
		return null;
		
	}
	


}
