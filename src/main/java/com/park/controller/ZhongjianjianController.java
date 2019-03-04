package com.park.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Constants;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.HttpUtil;
import com.park.service.ParkService;
import com.park.service.PosChargeDataService;
import com.park.service.Utility;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

@Controller
@RequestMapping("zjj")
public class ZhongjianjianController {
	@Autowired
	PosChargeDataService posChargeDataService;
	@Autowired
	ParkService parkService;

	private static Log logger = LogFactory.getLog(ZhongjianjianController.class);
	@RequestMapping(value = "/carIn", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public String carIn(@RequestBody Map<String, Object> args) {
		logger.info(args.toString());
		Map<String, Object> result=new HashMap<>();
		result.put("errorcode", 200);
		result.put("message", "成功");
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "/carOut", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public String carOut(@RequestBody Map<String, Object> args) {
		logger.info(args.toString());
		Map<String, Object> result=new HashMap<>();
		result.put("errorcode", 200);
		result.put("message", "成功");
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "/evt", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public String evt(@RequestBody Map<String, Object> args) throws Exception {
		logger.info("evt:"+args.toString());
		String evt=(String) args.get("evt");
		String plateNumber =(String) args.get("plateNumber");
		String picUrlIn =(String) args.get("picUrlIn");
		String timeIn =(String) args.get("timeIn");
		String parkingCode =(String) args.get("parkingCode");
		String parkingName =(String) args.get("parkingName");
		String happenTime =(String) args.get("happenTime");
		String parkingActId =(String) args.get("parkingActId");
		String berthCode=(String) args.get("berthCode");
		Map<String, Object> argMap=new HashMap<>();
		Map<String, Object> argMap2=new HashMap<>();
		Map<String, Object> result=new HashMap<>();
		
		
		
		Park park=parkService.getParkById(Integer.valueOf(parkingCode));
		
		if (evt.equals("evt.car.in")) {
			PosChargeData chargeData=new PosChargeData();
			chargeData.setCardNumber(plateNumber);
			chargeData.setEntranceDate(timeIn);
			chargeData.setParkId(Integer.valueOf(parkingCode));
			chargeData.setParkDesc(parkingName);
			chargeData.setPortNumber(berthCode);
			chargeData.setUrl(picUrlIn);
			posChargeDataService.insert(chargeData);
			
			//南阳推送
			argMap.put("plateNumber", plateNumber);
			argMap.put("parkName", parkingName);
			argMap.put("parkId", Integer.valueOf(parkingCode));
			argMap.put("portNumber", berthCode);
			argMap.put("entranceTime", happenTime);
			argMap.put("imagePath", picUrlIn);
			argMap.put("orderSN", String.valueOf(chargeData.getId()));
			logger.info("evtnanyangin"+argMap);
			HttpUtil.postNanyang("http://henanguanchao.com/v1/StreetParking/ReportEntrance", argMap);
			int leftcount=(park.getPortLeftCount()-1)<0?0:(park.getPortLeftCount()-1);
			parkService.updateLeftPortCount(park.getId(), leftcount>park.getPortCount()?park.getPortCount():leftcount);	
			argMap2.put("id", park.getId());
			argMap2.put("count",(park.getPortLeftCount()-1)<0?0:(park.getPortLeftCount()-1));
			HttpUtil.postNanyang("http://henanguanchao.com/v1/StreetParking/ReportParkingSpace", argMap2);
		}
		if (evt.equals("evt.car.out")) {
			List<PosChargeData> posChargeDatas=posChargeDataService.getByCardNumberAndPark(plateNumber, Integer.valueOf(parkingCode));		
			if (posChargeDatas.isEmpty()) {
				logger.info(plateNumber+"无入场记录");
				result.put("errorcode", 0);
				result.put("message", "无入场记录");
				return Utility.gson.toJson(result);
			}
			PosChargeData posChargeData=posChargeDatas.get(0);
			
			argMap.put("plateNumber", plateNumber);
			argMap.put("parkName", parkingName);
			argMap.put("portNumber", berthCode);
			argMap.put("parkId", Integer.valueOf(parkingCode));
			argMap.put("entranceTime", new SimpleDateFormat(Constants.DATEFORMAT).format(posChargeData.getEntranceDate()));
			String picUrlOut =(String) args.get("picUrlOut");
			argMap.put("imagePath", picUrlOut);
			String exitTime=(String)args.get("timeOut");
			argMap.put("exitTime", exitTime);
			argMap.put("orderSN", String.valueOf(posChargeData.getId()));
			
			
			//南阳
			int diff=(int) ((new SimpleDateFormat(Constants.DATEFORMAT).parse(exitTime).getTime()-posChargeData.getEntranceDate().getTime())/(1000*60));
			argMap.put("diffTime", diff);
		//	posChargeData.setExitDate(exitTime);
			Date exitDate=new Date();
			if (diff<0) {
				exitDate=posChargeData.getEntranceDate();
				posChargeData.setOther2("高桩视频人工审核");				
				posChargeData.setExitDate1(exitDate);
				argMap.put("exitTime", new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate));
			}
			
			posChargeDataService.update(posChargeData);
			argMap.put("plateNumber", plateNumber);
			HttpUtil.postNanyang("http://henanguanchao.com/v1/StreetParking/ReportExit", argMap);
			logger.info("evtnanyangout"+argMap);
			parkService.updateLeftPortCount(park.getId(), park.getPortLeftCount()+1);			
			argMap2.put("id", park.getId());
			argMap2.put("count",(park.getPortLeftCount()+1)>park.getPortCount()?park.getPortCount():(park.getPortLeftCount()+1));
			
			try {
				if (posChargeData!=null) {
					posChargeDataService.getDebtWithParkId(posChargeData.getCardNumber(), new SimpleDateFormat(Constants.DATEFORMAT).parse(exitTime), posChargeData.getParkId());
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}					
		}
		
		
		HttpUtil.postNanyang("http://henanguanchao.com/v1/StreetParking/ReportParkingSpace", argMap2);
		result.put("errorcode", 0);
		result.put("message", "成功");
		return Utility.gson.toJson(result);
		
	}
}
