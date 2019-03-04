package com.park.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.park.model.Constants;
import com.util.UcSignature;

@Service
public class YanchengDataService {

	private String url = "http://218.92.191.54:1080/yancheng_barrier-pre-srv/";

	private String key = "5bcd079d169543c3a71671bfb0db2d7f";
	private static Log logger = LogFactory.getLog(YanchengDataService.class);
	public String ParkLotFreeSpace(String parkingNo, String updateTimestamp, String totalNumber, String freeSpaceNumber) {
//		Map<String, Object> result=new HashMap<>();
//		 String reqUrl = url + "/parklot_freespace";
//	        Map<String, String> params = new TreeMap<String, String>(new Comparator<String>() {
//	            public int compare(String key1, String key2) {
//	                return key1.compareTo(key2);
//	            }
//	        });
//	        params.put("parkingNo", parkingNo);
//	        params.put("totalNumber", totalNumber);
//	        params.put("freeSpaceNumber", freeSpaceNumber);
//	        params.put("updateTimestamp", new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//	        String msgSign = UcSignature.signature(params, key);
//	        params.put("msgSign", msgSign);
//	        String str=HttpPost.send(reqUrl, params);
//	        logger.info(str);
		return Utility.gson.toJson(null);
	
	}
}
