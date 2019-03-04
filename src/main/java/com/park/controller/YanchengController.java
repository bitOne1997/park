package com.park.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Pos;
import com.park.service.HttpUtil;
import com.park.service.Utility;
import com.util.UcSignature;


@Controller
@RequestMapping("yancheng")
public class YanchengController {
	 private String url = "http://218.92.191.54:1080/yancheng_barrier-pre-srv/";
	    private String parkingNo = "10028";
	    private String key = "5bcd079d169543c3a71671bfb0db2d7f";
	    @RequestMapping(value="insert",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
		@ResponseBody
		public String insert(){
			Map<String, Object> result=new HashMap<>();
			 String reqUrl = url + "/parklot_freespace";
		        Map<String, String> params = new TreeMap<String, String>(new Comparator<String>() {
		            public int compare(String key1, String key2) {
		                return key1.compareTo(key2);
		            }
		        });
		        params.put("parkingNo", parkingNo);
		        params.put("totalNumber", "30");
		        params.put("freeSpaceNumber", "25");
		        params.put("updateTimestamp", "2017-11-01 17:00:48");
		        String msgSign = UcSignature.signature(params, key);
		        params.put("msgSign", msgSign);
			//return Utility.gson.toJson(HttpPost.send(reqUrl, params));
		        return null;
		}

}
