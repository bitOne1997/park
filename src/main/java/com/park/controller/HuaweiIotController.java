package com.park.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Constants;
import com.park.service.Utility;

@Controller
@RequestMapping("iot")
public class HuaweiIotController {
	@RequestMapping(value="notify",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	public void Notify(@RequestBody Map<String, Object> args){
		Constants.iotData=args;
	}
	@RequestMapping(value="getNotify",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getNotify(){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", 1001);
		ret.put("body", Constants.iotData);
		return Utility.createJsonMsg(1001,"ok",Constants.iotData);
	}
}
