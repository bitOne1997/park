package com.park.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.Area;
import com.park.model.AuthUser;
import com.park.model.Outsideparkinfo;
import com.park.model.Park;
import com.park.model.Street;
import com.park.model.Zonecenter;
import com.park.service.AreaService;
import com.park.service.ChargeDataService;
import com.park.service.MonthUserService;
import com.park.service.OutsideParkInfoService;
import com.park.service.ParkService;
import com.park.service.PosChargeDataService;
import com.park.service.StreetService;
import com.park.service.Utility;
import com.park.service.ZoneCenterService;

@Controller
@RequestMapping("outsideParkInfo")
public class OutsideParkInfoController {
	@Autowired
	private ZoneCenterService zoneCenterService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private StreetService streetService;
	@Autowired
	private ParkService parkService;
	@Autowired
	private PosChargeDataService posChargeDataService;
	@Autowired
	private OutsideParkInfoService outsideParkInfoService;
	@Autowired
	private MonthUserService monthuserService;

	@RequestMapping(value = "monthUserInfo", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String monthUserInfo(HttpSession session) {
		String username = (String) session.getAttribute("username");
		List<Park> parkList = parkService.getParks();
		if (username != null) {
			parkList = parkService.filterPark(parkList, username);
		}
		long monthUserCount = 0;
		double payment = 0;
		for (Park park : parkList) {
			Map<String, Object> tmpdata = monthuserService.statisticsInfo(park.getId(), 0);
			if (tmpdata != null) {
				monthUserCount += tmpdata.get("count") == null ? 0 : (long) tmpdata.get("count");
				payment += tmpdata.get("payment") == null ? 0 : (double) tmpdata.get("payment");
			}

		}
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		result.put("count", monthUserCount);
		result.put("payment", payment);
		ret.put("body", result);
		ret.put("status", 1001);
		return Utility.gson.toJson(ret);
	}

	/*@RequestMapping(value = "zoneCenterInfo", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String zoneCenterInfo(@RequestParam("start") int start, @RequestParam("count") int count,
			HttpSession session) {
		String username = (String) session.getAttribute("username");
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> info = new ArrayList<>();
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Zonecenter> zonecenters = zoneCenterService.getByUserName(username);
		for (Zonecenter zonecenter : zonecenters) {// 获取市
			Map<String, Object> tmpdata = new HashMap<>();
			tmpdata.put("id", zonecenter.getId());
			tmpdata.put("zonenum", zonecenter.getNum());
			tmpdata.put("zonename", zonecenter.getName());
			List<Area> areas = areaService.getByZoneCenterId(zonecenter.getId());
			tmpdata.put("areacount", areas.size());
			int streetCount = 0;
			int parkCount = 0;
			int carportCount = 0;
			int carportLeftCount = 0;
			float amountMoney = 0.0f;
			float realMoney = 0.0f;
			float arrearage = 0.0f;
			int entranceCount = 0;
			int outCount = 0;
			int onlineParkCount = 0;
			int onlineCarportCount = 0;
			for (Area area : areas) {// 获取区
				List<Street> streets = streetService.getByArea(area.getId());
				streetCount += streets.size();
				for (Street street : streets) {// 获取街道
					List<Park> parks = parkService.getOutsideParkByStreetId(street.getId());
					parkCount += parks.size();
					for (Park park : parks) {// 获取停车场
						Outsideparkinfo parkInfo = posChargeDataService.getOutsideparkinfoByOrigin(park.getId(), day);
						System.out.println("parkInfo:---------数据-----------" + parkInfo);
						if (parkInfo != null) {
							carportCount += parkInfo.getCarportcount();
							System.out.println("carportCount:" + carportCount);
							carportLeftCount += parkInfo.getUnusedcarportcount();
							System.out.println("carportLeftCount:" + carportLeftCount);
							amountMoney += parkInfo.getAmountmoney();
							System.out.println("amountMoney:" + amountMoney);
							realMoney += parkInfo.getRealmoney();
							System.out.println("realMoney:" + realMoney);
							arrearage += parkInfo.getArrearage();
							System.out.println("arrearage:" + arrearage);
							entranceCount += parkInfo.getEntrancecount();
							System.out.println("entranceCount:" + entranceCount);
							outCount += parkInfo.getOutcount();
							System.out.println("outCount:" + outCount);
							if (parkInfo.getPossigndate() == null) {
								continue;
							}
							Long signDate = parkInfo.getPossigndate().getTime();
							Long diffMin = ((new Date()).getTime() - signDate) / (1000 * 60);
							if (diffMin <= 30) {
								onlineParkCount += 1;
								onlineCarportCount += parkInfo.getCarportcount();
								System.out.println("onlineCarportCount:" + onlineCarportCount);
							}
						}
					}
				}
			}
			tmpdata.put("streetcount", streetCount);
			tmpdata.put("parkcount", parkCount);
			tmpdata.put("carportcount", carportCount);
			tmpdata.put("carportleftcount", carportLeftCount);
			tmpdata.put("amountmoney", amountMoney);
			tmpdata.put("realmoney", realMoney);
			tmpdata.put("arrearage", arrearage);
			tmpdata.put("entrancecount", entranceCount);
			tmpdata.put("outcount", outCount);
			tmpdata.put("onlineparkcount", onlineParkCount);
			tmpdata.put("onlinecarportcount", onlineCarportCount);
			info.add(tmpdata);
			System.out.println("全部信息:----------------" + tmpdata);
		}
		result.put("status", 1001);
		result.put("body", info);
		return Utility.gson.toJson(result);
	}*/



	@RequestMapping(value = "zoneCenterInfo", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String zoneCenterInfo(@RequestParam("start") int start, @RequestParam("count") int count,
			HttpSession session) {

		String username = (String) session.getAttribute("username");
		String startDay=null;
		String endDay=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = String.valueOf(sdf.format(new Date()));
		String date1 = String.valueOf(sdf.format(new Date()));
		try {
			startDay = date+" 00:00:00";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			endDay = date+" 23:59:59";
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Zonecenter> zonecenters = zoneCenterService.getByUserName(username);
		List<Object> listResult=new ArrayList<Object>();
		int zonecenterId=-1;
		for (Zonecenter zonecenter : zonecenters) {// 获取市
			zonecenterId=zonecenter.getId();
			Map<String,Object> posdata = posChargeDataService.selectPosdataSumByParkAndRangeMoney(zonecenterId,startDay,endDay);
			System.out.println("全部信息:----------------" + posdata);
			listResult.add(posdata);
		}
		return Utility.createJsonMsg(1001, "success", listResult);
	}


	@RequestMapping(value = "/areaInfo/{zoneid}", method = RequestMethod.GET, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String areaInfo(@PathVariable("zoneid") int zoneid) {
		Map<String, Object> result = new HashMap<>();
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Area> areas = areaService.getByZoneCenterId(zoneid);
		List<Map<String, Object>> info = new ArrayList<>();
		for (Area area : areas) {
			Map<String, Object> tmpdata = new HashMap<>();
			tmpdata.put("id", area.getId());
			tmpdata.put("areanum", area.getNumber());
			tmpdata.put("areaname", area.getName());
			int streetCount = 0;
			int parkCount = 0;
			int carportCount = 0;
			int carportLeftCount = 0;
			float amountMoney = 0;
			float realMoney = 0;
			float arrearage = 0;
			int entranceCount = 0;
			int outCount = 0;
			int onlineParkCount = 0;
			int onlineCarportCount = 0;
			List<Street> streets = streetService.getByArea(area.getId());
			streetCount += streets.size();
			for (Street street : streets) {
				List<Park> parks = parkService.getOutsideParkByStreetId(street.getId());
				parkCount += parks.size();
				for (Park park : parks) {
					Outsideparkinfo parkInfo = posChargeDataService.getOutsideparkinfoByOrigin(park.getId(), day);
					if (parkInfo == null) {
						continue;
					}
					carportCount += parkInfo.getCarportcount();
					carportLeftCount += parkInfo.getUnusedcarportcount();
					amountMoney += parkInfo.getAmountmoney();
					realMoney += parkInfo.getRealmoney();
					arrearage += parkInfo.getArrearage();
					entranceCount += parkInfo.getEntrancecount();
					outCount += parkInfo.getOutcount();
					if (parkInfo.getPossigndate() == null) {
						continue;
					}
					Long signDate = parkInfo.getPossigndate().getTime();
					Long diffMin = ((new Date()).getTime() - signDate) / (1000 * 60);
					if (diffMin <= 30) {
						onlineParkCount += 1;
						onlineCarportCount += parkInfo.getCarportcount();
					}
				}
			}
			tmpdata.put("streetcount", streetCount);
			tmpdata.put("parkcount", parkCount);
			tmpdata.put("carportcount", carportCount);
			tmpdata.put("carportleftcount", carportLeftCount);
			tmpdata.put("amountmoney", amountMoney);
			tmpdata.put("realmoney", realMoney);
			tmpdata.put("arrearage", arrearage);
			tmpdata.put("entrancecount", entranceCount);
			tmpdata.put("outcount", outCount);
			tmpdata.put("onlineparkcount", onlineParkCount);
			tmpdata.put("onlinecarportcount", onlineCarportCount);
			info.add(tmpdata);
		}
		result.put("status", 1001);
		result.put("body", info);
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/streetInfo/{areaid}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String streetInfo(@PathVariable("areaid") int areaid) {
		Map<String, Object> result = new HashMap<>();
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Street> streets = streetService.getByArea(areaid);
		List<Map<String, Object>> info = new ArrayList<>();
		for (Street street : streets) {
			Map<String, Object> tmpdata = new HashMap<>();
			tmpdata.put("id", street.getId());
			tmpdata.put("streetnum", street.getNumber());
			tmpdata.put("streetname", street.getName());
			tmpdata.put("contact", street.getContact());
			tmpdata.put("phone", street.getPhone());
			int parkCount = 0;
			int carportCount = 0;
			int carportLeftCount = 0;
			float amountMoney = 0;
			float realMoney = 0;
			float arrearage = 0;
			int entranceCount = 0;
			int outCount = 0;
			int onlineParkCount = 0;
			int onlineCarportCount = 0;
			List<Park> parks = parkService.getOutsideParkByStreetId(street.getId());
			parkCount += parks.size();
			for (Park park : parks) {
				Outsideparkinfo parkInfo = posChargeDataService.getOutsideparkinfoByOrigin(park.getId(), day);
				if (parkInfo == null) {
					continue;
				}
				carportCount += parkInfo.getCarportcount();
				carportLeftCount += parkInfo.getUnusedcarportcount();
				amountMoney += parkInfo.getAmountmoney();
				realMoney += parkInfo.getRealmoney();
				arrearage += parkInfo.getArrearage();
				entranceCount += parkInfo.getEntrancecount();
				outCount += parkInfo.getOutcount();
				if (parkInfo.getPossigndate() == null) {
					continue;
				}
				Long signDate = parkInfo.getPossigndate().getTime();
				Long diffMin = ((new Date()).getTime() - signDate) / (1000 * 60);
				if (diffMin <= 30) {
					onlineParkCount += 1;
					onlineCarportCount += parkInfo.getCarportcount();
				}
			}
			tmpdata.put("parkcount", parkCount);
			tmpdata.put("carportcount", carportCount);
			tmpdata.put("carportleftcount", carportLeftCount);
			tmpdata.put("amountmoney", amountMoney);
			tmpdata.put("realmoney", realMoney);
			tmpdata.put("arrearage", arrearage);
			tmpdata.put("entrancecount", entranceCount);
			tmpdata.put("outcount", outCount);
			tmpdata.put("onlineparkcount", onlineParkCount);
			tmpdata.put("onlinecarportcount", onlineCarportCount);
			info.add(tmpdata);
		}
		result.put("status", 1001);
		result.put("body", info);
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/parkInfo/{streetid}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfo(@PathVariable("streetid") int streetid) {
		Map<String, Object> result = new HashMap<>();
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Park> parks = parkService.getOutsideParkByStreetId(streetid);
		List<Map<String, Object>> info = new ArrayList<>();
		for (Park park : parks) {
			Outsideparkinfo parkInfo = posChargeDataService.getOutsideparkinfoByOrigin(park.getId(), day);
			if (parkInfo == null) {
				continue;
			}
			Map<String, Object> tmpdata = new HashMap<>();
			tmpdata.put("id", park.getId());
			tmpdata.put("contactnum", park.getNumber());
			tmpdata.put("contactname", park.getContact());
			tmpdata.put("parkname", park.getName());
			tmpdata.put("carportcount", parkInfo.getCarportcount());
			tmpdata.put("carportleftcount", parkInfo.getUnusedcarportcount());
			tmpdata.put("amountmoney", parkInfo.getAmountmoney());
			tmpdata.put("realmoney", parkInfo.getRealmoney());
			tmpdata.put("arrearage", parkInfo.getArrearage());
			tmpdata.put("entrancecount", parkInfo.getEntrancecount());
			tmpdata.put("outcount", parkInfo.getOutcount());
			if (parkInfo.getPossigndate() == null) {
				tmpdata.put("isonline", false);
				info.add(tmpdata);
				continue;
			}
			Long signDate = parkInfo.getPossigndate().getTime();
			Long diffMin = ((new Date()).getTime() - signDate) / (1000 * 60);
			if (diffMin >= 30) {
				tmpdata.put("isonline", false);
			} else {
				tmpdata.put("isonline", true);
			}
			info.add(tmpdata);
		}
		result.put("status", 1001);
		result.put("body", info);
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String update(@RequestBody Outsideparkinfo info) {
		Map<String, Object> result = new HashMap<>();
		int num = outsideParkInfoService.updateByPrimaryKeySelective(info);
		if (num == 1) {
			result.put("status", 1001);
		} else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/getInfoByParkId/{parkId}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getInfoByParkId(@PathVariable("parkId") int parkId) {
		Map<String, Object> result = new HashMap<>();
		Outsideparkinfo outsideparkinfo = outsideParkInfoService.getByParkidAndDate(parkId);
		if (outsideparkinfo != null) {
			result.put("status", 1001);
			result.put("body", outsideparkinfo);
		} else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/posHeartBeat/{parkId}", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String posHeartBeat(@PathVariable("parkId") int parkId) {
		Map<String, Object> result = new HashMap<>();
		Outsideparkinfo outsideparkinfo = outsideParkInfoService.getByParkidAndDate(parkId);
		outsideparkinfo.setPossigndate(new Date());
		int num = outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		if (num == 1) {
			result.put("status", 1001);
		} else {
			result.put("status", 1002);
		}
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "testDelay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testDelay(@RequestBody Map<String, Integer> args) {
		Integer delay = args.get("delay");
		try {
			Thread.sleep(1000 * delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("message", "ok");
		return Utility.gson.toJson(result);
	}
}
