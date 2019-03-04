package com.park.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;
@Service
public class InvoiceService {
	
//	 Jedis jedis = new Jedis("106.15.231.85", 6390);
//	
//	public  InvoiceService() {
//		jedis.auth("liushuijiwusi");
//	}
	public String getTocken(){
		Map<String, Object> params=new HashMap<>();
		params.put("openid", "ceTSp96pAzxwTlmxy4c84CrooflGZsK3wHUOjMG2NzfX3SzWpvT1480038819783");
		params.put("app_secret","a8sm8zfFw9Wi9N392IOKCYISGRkaQMR5SlWSVRpasSfQShAA2G81499333176474");
		Map<String, Object> map=HttpUtil.post("http://open.hydzfp.com/open_access/access/token.open",params);
		Object data=map.get("body");
		 Gson gson = new Gson();
		 Map<String, Object> mapdata=gson.fromJson((String) data, new TypeToken<Map<String, Object>>(){
         }.getType() );
		 String result=(String)mapdata.get("result");
		 if (result.equals("SUCCESS")) {
			 Map<String, Object> mapdata2=(Map<String, Object>) mapdata.get("rows");
			 String accessToken=(String) mapdata2.get("access_token");
			 return accessToken;
		}
		 return null;
	}
	public Map<String, Object> getInvoice(){
		Map<String, Object> params=new HashMap<>();
		params.put("openid", "ceTSp96pAzxwTlmxy4c84CrooflGZsK3wHUOjMG2NzfX3SzWpvT1480038819783");
		params.put("app_secret","a8sm8zfFw9Wi9N392IOKCYISGRkaQMR5SlWSVRpasSfQShAA2G81499333176474");
		Map<String, Object> datap=new HashMap<>();
		datap.put("data_resources", "API");
		datap.put("nsrsbh", "91320104793709115w");
		datap.put("order_num", "20180830111658416a1a");
		datap.put("bmb_bbh", "12.0");
		datap.put("zsfs", "0");
		datap.put("tspz", "00");
		datap.put("xsf_nsrsbh", "91320104793709115w");
		datap.put("xsf_mc", "南京名都家居广场有限公司");
		datap.put("xsf_dzdh", "南京名都家居广场有限公司");
		datap.put("xsf_yhzh", "用户");
		datap.put("gmf_nsrsbh", "");
		datap.put("gmf_mc", "南京市");
		datap.put("gmf_dzdh", "");
		datap.put("gmf_yhzh", "");
		datap.put("kpr", "超级管理员");
		datap.put("skr", "");
		datap.put("fhr", "");
		datap.put("jshj", "10.6");
		datap.put("hjje", "10");
		datap.put("hjse", "0.6");
		datap.put("kce", "");
		datap.put("bz", "备注");
		datap.put("kpzdbs", "");
		
		Map<String, Object> datapc=new HashMap<>();		
		datapc.put("fphxz", "0");
		datapc.put("spbm", "1030201020000000000");
		datapc.put("zxbm", "");
		datapc.put("yhzcbs", "");
		datapc.put("lslbs", "");
		datapc.put("zzstsgl", "");
		datapc.put("xmmc", "停车费");
		datapc.put("ggxh", "");
		datapc.put("dw", "项");
		datapc.put("xmsl", "1");
		datapc.put("xmdj", "10.6");
		datapc.put("xmje", "10.6");
		datapc.put("sl", "0.06");
		datapc.put("se", "0.6");
		
		List<Map<String, Object>> datapclist=new ArrayList<>();
		datapclist.add(datapc);
		datap.put("common_fpkj_xmxx", datapclist);

		params.put("data", datap);
		Map<String, Object> map=HttpUtil.post("http://open.hydzfp.com/open_access/buss/exec.open",params);
		Object data=map.get("body");
		 Gson gson = new Gson();
		 Map<String, Object> mapdata=gson.fromJson((String) data, new TypeToken<Map<String, Object>>(){
         }.getType() );
		 String result=(String)mapdata.get("result");
		 if (result.equals("SUCCESS")) {
			 List<Map<String, Object>> mapdata2=(List<Map<String, Object>>) mapdata.get("rows");
			 Map<String, Object> resultRow=(Map<String, Object>) mapdata2.get(0);
			 return resultRow;
		}
		 return null;
	}
}
