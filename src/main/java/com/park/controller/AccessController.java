package com.park.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.park.model.Access;
import com.park.model.AccessDetail;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Page;
import com.park.service.AccessService;
import com.park.service.AuthorityService;
import com.park.service.ChannelService;
import com.park.service.ExcelExportService;
import com.park.service.HardwareService;
import com.park.service.ParkService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class AccessController {

	@Autowired
	private AccessService accessService;

	@Autowired
	private HardwareService hardwareService;

	@Autowired
	private ParkService parkService;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private UserPagePermissionService pageService;
	
	@Autowired
	private ExcelExportService excelService;
	// private static Log logger = LogFactory.getLog(UserController.class);

	@RequestMapping(value = "/access", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public String accessIndex(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);
			Set<Page> pages = pageService.getUserPage(user.getId()); 
			for(Page page : pages){
				modelMap.addAttribute(page.getPageKey(), true);
			}
		}
		return "access";
	}

	@RequestMapping(value = "/getAccessDetail", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String accessIndex(@RequestParam("low") int low, @RequestParam("count") int count,
			@RequestParam(value = "parkId", required = false) Integer parkId) {
		Map<String, Object> ret = new HashMap<String, Object>();

		List<AccessDetail> accessDetail = accessService.getAccessDetail(low, count, parkId);
		if (accessDetail != null) {
			ret.put("status", "1001");
			ret.put("message", "get access detail success");
			ret.put("body", Utility.gson.toJson(accessDetail));
		} else {
			ret.put("status", "1002");
			ret.put("message", "get access detail fail");
		}
		return Utility.gson.toJson(ret);

	}

	@RequestMapping(value = "/getAccessCount", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getAccessCount(@RequestParam(value = "parkId", required = false) Integer parkId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = accessService.getAccessCount(parkId);
		body.put("count", count);

		ret.put("status", "1001");
		ret.put("message", "get access detail success");
		ret.put("body", Utility.gson.toJson(body));

		return Utility.gson.toJson(ret);

	}

	@RequestMapping(value = "/getHourCountByPark", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getHourCountByPark(@RequestBody Map<String, Object> args) throws ParseException {

		int parkId = -1;
		if (args.containsKey("parkId")) {
			parkId = (int) args.get("parkId");
		} else {
			String parkName = (String) args.get("parkName");
			parkId = parkService.nameToId(parkName);
		}

		String date = (String) args.get("date");
		Map<String, Map<Integer, Integer>> body = accessService.getHourCountByPark(parkId, date);
		return Utility.createJsonMsg(1001, "get count success", body);

	}

	@RequestMapping(value = "/getHourCountByChannel", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getHourCountByChannel(@RequestBody Map<String, Object> args) {
		int parkId = -1;
		if (args.containsKey("parkId")) {
			parkId = (int) args.get("parkId");
		} else {
			String parkName = (String) args.get("parkName");
			parkId = parkService.nameToId(parkName);
		}
		String date = (String) args.get("date");
		Map<String, Map<Integer, Integer>> body = accessService.getHourCountByChannel(parkId, date);
		return Utility.createJsonMsg(1001, "get count success", body);

	}

	@RequestMapping(value = "/getDayCountByPark", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getMonthCountByPark(@RequestBody Map<String, Object> args) {
		int parkId = -1;
		if (args.containsKey("parkId")) {
			parkId = (int) args.get("parkId");
		} else {
			String parkName = (String) args.get("parkName");
			parkId = parkService.nameToId(parkName);
		}
		String date = (String) args.get("date");
		Map<String, Map<Integer, Integer>> body = accessService.getDayCountByPark(parkId, date);
		return Utility.createJsonMsg(1001, "get count success", body);

	}

	/*
	 * @RequestMapping(value = "/getMonthCountByChannel", method =
	 * RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	 * 
	 * @ResponseBody public String getMonthCountByChannel(@RequestBody
	 * Map<String, Object> args){ int parkId = -1;
	 * if(args.containsKey("parkId")){ parkId = (int)args.get("parkId"); }else{
	 * String parkName = (String)args.get("parkName"); parkId =
	 * parkService.nameToId(parkName); } int year = (int)args.get("year");
	 * Map<String, Map<Integer, Integer>> body =
	 * accessService.getMonthCountByChannel(parkId, year); return
	 * Utility.createJsonMsg(1001, "get count success", body);
	 * 
	 * }
	 */
	@RequestMapping(value="/exportExcel/{parkId}/{monthNum}",method=RequestMethod.GET)
	@ResponseBody
	public void exportExcel(@PathVariable("parkId")int parkId,@PathVariable("monthNum")int monthNum,HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException{
		List<AccessDetail> accessData=accessService.getAccessForExcel(parkId, monthNum);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers={"id","停车场名","通道号","日期"};
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR+ "accessdata"+parkId+".xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		 
		excelService.produceExceldataAccessDetail("数据表",headers, accessData, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR+ "accessdata"+parkId+".xlsx", response);
	}
	
	@RequestMapping(value = "/getChannelHourCount", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getChannelHourCount(@RequestBody Map<String, Object> args) {
		int macId = -1;
		String mac = "";
		if (args.containsKey("macId")) {
			macId = (int) args.get("macId");
		} else {
			mac = (String) args.get("mac");
			macId = hardwareService.macToId(mac);
		}
		String date = (String) args.get("date");
		Map<Integer, Integer> body = accessService.getChannelHourCount(mac, macId, date);
		return Utility.createJsonMsg(1001, "get count success", body);

	}

	/*
	 * @RequestMapping(value = "/getChannelMonthCount", method =
	 * RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	 * 
	 * @ResponseBody public String getChannelMonthCount(@RequestBody Map<String,
	 * Object> args){ int macId = -1; if(args.containsKey("macId")){ macId =
	 * (int)args.get("macId"); }else{ String mac = (String)args.get("mac");
	 * macId = hardwareService.macToId(mac); } int year = (int)args.get("year");
	 * Map<Integer, Integer> body = accessService.getChannelMonthCount(macId,
	 * year); return Utility.createJsonMsg(1001, "get count success", body);
	 * 
	 * }
	 */

	@RequestMapping(value = "/insert/access", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String insertPark(@RequestBody Map<String, Object> argMap) {

		String mac = (String) argMap.get("mac");
		Date date = new Date();
		int channelId = channelService.getChannelIdByMac(mac);
		if (channelId < 0) {
			return Utility.createJsonMsg("1003", "the hardware is not bound") + "\neof\n";
		}
		Access access = new Access();
		access.setDate(date);
		access.setChannelId(channelId);
		channelService.updateChannelDateByMac(mac);
		return accessService.insertAccess(access) + "\neof\n";
	}

	@RequestMapping(value = "/getAllAccessCount", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getAllAccessCount() {
		int count = accessService.getAllAccessCount(200, 12);
		HashMap<String, Integer> num = new HashMap<String, Integer>();
		num.put("num", count);
		return Utility.gson.toJson(num);
	}

	@RequestMapping(value = "/getAccessCountByDate", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ResponseBody
	public String getAccessCountByDate(@RequestBody Map<String, Object> args) {
		String accessDate=(String) args.get("date");
		int count = accessService.getAccessCountByDate(200, 12,accessDate);
		HashMap<String, Integer> num = new HashMap<String, Integer>();
		num.put("num", count);
		return Utility.gson.toJson(num);
	}
	/*
	 * @RequestMapping(value = "/update/access", method = RequestMethod.POST,
	 * produces = {"application/json;charset=UTF-8"})
	 * 
	 * @ResponseBody public String updatePark(@RequestBody Access access){
	 * return accessService.updateAccess(access); }
	 * 
	 * @RequestMapping(value = "/delete/access/{id}", method =
	 * RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	 * 
	 * @ResponseBody public String deletePark(@PathVariable int id){ return
	 * accessService.deleteAccess(id); }
	 */
}
