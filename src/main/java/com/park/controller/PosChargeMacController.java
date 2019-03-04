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

import com.park.model.PosChargeData;
import com.park.model.Poschargemac;
import com.park.service.HardwareService;
import com.park.service.PosChargeDataService;
import com.park.service.PosChargeMacService;
import com.park.service.Utility;

@Controller
@RequestMapping("poschargemac")
public class PosChargeMacController {

	@Autowired
	PosChargeMacService poschargemacService;
	@Autowired
	PosChargeDataService poschargedataService;
	@Autowired
	HardwareService hardwareService;
	@RequestMapping(value = "getLastByMac", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getLastByMac(@RequestBody Map<String, String> args){
		String mac=args.get("mac");
		int macId=hardwareService.macToId(mac);
		List<Poschargemac> poschargemacs=poschargemacService.selectByMac(macId, 1);
		PosChargeData posChargeData=poschargedataService.getById(poschargemacs.get(0).getPoschargeid());
		Map<String, Object> ret = new HashMap<String, Object>();
		if (posChargeData!=null) {
			ret.put("status", 1001);
			ret.put("body", posChargeData);
		}
		return Utility.gson.toJson(ret);
	}
}
