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
import com.park.model.Parkshowtext;
import com.park.service.ParkShowTextService;
import com.park.service.Utility;
@Controller
@RequestMapping("parkShowText")

public class ParkShowTextController {

	@Autowired
	ParkShowTextService parkShowTextService;
	
	@RequestMapping(value="insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String insert(@RequestBody Parkshowtext parkshowtext){
		int num=parkShowTextService.insertSelective(parkshowtext);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="getByPark",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByPark(@RequestBody Map<String, Object> args){
		int parkId=(int) args.get("parkId");
		List<Parkshowtext> parkshowtexts=parkShowTextService.getByPark(parkId);

		Map<String, Object> result=new HashMap<>();
		if (!parkshowtexts.isEmpty()) {
			result.put("status", 1001);
			result.put("body", parkshowtexts);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="update",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String update(@RequestBody Parkshowtext parkshowtext){
		int num=parkShowTextService.updateByPrimaryKeySelective(parkshowtext);
		Map<String, Object> result=new HashMap<>();
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="updateRows",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String updateRows(@RequestBody List<Parkshowtext> parkshowtext){
		int num=0;
		for (Parkshowtext parkshowtext2 : parkshowtext) {
			num+=parkShowTextService.updateByPrimaryKeySelective(parkshowtext2);
		}
		
		Map<String, Object> result=new HashMap<>();
		if (num==2) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value="delete",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String delete(@RequestBody Map<String, Object> args){
		int id=(int) args.get("id");
		Map<String, Object> result=new HashMap<>();
		int num=parkShowTextService.deleteByPrimaryKey(id);
		if (num==1) {
			result.put("status", 1001);
		}
		else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}
}
