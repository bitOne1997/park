package com.park.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.park.model.BusinessCarport;
import com.park.model.Channel;
import com.park.model.ChannelDetail;
import com.park.model.Hardware;
import com.park.model.Page;
import com.park.model.Status;
import com.park.service.AuthorityService;
import com.park.service.ChannelService;
import com.park.service.ExcelExportService;
import com.park.service.HardwareService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class ChannelController {
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private HardwareService hardwareService;

	@Autowired
	private AuthorityService authService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private ExcelExportService excelService;
	
	@RequestMapping(value = "/channel", produces = {"application/json;charset=UTF-8"})
	public String getChannels(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}		
		return "channel";
	}
	/*新加页面*/
	@RequestMapping(value = "/channel2", produces = {"application/json;charset=UTF-8"})
	public String getChannels2(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}		
		return "channel2";
	}
	
	@RequestMapping(value = "barrier", produces = {"application/json;charset=UTF-8"})
	public String barrier(ModelMap modelMap, HttpServletRequest request, HttpSession session){		
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if(user != null){
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if(user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin=true;
			modelMap.addAttribute("isAdmin", isAdmin);
			
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}		
		return "barrier";
	}
	@RequestMapping(value = "/getchannelCount", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getchannelCount(){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = channelService.getchannelCount();
		body.put("count", count);
	
		ret.put("status", "1001");
		ret.put("message", "get channel detail success");
		ret.put("body", Utility.gson.toJson(body));
		
		return Utility.gson.toJson(ret);					
	}
	@RequestMapping(value = "/getchannelCountByMacType/{macType}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getchannelCountByChannelFlag(@PathVariable int macType){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = channelService.getChannelCountByChannelFlag(macType);
		body.put("count", count);
	
		ret.put("status", "1001");
		ret.put("message", "get channel detail success");
		ret.put("body", Utility.gson.toJson(body));
		
		return Utility.gson.toJson(ret);					
	}
	
	@RequestMapping(value = "/getchannelDetail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getchannelDetail(@RequestParam("low")int low, @RequestParam("count")int count){	
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<ChannelDetail> channelDetail = channelService.getChannelDetail(low, count);
		if(channelDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get channel detail success");
			ret.put("body", Utility.gson.toJson(channelDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get channel detail fail");
		}
		return Utility.gson.toJson(ret);		
	}
	@RequestMapping(value = "/getchannelDetailByMacType", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getchannelDetailByChannelFlag(@RequestParam("low")int low, @RequestParam("count")int count,@RequestParam("macType")int macType){	
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<ChannelDetail> channelDetail = channelService.getChannelDetailByMacType(low, count, macType);
		if(channelDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get channel detail success");
			ret.put("body", Utility.gson.toJson(channelDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get channel detail fail");
		}
		return Utility.gson.toJson(ret);		
	}
	@RequestMapping(value="excelChannelDetailExport",method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void excelChannelDetailExport(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException{
		List<ChannelDetail> channelDetails=channelService.getChannelDetail(0, 1000);
		String[] headers = {"id","停车场","硬件地址","出入口","描述","日期"};
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "channelDetail.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataChannel("channel数据", headers, channelDetails, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "channelDetail.xlsx", response);
	}
	
	@RequestMapping(value = "/getExcelByParkDay")
	@ResponseBody
	public void getExcelByParkDay(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String date = request.getParameter("date");
		String parkId = request.getParameter("parkId");
		List<ChannelDetail> channel = channelService.getExcelByParkDay(Integer.parseInt(parkId), date);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "id","停车场名", "通道编号", "MacId", "标志", "状态", "描述", "日期"};
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		String filename = sFormat.format(new Date()) + "barrierdata.xlsx";
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "barrierdata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataChannelData("道闸详情", headers, channel, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "barrierdata.xlsx", response);
	}
	
	@RequestMapping(value = "/getExcelByAllParkDay")
	@ResponseBody
	public void getExcelByAllParkDay(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		List<ChannelDetail> channel = channelService.getExcelByAllParkDay();
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "id","停车场名", "通道编号", "MacId", "标志", "状态", "描述", "日期"};
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "barrierdata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExcelAllChannelData("道闸详情", headers, channel, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "barrierdata.xlsx", response);
	}

	
	@RequestMapping(value = "/getChannelDetailByKeywords", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getChannelDetailByKeywords(@RequestBody Map<String, Object> args){	
		Map<String, Object> ret = new HashMap<String, Object>();
		String keywords=(String) args.get("keywords");
		keywords=keywords.trim();
		List<ChannelDetail> channelDetail = channelService.getChannelDetailByKeywords(keywords);
		if(channelDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get channel detail success");
			ret.put("body", Utility.gson.toJson(channelDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get channel detail fail");
		}
		return Utility.gson.toJson(ret);		
	}
	
	@RequestMapping(value = "/getParkchannelDetail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String accessIndexpark(@RequestParam("low")int low, @RequestParam("count")int count,@RequestParam("parkId") Integer parkId){	
		Map<String, Object> ret = new HashMap<String, Object>();
		List<ChannelDetail> channelDetail = channelService.getParkChannelDetail(low, count,parkId);
		if(channelDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get channel detail success");
			ret.put("body", Utility.gson.toJson(channelDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get channel detail fail");
		}
		return Utility.gson.toJson(ret);
		
	}
	@RequestMapping(value="/getChannelDetailByDate",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getChannelDetailByDate(@RequestParam("day")int day){
		Map<String, Object> ret = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String endday=df.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, day);
		String startday=df.format(cal.getTime());
		List<ChannelDetail> channelDetail = channelService.getChannelDetailByDate(startday, endday);
		if(channelDetail != null){
			ret.put("status", "1001");
			ret.put("message", "get channel detail success");
			ret.put("body", Utility.gson.toJson(channelDetail));
		}else{
			ret.put("status", "1002");
			ret.put("message", "get channel detail fail");
		}
		return Utility.gson.toJson(ret);
	}
	@RequestMapping(value = "/insert/channel", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String insertChannel1(@RequestBody Channel channel){
		Integer macId = channel.getMacId();
		Hardware hardware = hardwareService.getHardwareById(macId);
		
		if(hardware!= null && hardware.getStatus() == Status.USED.getValue())
			return Utility.createJsonMsg("1003", "hard is being used");
		if(channelService.insertChannel(channel)){
			if(hardware != null)
				hardwareService.bindHardware(macId);
			return Utility.createJsonMsg("1001", "insert success");
		}else{
			return Utility.createJsonMsg("1002","insert failed");
		}
	}
	
	@RequestMapping(value = "/update/channel", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateChannel(@RequestBody Channel channel){
		
		Channel orignChannel = channelService.getChannelsById(channel.getId());
		Integer oldMacId = orignChannel.getMacId();
		Integer newMacId = channel.getMacId();
		if(newMacId < 0)
			channel.setMac(null);
		
		if(channelService.updateChannel(channel)){
			if(newMacId != oldMacId){
				Hardware hardware = hardwareService.getHardwareById(newMacId);
				if(hardware != null && hardware.getStatus() == Status.USED.getValue())
					return Utility.createJsonMsg("1003", "hardware is being used");
				hardwareService.changeHardwareStatus(oldMacId, Status.UNUSED.getValue());
				if(hardware != null)
					hardwareService.changeHardwareStatus(newMacId, Status.USED.getValue());
			}
			return Utility.createJsonMsg("1001", "update success");
		}
		return Utility.createJsonMsg("1002", "update failed");
	}
	
	@RequestMapping(value = "/delete/channel/{Id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteChannel(@PathVariable int Id){
		Channel channel = channelService.getChannelsById(Id);
		if(channel.getMacId()!= null && channel.getMacId() > 0)
			hardwareService.changeHardwareStatus(channel.getMacId(), Status.UNUSED.getValue());
		return channelService.deleteChannel(Id);
	}

}
