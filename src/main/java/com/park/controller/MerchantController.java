package com.park.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Merchant;
import com.park.service.MerchantService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMerchant(@PathVariable int id){
		Merchant merchant = merchantService.getMerchantById(id);
		if(merchant != null)
			return Utility.createJsonMsg(1001, "get merchante success", merchant);
		else
			return Utility.createJsonMsg(1002, "get merchant fail");
	}
	
	@RequestMapping(value = "/getNear", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getNearMerchant(@RequestBody Map<String, Object> args){
		double longitude = Double.parseDouble(args.get("longitude").toString());
		double latitude = Double.parseDouble(args.get("latitude").toString());
		double radius = Double.parseDouble(args.get("radius").toString());
		
		List<Merchant> merchants = merchantService.getNearMerchant(longitude, latitude, radius);
		return Utility.createJsonMsg(1001, "get merchants success", merchants);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertMerchant(@RequestBody Merchant merchant){
		merchant.setDate(new Date());
		if(merchantService.insertMerchant(merchant) > 0)
			return Utility.createJsonMsg(1001, "insert merchant successfully");
		else
			return Utility.createJsonMsg(1002, "insert merchant fail");
	}
	
	@RequestMapping(value = "/updateField", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateField(@RequestBody Map<String, Object> args){
		
		if(!args.containsKey("id"))
			return Utility.createJsonMsg(1002, "need park id");
		
		int merchantId = (int) args.get("id");
		Merchant merchant = merchantService.getMerchantById(merchantId);
		if(merchant == null)
			return Utility.createJsonMsg(1003, "no this merchant, cannot update");
		
		Utility.updateObjectField(merchant, "com.park.model.Merchant", args);
		
		if(merchantService.updateMerchant(merchant) > 0)
			return Utility.createJsonMsg(1001, "update successfully");
		else
			return Utility.createJsonMsg(1004, "update fail");
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteMerchant(@PathVariable int id){
		if(merchantService.deleteMerchant(id) > 0)
			return Utility.createJsonMsg(1001, "delete merchant successfully");
		else
			return Utility.createJsonMsg(1002, "delete merchant fail");
			
	}
	

}
