package com.park.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.GongzxRecord;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.AuthorityService;
import com.park.service.ExcelExportService;
import com.park.service.GongzxRecordService;
import com.park.service.ParkService;
import com.park.service.PosdataService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
@RequestMapping("/gongzx")
public class GongzxRecordController {
	@Autowired
	private GongzxRecordService gongzxRecordService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	ParkService parkService;
	@Autowired
	private UserPagePermissionService pageService;
	@Autowired
	private PosdataService posdataService;
	@Autowired
	private ExcelExportService excelService;
	
	//财务对账页面
	@RequestMapping(value = "/reconciliation3", produces = { "application/json;charset=UTF-8" })
	public String reconciliation3(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
			modelMap.addAttribute("parks", parkList);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);

			Set<Page> pages = pageService.getUserPage(user.getId());
			for (Page page : pages) {
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "reconciliation3";
	}
	//根据时间段导出
	@RequestMapping(value = "/getExcelByParkAndDayRange")
	@ResponseBody
	public void getExcelByParkAndDayRange(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String parkId = request.getParameter("parkId");
		
		List<GongzxRecord> gongzx = gongzxRecordService.getByParkAndDayRange(Integer.parseInt(parkId), startDate, endDate);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "车牌号", "卡号", "到达时间", "停车场编号", "停车场名称", "停车类型", "应收费", "折扣", "实付", "图片地址", "其他","交易编号","离场时间" };
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "gongzxrecord.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataGongzx("收费明细", headers, gongzx, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "gongzxrecord.xlsx", response);
	}
	
	//停车记录页面
	@RequestMapping(value = "/record2", produces = { "application/json;charset=UTF-8" })
	public String record2(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> outsideparks = new ArrayList<>();
		for (Park park : parkList) {
			if (park.getType() == 3) {
				outsideparks.add(park);
			}
		}
		modelMap.addAttribute("parks", outsideparks);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);

			Set<Page> pages = pageService.getUserPage(user.getId());
			for (Page page : pages) {
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "record2";
	}
	
	//停车记录查询(获取总记录数)
	@RequestMapping(value = "/gongCount", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String gongCount() {
		int gongcount = gongzxRecordService.gongcount();
		return Utility.createJsonMsg(1001, "success", gongcount);
	}
	//根据用户名权限分页查询
	@RequestMapping(value = "/gongpage", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String gongpage(@RequestBody Map<String, Object> args, HttpSession session) {
		@SuppressWarnings("unused")
		int low = (int) args.get("low");
		@SuppressWarnings("unused")
		int count = (int) args.get("count");
		String userName = (String) session.getAttribute("username");
		List<GongzxRecord> list=null;
		if(userName.equals("admin")){
			//查询等于admin停车记录
			list=gongzxRecordService.getByParkadmin(userName);
		}else{
			//查询不等于admin停车记录
			list=gongzxRecordService.getByParkusername(userName);
		}
		//List<GongzxRecord> list=gongzxRecordService.getByParkAuthority(userName);
		return Utility.createJsonMsg(1001, "success", list);
	}
	
	//根据parkId查询记录分页
	@RequestMapping(value = "/pageByParkId", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String pageByParkId(@RequestBody Map<String, Object> args, HttpSession session) {
		int parkId = (int) args.get("parkId");
		int start = (int) args.get("start");
		int count = (int) args.get("count");
		List<GongzxRecord> gongzxRecord = gongzxRecordService.getPageByParkId(parkId, start, count);
		if (gongzxRecord.isEmpty()) {
			return Utility.createJsonMsg(1001, "success",
					gongzxRecordService.getPageByParkId(parkId, start, count));
		}
		return Utility.createJsonMsg(1001, "success", gongzxRecord);
	}
	
	//车牌号查停车记录
	@RequestMapping(value = "getByCarnumberAuthority", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCarnumberAuthority(@RequestBody Map<String, String> args, HttpSession session) {
		String carNumber = args.get("carNumber");
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username == null)
			return null;
		if (user.getRole() == AuthUserRole.ADMIN.getValue()) {
			return Utility.createJsonMsg(1001, "success", gongzxRecordService.getByCarNumber(carNumber));
		}
		parkList = parkService.filterPark(parkList, username);
		List<GongzxRecord> gongzxRecord = new ArrayList<>();
		for (Park park : parkList) {
			gongzxRecord.addAll(gongzxRecordService.getByCarNumberAndPark(carNumber, park.getId()));
		}
		return Utility.createJsonMsg(1001, "success", gongzxRecord);
	}
	
	//停车记录根据车牌号查
		@RequestMapping(value = "/getByCarNumber", method = RequestMethod.POST, produces = {
		"application/json;charset=UTF-8" })
		@ResponseBody
		public String getByCarNumber(@RequestBody Map<String, Object> args) {
			String carNumber = (String) args.get("carNumber");
			if(carNumber!=null){
				List<GongzxRecord> querygongzx = gongzxRecordService.getByCarNumber(carNumber);
			}else{
				return Utility.createJsonMsg(1002, "false");
			}
			List<GongzxRecord> querygongzx = gongzxRecordService.getByCarNumber(carNumber);
			return Utility.createJsonMsg(1001, "success", querygongzx);
		}
	
	//停车记录根据车牌号或停车场查
	@RequestMapping(value = "/getByCarNumberAndPN", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCarNumberAndPN(@RequestBody Map<String, Object> args) {
		String carNumber = (String) args.get("carNumber");
		String parkName=(String)args.get("parkName");
		if(carNumber!=null||parkName!=null){
			List<GongzxRecord> querygongzx = gongzxRecordService.getByCarNumberAndPN(carNumber,parkName);
		}else{
			return Utility.createJsonMsg(1002, "false");
		}
		List<GongzxRecord> querygongzx = gongzxRecordService.getByCarNumberAndPN(carNumber,parkName);
		return Utility.createJsonMsg(1001, "success", querygongzx);
	}
	//停车记录停车场名查
	@RequestMapping(value = "getByParkName", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByParkName(@RequestBody Map<String, String> args) {
		String parkName = args.get("parkName");
		return Utility.createJsonMsg(1001, "success", gongzxRecordService.getByParkName(parkName));
	}
	
	//查询收费总笔数、收费总金额、各渠道收费统计
	@RequestMapping(value = "/getByDateAndParkCount", produces = {"application/json;charset=utf-8" })
	@ResponseBody
	public String getByDateAndParkCount(@RequestBody Map<String, Object> args,HttpServletRequest request, HttpSession session) throws Exception{
		int parkId=Integer.parseInt((String)args.get("parkId"));
		String startDate=(String)args.get("startDate");
		String endDate=(String)args.get("endDate");
		Map<String, Object> retMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(startDate + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(endDate + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//查询收费总金额统计
		String results4=gongzxRecordService.getByDateAndParkCount(parkId,startDate, endDate);
		retMap.put("totalAmount", results4==null?new BigDecimal("0"):new BigDecimal(results4));
		return Utility.createJsonMsg(1001, "success", retMap);
	}
	
	@RequestMapping(value = "/getParkChargeByRange", method = RequestMethod.POST, produces = {
	"application/json;charset=utf-8" })
	@ResponseBody
	public String getParkChargeByRange(@RequestBody Map<String, Object> args) {
		int parkId = Integer.parseInt((String) args.get("parkId"));
		String startDay = (String) args.get("startDay");
		String endDay = (String) args.get("endDay");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(startDay + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(endDay + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar start = Calendar.getInstance();
		start.setTime(parsedStartDay);
		Long startTime = start.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.setTime(parsedEndDay);
		Long endTime = end.getTimeInMillis();
		Long oneDay = 1000 * 60 * 60 * 24l;
		Long time = startTime;
		Map<Long, Object> comparemap = new TreeMap<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		while (time <= endTime) {
			Date d = new Date(time);
			Map<String, Object> gongzxRecord = gongzxRecordService.getParkChargeByDay(parkId, df.format(d));
			comparemap.put(d.getTime(), gongzxRecord);
			time += oneDay;
		}
		return Utility.gson.toJson(comparemap);
	}
	
	//第三方停车记录根据车牌号时间段查询
	@RequestMapping(value = "/getByParkDatetime", method = RequestMethod.POST, produces = {
	"application/json;charset=utf-8" })
	@ResponseBody
	public String getByParkDatetime(@RequestBody Map<String, Object> args) throws ParseException{
		String carNumber=(String)args.get("carNumber");
		String startDatestr = (String) args.get("startDate");
		String endDatestr = (String) args.get("endDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startDatestr);
		Date endDate = sdf.parse(endDatestr);
		List<GongzxRecord> gongzxRecords = gongzxRecordService.getByParkDatetime(carNumber,startDate, endDate);
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (gongzxRecords.isEmpty()) {
			retMap.put("status", 1002);
		} else {
			retMap.put("status", 1001);
			retMap.put("message", "success");
			retMap.put("body", gongzxRecords);
		}
		return Utility.gson.toJson(retMap);
	}
}
