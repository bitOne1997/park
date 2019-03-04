package com.park.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.service.InvoiceService;
import com.park.service.Utility;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("invoice")
public class InvoiceController {
	@Autowired
	InvoiceService invoiceService;
	@RequestMapping(value="putJedisTocken",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String putJedisTocken(){
		Jedis jedis = new Jedis("106.15.231.85", 6390);
		jedis.auth("liushuijiwusi");
		String accessToken=invoiceService.getTocken();
		jedis.set("invoiceAccessToken", accessToken);
		jedis.expire("invoiceAccessToken", 5000);
		return Utility.createJsonMsg(1001, accessToken);
	}
	
}
