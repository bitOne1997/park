package com.park.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.park.model.BusinessCarportDetail;
import com.park.model.BusinessCarportDetailSimple;
import com.park.model.BusinessCarportStatus;
import com.park.model.CarportStatusDetail;
import com.park.model.Constants;
import com.park.model.Hardware;
import com.park.model.Page;
import com.park.model.Status;
import com.park.service.AuthorityService;
import com.park.service.BusinessCarportService;
import com.park.service.ExcelExportService;
import com.park.service.HardwareService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;

@Controller
public class BusinessCarportController {

	@Autowired
	private BusinessCarportService businessCarportService;

	@Autowired
	private UserPagePermissionService pageService;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private HardwareService hardwareService;
	
	@Autowired
	private ExcelExportService excelService;

	private static Log logger = LogFactory.getLog(BusinessCarportController.class);
	
	@RequestMapping(value = "/businessCarport", produces = { "application/json;charset=UTF-8" })
	public String getBusinessCarports(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
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

		return "businessCarport";
	}

	@RequestMapping(value = "/businessCarportAngular", produces = { "application/json;charset=UTF-8" })
	public String getbusinessCarportAngular(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		if (user != null) {
			modelMap.addAttribute("user", user);
			boolean isAdmin = false;
			if (user.getRole() == AuthUserRole.ADMIN.getValue())
				isAdmin = true;
			modelMap.addAttribute("isAdmin", isAdmin);
		}
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
		return "businessCarportAngular";
	}

