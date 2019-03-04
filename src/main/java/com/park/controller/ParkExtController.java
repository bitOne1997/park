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


import com.park.model.Parkext;
import com.park.service.ParkExtService;
import com.park.service.Utility;

@Controller
@RequestMapping("parkext")
public class ParkExtController {

	@Autowired
	ParkExtService parkExtService;
	
	@RequestMapping(value="getByParkid", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByParkid(@RequestBody Map<String, Object> args) {
		int parkId=(int) args.get("parkId");
		List<Parkext> parkexts=parkExtService.selectByParkid(parkId);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (parkexts.isEmpty()) {
			ret.put("status", "1002");
			ret.put("msg", "无相应信息");
			return Utility.gson.toJson(ret);
		}
		ret.put("status", "1001");
		ret.put("msg", "success");
		ret.put("body", parkexts.get(0));
		return Utility.gson.toJson(ret);
	}
}
