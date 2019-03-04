package com.park.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.language.Caverphone1;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Channel;
import com.park.model.Hardware;
import com.park.model.HardwareDetail;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.Status;
import com.park.service.AuthorityService;
import com.park.service.ChannelService;
import com.park.service.HardwareService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class HardwareController {
	
	@Autowired
	private HardwareService hardwareService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	ParkService parkService;
	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@RequestMapping(value = "/hardware", produces = {"application/json;charset=UTF-8"})
	public String hardwares(ModelMap modelMap, HttpServletRequest request, HttpSession session){
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		boolean isAdmin = false;
		if(user != null){
			modelMap.addAttribute("user", user);
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
	//	if(isAdmin)
			return "hardware";
	//	else
	//		return "/login";
	}

	
	@RequestMapping(value = "/getHardwares", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getHardwares(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Hardware> hardwareList = hardwareService.getHardwares();
		ret.put("status", "1001");
		ret.put("message", "get hardware detail success");
		ret.put("body", hardwareList);
		
		return Utility.gson.toJson(ret);
	}
	
	@RequestMapping(value = "/getUnusedhardwares", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getUnusedHardwares(ModelMap modelMap, HttpServletRequest request){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Hardware> hardwareList = hardwareService.getUnusedHardwares();
		ret.put("status", "1001");
		ret.put("message", "get hardware detail success");
		ret.put("body", hardwareList);		
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value="/register/channel",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String registerChannel(@RequestBody Map<String, Object> args){
		String macId=(String)args.get("mac");
		String parkId=(String)args.get("parkId");
		String channelId=(String)args.get("channelId");
		String channelFlag=(String)args.get("channelFlag");
		String hdDescription=(String)args.get("hardwareDescription");
		String channelDescription =(String)args.get("channelDescription");
		Hardware hd=new Hardware();
		hd.setMac(macId);
		if (Integer.parseInt(channelFlag)==2) {
			hd.setType(2);
		} else {
			hd.setType(1);
		}
		hd.setStatus(1);
		hd.setDescription(hdDescription);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		hd.setDate(time);
		Channel chl=new Channel();
		
		if (hardwareService.insertHardware(hd)>0) {
			int macid=hardwareService.macToId(macId);
			chl.setMac(macid);
			chl.setChannelId(Integer.parseInt(channelId));
			chl.setChannelFlag(Integer.parseInt(channelFlag));		
			chl.setParkId(Integer.parseInt(parkId));
			chl.setStatus(1);
			chl.setIsEffective(1);
			chl.setDescription(channelDescription);
			chl.setDate(time);
			Hardware hardware = hardwareService.getHardwareById(macid);
			if(hardware.getStatus() == Status.USED.getValue())
				return Utility.createJsonMsg("msg", "hardare is being used");
			if(channelService.insertChannel(chl)){
				hardwareService.bindHardware(macid);
				return Utility.createJsonMsg(1001, "success");
		} else {
			return Utility.createJsonMsg("1002","register failure");
		}
	}
		else {
			return Utility.createJsonMsg("1002", "insert hardware error");
		}	
	}
	@RequestMapping(value = "/getUnBoundHardwares/{type}", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getUnBoundHardwares(@PathVariable int type){
		List<Hardware> hardwareList = hardwareService.getUnboundHardwares(type);
		return Utility.createJsonMsg("1001", "get hardware detail success", hardwareList);
	}
	
	@RequestMapping(value = "/getHardwareCount", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String gethardwareCount(){
		Map<String, Object> body = new HashMap<String, Object>();
		int count = hardwareService.getHardwareCount();
		body.put("count", count);
		return Utility.createJsonMsg("1001", "get hardware detail success", body);					
	}
	
	@RequestMapping(value = "/getHardwareDetail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String accessIndex(@RequestParam("low")int low, @RequestParam("count")int count){	
		Map<String, Object> ret = new HashMap<String, Object>();
		List<HardwareDetail> hardwareDetail = hardwareService.getHardwareDetail(low, count);
		if(hardwareDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get hardware detail success");
			ret.put("body", hardwareDetail);
		}else{
			ret.put("status", "1002");
			ret.put("message", "get hardware detail fail");
		}
		return Utility.gson.toJson(ret);
		
	}
	@RequestMapping(value="/searchHardwareByKeywords",produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String searchHardwareByKeywords(@RequestParam("mac")String mac) throws UnsupportedEncodingException{
		mac=new String(mac.getBytes("ISO-8859-1"), "UTF-8");
		Map<String, Object> ret=new HashMap<>();
		mac=mac.trim();
		List<Hardware> hardwares=hardwareService.searchHardwareByKeywords(mac);
		if(hardwares != null)
		{
			ret.put("state", "1001");
			ret.put("message", "search hardware success");
			ret.put("body", hardwares);
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/search/hardware", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String searchHardware(@RequestParam("mac")String mac){
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Hardware> hardware= hardwareService.searchHardware(mac);
		if(hardware != null)
		{
			retMap.put("state", "1001");
			retMap.put("message", "search hardware success");
			retMap.put("body", hardware);
		}
		else{
			retMap.put("state", "1002");
			retMap.put("message", "search hardware fail");
		}
		return  Utility.gson.toJson(retMap);
	}
	
	@RequestMapping(value = "/insert/hardware", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertHardware1(@RequestBody Hardware hardware){
		
		if(hardwareService.checkHardwareExist(hardware.getMac()))
			return Utility.createJsonMsg("1002", "mac exists");
		int ret =  hardwareService.insertHardware(hardware);
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(ret > 0 ){
			retMap.put("status", "1001");
			retMap.put("message", "insert hardware detail success");
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "mac exist");
		}
		return Utility.gson.toJson(retMap);
	}
	
	@RequestMapping(value = "/hardware/exist", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String checkHardwareExist(@RequestBody Map<String, Object> args){
		String mac = (String)args.get("mac");
		if(hardwareService.checkHardwareExist(mac))
			return Utility.createJsonMsg("1002", "mac exists");
		else 
			return Utility.createJsonMsg("1001", "mac does not exist");
				
	}
	
	
	@RequestMapping(value = "/register/getInfoByMac", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getInfoByMac(@RequestBody Map<String, Object> argMap)
	{
		String mac = (String)argMap.get("mac");
		List<Map<String, Object>> data=hardwareService.getInfoByMac(mac);
		return  Utility.gson.toJson(data.get(0));
	}
	@RequestMapping(value = "/getParkInfoByMac", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getParkInfoByMac(@RequestBody Map<String, Object> argMap)
	{
		String mac = (String)argMap.get("mac");		
		List<Map<String, Object>> data=hardwareService.getInfoByMac(mac);
		Map<String, Object> info=data.get(0);
		if (info==null) {
			return Utility.createJsonMsg(1002, "fail");
		}
		Integer parkId=(Integer) info.get("parkID");
		Park park =parkService.getParkById(parkId);
		return  Utility.gson.toJson(park);
	}
	@RequestMapping(value = "/getChannelInfoByMac", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getChannelInfoByMac(@RequestBody Map<String, Object> argMap)
	{
		String mac = (String)argMap.get("mac");		
	//	List<Map<String, Object>> data=hardwareService.getInfoByMac(mac);
		List<Map<String, Object>> infos=hardwareService.getInfoByMac(mac);	
		if (infos.isEmpty()) {
			return Utility.createJsonMsg(1002, "fail");
		}
		Map<String, Object> info=infos.get(0);
		try {
			int channelFlag=(int) info.get("channelFlag");
			Map<String, Object> retMap = new HashMap<String, Object>();
			Integer parkId=(Integer) info.get("parkID");
			Park park =parkService.getParkById(parkId);
			retMap.put("flag", channelFlag);
			retMap.put("parkId", parkId);
			retMap.put("parkName", park.getName());			
			return  Utility.gson.toJson(retMap);
		} catch (Exception e) {
			// TODO: handle exception
			return Utility.createJsonMsg(1002, "fail");
		}
	
	}
	@RequestMapping(value = "/update/hardware", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateHardware(@RequestBody Hardware hardware){
		int ret = hardwareService.updateHardware(hardware);
//		if(hardwareService.checkHardwareExist(hardware.getMac()))
//			return Utility.createJsonMsg("1002", "mac exists");
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(ret > 0 ){
			retMap.put("status", "1001");
			retMap.put("message", "update hardware success");
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "update hardware fail");
		}
		return Utility.gson.toJson(retMap);
	}
	
	@RequestMapping(value = "/delete/hardware/{Id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteHardware(@PathVariable int Id){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		int ret = hardwareService.deleteHardware(Id);
		
		if(ret > 0 ){
			retMap.put("status", "1001");
			retMap.put("message", "delete hardware success");
		}else{
			retMap.put("status", "1002");
			retMap.put("message", "delete hardware fail");
		}
		return Utility.gson.toJson(retMap);
	}
}