	@RequestMapping(value = "/getBusinessCarportCount", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getBusinessCarportCount(@RequestParam(value = "parkId", required = false) Integer parkId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		int count = businessCarportService.getBusinessCarportCount(parkId);
		body.put("count", count);

		ret.put("status", "1001");
		ret.put("message", "get businessCarport detail success");
		ret.put("body", body);

		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "/getCarportStatusDetailCount", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getCarportStatusDetailCount() {

		int count = businessCarportService.getCarportStatusDetailCount();

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("count", count);
		return Utility.createJsonMsg(1001, "get count success", body);
	}

	@RequestMapping(value = "/getCarportStatusDetail", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getCarportStatusDetail(@RequestParam("carportId") int carportId) {

		List<CarportStatusDetail> details = businessCarportService.getCarportStatusDetailByCarportId(carportId);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("carportStatusDetail", details);
		return Utility.createJsonMsg(1001, "get carportStatus success", body);
	}

	@RequestMapping(value = "/getLimitCarportStatusDetail", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getLimitCarportStatusDetail(@RequestParam("low") int low, @RequestParam("count") int count) {

		List<CarportStatusDetail> details = businessCarportService.getLimitCarportStatusDetail(low, count);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("carportStatusDetail", details);
		return Utility.createJsonMsg(1001, "get carportStatus success", body);
	}

	@RequestMapping(value = "/getDayCarportStatusDetail", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getDayCarportStatusDetail(@RequestParam("carportId") int carportId,
			@RequestParam("startDay") String startDay, @RequestParam("endDay") String endDay) {
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

		List<CarportStatusDetail> details = businessCarportService.getDayCarportStatusDetail(carportId, parsedStartDay,
				parsedEndDay);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("carportStatusDetail", details);
		return Utility.createJsonMsg(1001, "get carportStatus success", body);
	}

	@RequestMapping(value = "/getCarportUsage", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getCarportUsage(@RequestParam("carportId") int carportId, @RequestParam("startDay") String startDay,
			@RequestParam("endDay") String endDay) {
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

		double usageRate = businessCarportService.getCarportUsage(carportId, parsedStartDay, parsedEndDay);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("usage", usageRate);
		body.put("unusage", 1 - usageRate);
		return Utility.createJsonMsg(1001, "get carportUsage success", body);
	}

	@RequestMapping(value = "/getParkUsage", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getParkUsage(@RequestParam("parkId") int parkId, @RequestParam("startDay") String startDay,
			@RequestParam("endDay") String endDay) {
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

		double usage = businessCarportService.getParkUsage(parkId, parsedStartDay, parsedEndDay);
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("usage", usage);
		body.put("unusage", 1 - usage);
		return Utility.createJsonMsg(1001, "get park usage success", body);
	}

	@RequestMapping(value = "/getDetailByCarportId", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getDetailByCarportId(@RequestParam("carportId") int carportId) {

		List<CarportStatusDetail> details = businessCarportService.getDetailByCarportId(carportId);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("carportStatusDetail", details);
		return Utility.createJsonMsg(1001, "get carportStatus success", body);
	}

	@RequestMapping(value = "/getBusinessCarportDetail", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String accessIndex(@RequestParam("low") int low, @RequestParam("count") int count,
			@RequestParam(value = "parkId", required = false) Integer parkId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "token,content-type");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<BusinessCarportDetail> businessCarportDetail = businessCarportService.getBusinessCarportDetail(low, count,
				parkId);
		if (businessCarportDetail != null) {
			ret.put("status", "1001");
			ret.put("message", "get businessCarport detail success");
			ret.put("body", businessCarportDetail);
		} else {
			ret.put("status", "1002");
			ret.put("message", "get businessCarport detail fail");
		}
		return Utility.gson.toJson(ret);

	}

	@RequestMapping(value = "/getBusinessCarportDetailSimple", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getBusinessCarportDetailSimple(@RequestParam("low") int low, @RequestParam("count") int count,
			@RequestParam(value = "parkId", required = false) Integer parkId, HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<BusinessCarportDetail> businessCarportDetail = businessCarportService.getBusinessCarportDetail(low, count,
				parkId);
		if (businessCarportDetail != null) {
			List<BusinessCarportDetailSimple> businessCarportDetailSimples = new ArrayList<BusinessCarportDetailSimple>();
			for (BusinessCarportDetail tmpbusiness : businessCarportDetail) {
				BusinessCarportDetailSimple tmpdata = new BusinessCarportDetailSimple();
				tmpdata.setCarportNumber(tmpbusiness.getCarportNumber());
				tmpdata.setDate(tmpbusiness.getDate());
				tmpdata.setStatus(tmpbusiness.getStatus());
				businessCarportDetailSimples.add(tmpdata);
			}
			ret.put("status", "1001");
			// ret.put("message", "get businessCarport detail success");
			ret.put("body", businessCarportDetailSimples);
		} else {
			ret.put("status", "1002");
			// ret.put("message", "get businessCarport detail fail");
		}
		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "/getBCDSimpleLimitSeconds", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getBCDSimpleLimitSecond(@RequestParam("low") int low, @RequestParam("count") int count,
			@RequestParam(value = "seconds", required = false) Integer seconds,
			@RequestParam(value = "parkId", required = false) Integer parkId, HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<BusinessCarportDetail> businessCarportDetail = businessCarportService.getBusinessCarportDetail(low, count,
				parkId);
		if (seconds == null) {
			seconds = 10;
		}
		if (businessCarportDetail != null) {
			List<BusinessCarportDetailSimple> businessCarportDetailSimples = new ArrayList<BusinessCarportDetailSimple>();
			for (BusinessCarportDetail tmpbusiness : businessCarportDetail) {
				if (new Date().getTime() - tmpbusiness.getDate().getTime() > 1000 * seconds) {
					continue;
				}
				BusinessCarportDetailSimple tmpdata = new BusinessCarportDetailSimple();
				tmpdata.setCarportNumber(tmpbusiness.getCarportNumber());
				tmpdata.setDate(tmpbusiness.getDate());
				tmpdata.setStatus(tmpbusiness.getStatus());
				businessCarportDetailSimples.add(tmpdata);
			}
			ret.put("status", "1001");
			// ret.put("message", "get businessCarport detail success");
			ret.put("body", businessCarportDetailSimples);
		} else {
			ret.put("status", "1002");
			// ret.put("message", "get businessCarport detail fail");
		}
		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "/insert/businessCarport", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String insertBusinessCarport(@RequestBody BusinessCarport businessCarport) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		int carportRet = 0;
		carportRet = businessCarportService.insertBusinessCarport(businessCarport);
		if (carportRet > 0) {
			retMap.put("status", "1001");
			retMap.put("message", "insert businessCarport success");
			return Utility.gson.toJson(retMap);
		}
		retMap.put("status", "1002");
		retMap.put("message", "insert businessCarport fail, mac has already been used");
		return Utility.gson.toJson(retMap);
	}

	@RequestMapping(value = "/addMacAndInsertBusinessCarport", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String addMacAndInsertBusinessCarport(@RequestBody Map<String, Object> args) {
		Map<String, Object> result = new HashMap<>();
		String mac = (String) args.get("mac");
		String macDescription = (String) args.get("macDesc");
		Hardware hardware = new Hardware();
		hardware.setMac(mac);
		hardware.setDescription(macDescription);
		hardware.setType(0);
		hardware.setStatus(1);
		hardware.setDate(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
		int ret = hardwareService.insertHardware(hardware);
		if (ret != 1) {
			result.put("status", 1002);
			result.put("message", "硬件插入失败");
			return Utility.gson.toJson(result);
		}
		int macId = hardware.getId();
		int parkId = (int) args.get("parkId");
		int carportNumber = (int) args.get("carportNumber");
		BusinessCarport businessCarport = new BusinessCarport();
		businessCarport.setCarportNumber(carportNumber);
		businessCarport.setDate(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
		businessCarport.setMacId(macId);
		businessCarport.setStatus(0);
		businessCarport.setParkId(parkId);
		if (businessCarportService.insertBusinessCarport(businessCarport) == 1) {
			businessCarportService.updateBusinessCarport(businessCarport);
			result.put("status", 1001);
			result.put("message", "成功插入硬件并绑定成功");
			return Utility.gson.toJson(result);
		}
		result.put("status", 1002);
		result.put("message", "绑定失败");
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/update/businessCarport", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String updateBusinessCarport(@RequestBody BusinessCarport businessCarport) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		int ret = businessCarportService.updateBusinessCarport(businessCarport);
		if (ret > 0) {
			retMap.put("status", "1001");
			retMap.put("message", "update businessCarport success");
		} else {
			retMap.put("status", "1002");
			retMap.put("message", "update businessCarport fail, change mac status or update carport fail");
		}

		return Utility.gson.toJson(retMap);
	}

	@RequestMapping(value = "/update/status/businessCarport", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String updateBusinessCarportStatus(@RequestBody Map<String, Object> args) throws InterruptedException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String mac = (String) args.get("mac");
		int status = Integer.parseInt((String) args.get("status"));
		int ret = businessCarportService.updateBusinessCarportStatus(mac, status, false);
		logger.info("updateBusinessCarportStatus resutl: " + ret);
		if (ret > 0) {
			retMap.put("status", "1001");
			retMap.put("message", "update businessCarport success");
		} else {
			retMap.put("status", "1002");
			retMap.put("message", "update businessCarport fail, mac may not be used");
		}

		return Utility.gson.toJson(retMap) + "eof\n";
	}

	@RequestMapping(value = "/update/status/businessCarports", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String updateBusinessCarportsStatus(@RequestBody Map<String, Object> args) throws InterruptedException {
		List<String> updatedMac = new ArrayList<String>();
		List<Object> carportsStatus = (List<Object>) args.get("carportStatus");
		int i = 1;
		for (Object item : carportsStatus) {
			Map<String, Object> arg = (Map<String, Object>) item;
			String mac = (String) arg.get("mac");
			int status = Integer.parseInt((String) arg.get("status").toString());
			int ret;
			if (i == 1) {
				ret = businessCarportService.updateBusinessCarportStatus(mac, status, true);
			} else {
				ret = businessCarportService.updateBusinessCarportStatus(mac, status, false);
			}
			i++;
			if (ret > 0)
				updatedMac.add(mac);

		}

		return Utility.createJsonMsg(1001, "updated mac", updatedMac) + "eof\n";
	}

	@RequestMapping(value = "/delete/businessCarport/{Id}", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteBusinessCarport(@PathVariable int Id) {
		BusinessCarport carport = businessCarportService.getBusinessCarportById(Id);
		hardwareService.changeHardwareStatus(carport.getMacId(), Status.UNUSED.getValue());
		int ret = businessCarportService.deleteBusinessCarport(Id);
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (ret > 0) {
			retMap.put("status", "1001");
			retMap.put("message", "delete businessCarport success");
		} else {
			retMap.put("status", "1002");
			retMap.put("message", "delete businessCarport fail");
		}
		return Utility.gson.toJson(retMap);
	}

	@RequestMapping(value = "/insertBusinessCarportNum", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })
	@ResponseBody
	public String insertBusinessCarportNum(@RequestBody Map<String, Object> args) {
		Integer carportstart = (Integer) args.get("carportStart");
		Integer carporttotal = (Integer) args.get("carportTotal");
		Integer parkid = (Integer) args.get("parkId");
		int insertnum = businessCarportService.insertBusinessCarportNum(parkid, carportstart, carporttotal);
		Map<String, Object> ret = new HashMap<>();
		ret.put("num", insertnum);
		return Utility.gson.toJson(ret);

	}

	@RequestMapping(value = "/businessCarportStatus")
	public String businessCarportStatus(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
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
		return "businessCarportStatus";
	}

	@RequestMapping(value = "/getBusinessCarportStatusByParkId/{parkId}", method = RequestMethod.GET, produces = {
			"application/json;charset=utf-8" })
	@ResponseBody
	public String getBusinessCarportStatusByParkId(@PathVariable("parkId") int parkId)
			throws ParseException, Exception {
		Map<String, Object> result = new HashMap<>();
		List<BusinessCarportStatus> businessCarportStatus = businessCarportService.getBusinessStatusByParkId(parkId);

		result.put("status", "1001");
		result.put("body", businessCarportStatus);
		return Utility.gson.toJson(result);
	}
}
