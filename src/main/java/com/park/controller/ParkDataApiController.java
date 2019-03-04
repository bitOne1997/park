package com.park.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.park.service.PosChargeDataService;
import com.park.service.Utility;

@Controller
public class ParkDataApiController {
	@Autowired
	PosChargeDataService chargeSerivce;

	@RequestMapping(value = "/getByParkAndRange", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })
	@ResponseBody
	public String getByParkAndRange(@RequestBody Map<String, Object> args) {
		int parkId = Integer.parseInt((String) args.get("parkId"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(new Date());
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(today + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(today + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<PosChargeData> posChargeDatas = chargeSerivce.selectPosdataByParkAndRange(parsedStartDay, parsedEndDay,parkId);
		
		return null;
	}
}
