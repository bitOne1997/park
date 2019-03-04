package com.park.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.jpush.Jpush;
import com.park.model.AuthUser;
import com.park.model.AuthUserRole;
import com.park.model.Constants;
import com.park.model.FeeCriterion;
import com.park.model.Feeoperator;
import com.park.model.Monthuser;
import com.park.model.Page;
import com.park.model.Park;
import com.park.model.Parktoalipark;
import com.park.model.Pos;
import com.park.model.PosChargeData;
import com.park.model.PosChargeDataSimple;
import com.park.model.Zonecenter;
import com.park.service.ActiveMqService;
import com.park.service.AliParkFeeService;
import com.park.service.AuthorityService;
import com.park.service.ExcelExportService;
import com.park.service.FeeCriterionService;
import com.park.service.FeeOperatorService;
import com.park.service.JedisClient;
import com.park.service.JsonUtils;
import com.park.service.MonthUserService;
import com.park.service.ParkService;
import com.park.service.ParkToAliparkService;
import com.park.service.PosChargeDataService;
import com.park.service.PosService;
import com.park.service.PosdataService;
import com.park.service.UserPagePermissionService;
import com.park.service.Utility;
import com.util.PayTypeUtil;

@Controller
@RequestMapping("/pos/charge")
public class PosChargeDataController {

	@Autowired
	ParkService parkService;
	@Autowired
	private AuthorityService authService;

	@Autowired
	PosChargeDataService chargeSerivce;

	@Autowired
	private UserPagePermissionService pageService;

	@Autowired
	private PosService posService;
	@Autowired
	private PosdataService posdataService;
	@Autowired
	private ExcelExportService excelService;
	@Autowired
	MonthUserService monthUserService;
	@Autowired
	private FeeOperatorService feeOperatorService;

	@Autowired
	private FeeCriterionService feeCriterionService;
	@Autowired
	AliParkFeeService aliparkFeeService;
	@Autowired
	ParkToAliparkService parkToAliparkService;

	@Resource(name = "jedisClient")
	private JedisClient jedisClient;

	private static Log logger = LogFactory.getLog(PosChargeDataController.class);

	
	PayTypeUtil payTypeUtilByZI = null;
	/*PayTypeUtil payTypeUtilByZHU = null;*/
	
	@Autowired
	private PosChargeDataService posChargeDataService;

	//主平台今日数据统计
	@RequestMapping(value = "/getParkByCountMoney", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object getParkByCountMoney(@RequestBody Map<String, Object> args,HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> mapmap = null;
		String userName = (String)session.getAttribute("username");
		Object usernameObj = args.get("username");
		String username = String.valueOf(userName);
		AuthUser user = authService.getUserByUsername(username);
		if (username == null)
			return null;
		if (user.getRole() == AuthUserRole.ADMIN.getValue()) {
			//判断登录的是管理员用户
			Object startDateObj = args.get("startDate");
			Object endDateObj = args.get("endDate");
			/*String username = String.valueOf(userName);*/
			String startDate = String.valueOf(startDateObj);
			String endDate = String.valueOf(endDateObj);
			/*map.put("username", username);*/
			map.put("startDate", startDate);
			map.put("endDate", endDate);
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
			PayTypeUtil payTypeUtil=new PayTypeUtil();
			maps.put("startDate", startDate);
			maps.put("endDate", endDate);
			mapmap = getByDateAndParkCountPay(maps);
			System.out.println(String.format("打印参数信息====参数名称：%s", mapmap));
			return Utility.createJsonMsg(1001, "success", mapmap);
		}else{
			//判断登录的用户是子账户
			Object startDateObj = args.get("startDate");
			Object endDateObj = args.get("endDate");
			String startDate = String.valueOf(startDateObj);
			String endDate = String.valueOf(endDateObj);
			map.put("username", username);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
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
			List<Park> listparkId = chargeSerivce.getParkByMoney(map);
			payTypeUtilByZI=new PayTypeUtil();
			for (int i = 0; i < listparkId.size(); i++) {
				int userId = listparkId.get(i).getId();
				int parkId = listparkId.get(i).getParkId();
				maps.put("parkId", parkId);
				maps.put("startDate", startDate);
				maps.put("endDate", endDate);
				mapmap = getByDateAndParkCount(maps);
				System.out.println(String.format("打印参数信息====参数名称：%s", mapmap));
			}
			return Utility.createJsonMsg(1001, "success", mapmap);
		}

	}

	//主平台今日数据统计调用
	// 查询收费总笔数、收费总金额、各渠道收费统计
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getByDateAndParkCountPay", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> getByDateAndParkCountPay(@RequestBody Map<String, Object> args) throws Exception {
		String startDate = (String) args.get("startDate");
		String endDate = (String) args.get("endDate");
		PayTypeUtil payTypeUtil=new PayTypeUtil();
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

		// 查询收费总笔数、收费总金额、各渠道收费统计
		String resultszbs = chargeSerivce.getByDateAndParkCountPayzbs(startDate, endDate);
		String resultszfbbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, PayTypeUtil.payTypezfb);
		String resultswxbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypewx);
		String resultsxjbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypexj);
		String resultsqtbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypeqt);
		String resultsylbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypeyl);
		String resultsghbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypegh);
		String resultsxj2bs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypexj2);
		String resultsappbs = chargeSerivce.getByDateAndParkCountPayTypebs(startDate, endDate, payTypeUtil.payTypeapp);

		String resultszje = chargeSerivce.getByDateAndParkCountPayzje(startDate, endDate);
		String resultszfbje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypezfb);
		String resultswxje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypewx);
		String resultsxjje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypexj);
		String resultsqtje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypeqt);
		String resultsylje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypeyl);
		String resultsghje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypegh);
		String resultsxj2je = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypexj2);
		String resultsappje = chargeSerivce.getByDateAndParkCountPayTypeje(startDate, endDate, PayTypeUtil.payTypeapp);
		String resultspaidAmount = chargeSerivce.getByParkpaidMoneyjine(startDate, endDate);
		
		//渠道笔数获取
		List<Integer> ListPayTypeInt = payTypeUtil.PayTypebsInt(resultszbs, resultszfbbs, resultswxbs, resultsxjbs, 
				resultsqtbs, resultsylbs, resultsghbs, resultsxj2bs, resultsappbs);
		payTypeUtil.ZongBishu += ListPayTypeInt.get(0);
		payTypeUtil.zfbZongBishu += ListPayTypeInt.get(1);
		payTypeUtil.weixinZongBishu += ListPayTypeInt.get(2);
		payTypeUtil.xjZongBishu += ListPayTypeInt.get(3);
		payTypeUtil.qtZongBishu += ListPayTypeInt.get(4);
		payTypeUtil.ylZongBishu += ListPayTypeInt.get(5);
		payTypeUtil.ghZongBishu += ListPayTypeInt.get(6);
		payTypeUtil.xj2ZongBishu += ListPayTypeInt.get(7);
		payTypeUtil.appZongBishu += ListPayTypeInt.get(8);

		//金额获取
		List<Float> ListPayTypeFloat=payTypeUtil.PayTypejeFloat(resultszje, resultszfbje, resultswxje, resultsxjje,
				resultsqtje, resultsylje, resultsghje, resultsxj2je, resultsappje, resultspaidAmount);
		payTypeUtil.Zongjine += ListPayTypeFloat.get(0);
		payTypeUtil.zfbZongjine += ListPayTypeFloat.get(1);
		payTypeUtil.weixinZongjine += ListPayTypeFloat.get(2);
		payTypeUtil.xjZongjine += ListPayTypeFloat.get(3);
		payTypeUtil.qtZongjine += ListPayTypeFloat.get(4);
		payTypeUtil.ylZongjine += ListPayTypeFloat.get(5);
		payTypeUtil.ghZongjine += ListPayTypeFloat.get(6);
		payTypeUtil.xj2Zongjine += ListPayTypeFloat.get(7);
		payTypeUtil.appZongjine += ListPayTypeFloat.get(8);
		payTypeUtil.paidMoneyjine += ListPayTypeFloat.get(9);

		Map<String, Object> mapCount = new HashMap<String, Object>();
		mapCount.put("totalAmount", payTypeUtil.Zongjine);
		mapCount.put("alipayAmount", payTypeUtil.zfbZongjine);
		mapCount.put("wechartAmount", payTypeUtil.weixinZongjine);
		mapCount.put("cashAmount", payTypeUtil.xjZongjine);
		mapCount.put("otherAmount", payTypeUtil.qtZongjine);
		mapCount.put("unionPayAmount", payTypeUtil.ylZongjine);
		mapCount.put("cbcAmount", payTypeUtil.ghZongjine);
		mapCount.put("cashAmount2", payTypeUtil.xj2Zongjine);
		mapCount.put("appAmount", payTypeUtil.appZongjine);
		mapCount.put("paidAmount", payTypeUtil.paidMoneyjine);

		mapCount.put("totalCount", payTypeUtil.ZongBishu);
		mapCount.put("alipayCount", payTypeUtil.zfbZongBishu);
		mapCount.put("wechartCount", payTypeUtil.weixinZongBishu);
		mapCount.put("cashCount", payTypeUtil.xjZongBishu);
		mapCount.put("otherCount", payTypeUtil.qtZongBishu);
		mapCount.put("unionPayCount", payTypeUtil.ylZongBishu);
		mapCount.put("cbcCount", payTypeUtil.ghZongBishu);
		mapCount.put("cashCount2", payTypeUtil.xj2ZongBishu);
		mapCount.put("appCount", payTypeUtil.appZongBishu);
		return mapCount;
	}
	
	
	//子平台首页统计
	@RequestMapping(value = "/getParkByMoney", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Object getParkByMoney(@RequestBody Map<String, Object> args) throws Exception {
		@SuppressWarnings("unused")
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> maps = new HashMap<String, Object>();
		Object usernameObj = args.get("username");
		Object startDateObj = args.get("startDate");
		Object endDateObj = args.get("endDate");
		String username = String.valueOf(usernameObj);
		String startDate = String.valueOf(startDateObj);
		String endDate = String.valueOf(endDateObj);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
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
		List<Park> listparkId = chargeSerivce.getParkByMoney(map);
		Map<String, Object> mapmap = null;
		for (int i = 0; i < listparkId.size(); i++) {
			int userId = listparkId.get(i).getId();
			int parkId = listparkId.get(i).getParkId();
			maps.put("parkId", parkId);
			maps.put("startDate", startDate);
			maps.put("endDate", endDate);
			mapmap = getByDateAndParkCount(maps);
			System.out.println(String.format("打印参数信息====参数名称：%s", mapmap));
		}
		return Utility.createJsonMsg(1001, "success", mapmap);
	}

	//子平台首页统计调用
	@SuppressWarnings("static-access")
	// 查询收费总笔数、收费总金额、各渠道收费统计
	@RequestMapping(value = "/getByDateAndParkCount", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Map<String, Object> getByDateAndParkCount(@RequestBody Map<String, Object> args) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		int parkId = 0;
		Object parkIdObj = args.get("parkId");
		String parkIdStr = parkIdObj.toString();
		int parkids = Integer.parseInt(parkIdStr);
		parkId = Integer.parseInt(args.get("parkId").toString());
		String startDate = (String) args.get("startDate");
		String endDate = (String) args.get("endDate");
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
		// 查询收费总笔数、收费总金额、各渠道收费统计
		String resultszbs = chargeSerivce.getByDateAndParkCountzbs(parkId, startDate, endDate);
		String resultszfbbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypezfb);
		String resultswxbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypewx);
		String resultsxjbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypexj);
		String resultsqtbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypeqt);
		String resultsylbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypeyl);
		String resultsghbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypegh);
		String resultsxj2bs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypexj2);
		String resultsappbs = chargeSerivce.getByDateAndParkCountbs(parkId, startDate, endDate, PayTypeUtil.payTypeapp);

		String resultszje = chargeSerivce.getByDateAndParkCountzje(parkId, startDate, endDate);
		String resultszfbje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypezfb);
		String resultswxje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypewx);
		String resultsxjje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypexj);
		String resultsqtje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypeqt);
		String resultsylje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypeyl);
		String resultsghje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypegh);
		String resultsxj2je = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypexj2);
		String resultsappje = chargeSerivce.getByDateAndParkCountje(parkId, startDate, endDate, PayTypeUtil.payTypeapp);
		String resultspaidAmount = chargeSerivce.getBypaidMoneyjine(parkId,startDate, endDate);

		//渠道笔数获取
		List<Integer> ListPayTypeInt = PayTypeUtil.PayTypebsInt(resultszbs, resultszfbbs, resultswxbs, resultsxjbs, 
						resultsqtbs, resultsylbs, resultsghbs, resultsxj2bs, resultsappbs);
		payTypeUtilByZI.ZongBishu+= ListPayTypeInt.get(0);
		payTypeUtilByZI.zfbZongBishu += ListPayTypeInt.get(1);
		payTypeUtilByZI.weixinZongBishu += ListPayTypeInt.get(2);
		payTypeUtilByZI.xjZongBishu += ListPayTypeInt.get(3);
		payTypeUtilByZI.qtZongBishu += ListPayTypeInt.get(4);
		payTypeUtilByZI.ylZongBishu += ListPayTypeInt.get(5);
		payTypeUtilByZI.ghZongBishu += ListPayTypeInt.get(6);
		payTypeUtilByZI.xj2ZongBishu += ListPayTypeInt.get(7);
		payTypeUtilByZI.appZongBishu += ListPayTypeInt.get(8);

		//金额获取
		List<Float> ListPayTypeFloat=PayTypeUtil.PayTypejeFloat(resultszje, resultszfbje, resultswxje, resultsxjje,
						resultsqtje, resultsylje, resultsghje, resultsxj2je, resultsappje, resultspaidAmount);
		payTypeUtilByZI.Zongjine += ListPayTypeFloat.get(0);
		payTypeUtilByZI.zfbZongjine += ListPayTypeFloat.get(1);
		payTypeUtilByZI.weixinZongjine += ListPayTypeFloat.get(2);
		payTypeUtilByZI.xjZongjine += ListPayTypeFloat.get(3);
		payTypeUtilByZI.qtZongjine += ListPayTypeFloat.get(4);
		payTypeUtilByZI.ylZongjine += ListPayTypeFloat.get(5);
		payTypeUtilByZI.ghZongjine += ListPayTypeFloat.get(6);
		payTypeUtilByZI.xj2Zongjine += ListPayTypeFloat.get(7);
		payTypeUtilByZI.appZongjine += ListPayTypeFloat.get(8);
		payTypeUtilByZI.paidMoneyjine += ListPayTypeFloat.get(9);

		Map<String, Object> mapCount = new HashMap<String, Object>();
		mapCount.put("totalAmount", payTypeUtilByZI.Zongjine);
		mapCount.put("alipayAmount", payTypeUtilByZI.zfbZongjine);
		mapCount.put("wechartAmount", payTypeUtilByZI.weixinZongjine);
		mapCount.put("cashAmount", payTypeUtilByZI.xjZongjine);
		mapCount.put("otherAmount", payTypeUtilByZI.qtZongjine);
		mapCount.put("unionPayAmount", payTypeUtilByZI.ylZongjine);
		mapCount.put("cbcAmount", payTypeUtilByZI.ghZongjine);
		mapCount.put("cashAmount2", payTypeUtilByZI.xj2Zongjine);
		mapCount.put("appAmount", payTypeUtilByZI.appZongjine);
		mapCount.put("paidAmount", payTypeUtilByZI.paidMoneyjine);

		mapCount.put("totalCount", payTypeUtilByZI.ZongBishu);
		mapCount.put("alipayCount", payTypeUtilByZI.zfbZongBishu);
		mapCount.put("wechartCount", payTypeUtilByZI.weixinZongBishu);
		mapCount.put("cashCount", payTypeUtilByZI.xjZongBishu);
		mapCount.put("otherCount", payTypeUtilByZI.qtZongBishu);
		mapCount.put("unionPayCount", payTypeUtilByZI.ylZongBishu);
		mapCount.put("cbcCount", payTypeUtilByZI.ghZongBishu);
		mapCount.put("cashCount2", payTypeUtilByZI.xj2ZongBishu);
		mapCount.put("appCount", payTypeUtilByZI.appZongBishu);
		return mapCount;
	}
	
	@RequestMapping(value = "/detail", produces = { "application/json;charset=UTF-8" })
	public String feeDetailIndex(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
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
		return "feeDetail";
	}

	@RequestMapping(value = "/record", produces = { "application/json;charset=UTF-8" })
	public String record(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		List<Park> outsideparks = new ArrayList<>();
		for (Park park : parkList) {
			if (park.getType()==3) {
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
		return "record";
	}

	@RequestMapping(value = "/taopaiche", produces = { "application/json;charset=UTF-8" })
	public String taopaiche(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
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
		return "taopaiche";
	}

	@RequestMapping(value = "/flowbill", produces = { "application/json;charset=UTF-8" })
	public String flowbill(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
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
		return "flowbill";
	}

	@RequestMapping(value = "/reconciliation", produces = { "application/json;charset=UTF-8" })
	public String reconciliation(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		// List<Park> outsideparks = new ArrayList<>();
		// List<Park> outsideparks = new ArrayList<>();
		// for (Park park : parkList) {
		// if (park.getType() == 3) {
		// outsideparks.add(park);
		// }
		// }
		// modelMap.addAttribute("parks", outsideparks);
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
		return "reconciliation";
	}

	// demo1
	@RequestMapping(value = "/demo1", produces = { "application/json;charset=UTF-8" })
	public String demo1(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		// List<Park> outsideparks = new ArrayList<>();
		// List<Park> outsideparks = new ArrayList<>();
		// for (Park park : parkList) {
		// if (park.getType() == 3) {
		// outsideparks.add(park);
		// }
		// }
		// modelMap.addAttribute("parks", outsideparks);
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
		return "demo1";
	}

	// demo2
	@RequestMapping(value = "/demo2", produces = { "application/json;charset=UTF-8" })
	public String demo2(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		// List<Park> outsideparks = new ArrayList<>();
		// List<Park> outsideparks = new ArrayList<>();
		// for (Park park : parkList) {
		// if (park.getType() == 3) {
		// outsideparks.add(park);
		// }
		// }
		// modelMap.addAttribute("parks", outsideparks);
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
		return "demo2";
	}

	// demo3
	@RequestMapping(value = "/demo3", produces = { "application/json;charset=UTF-8" })
	public String demo3(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		// List<Park> outsideparks = new ArrayList<>();
		// List<Park> outsideparks = new ArrayList<>();
		// for (Park park : parkList) {
		// if (park.getType() == 3) {
		// outsideparks.add(park);
		// }
		// }
		// modelMap.addAttribute("parks", outsideparks);
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
		return "demo3";
	}

	// 新加页面
	@RequestMapping(value = "/reconciliation2", produces = { "application/json;charset=UTF-8" })
	public String reconciliation2(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username != null)
			parkList = parkService.filterPark(parkList, username);
		// List<Park> outsideparks = new ArrayList<>();
		// List<Park> outsideparks = new ArrayList<>();
		// for (Park park : parkList) {
		// if (park.getType() == 3) {
		// outsideparks.add(park);
		// }
		// }
		// modelMap.addAttribute("parks", outsideparks);
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
		return "reconciliation2";
	}

	@RequestMapping(value = "/feeoperatorCharge", produces = { "application/json;charset=UTF-8" })
	public String feeoperatorCharge(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
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
		return "feeOperatorChargeData";
	}

	@RequestMapping(value = "/arrearage", produces = { "application/json;charset=UTF-8" })
	public String arrearage(ModelMap modelMap, HttpServletRequest request, HttpSession session) {
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
		return "arrearage";
	}

	@RequestMapping(value = "/paidNotify", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public String paidNotify(@RequestBody Map<String, Object> args) {
		int poschargeId = (int) args.get("poschargeId");
		float paidAmount = (float) args.get("paidAmount");
		int paidType = (int) args.get("paidType");
		return null;
	}

	@RequestMapping(value = "/getByParkAndRange", method = RequestMethod.POST, produces = {
	"application/json;charset=utf-8" })
	@ResponseBody
	public String getByParkAndRange(@RequestBody Map<String, Object> args) {
		int parkId = Integer.parseInt((String) args.get("parkId"));
		String startDay = (String) args.get("startDay");
		String endDay = (String) args.get("endDay");
		Map<String, Object> retMap = new HashMap<String, Object>();
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
		List<PosChargeData> posChargeDatas = chargeSerivce.selectPosdataByParkAndRange(parsedStartDay, parsedEndDay,
				parkId);
		if (posChargeDatas.isEmpty()) {
			retMap.put("status", 1002);
		} else {
			retMap.put("status", 1001);
			retMap.put("message", "success");
			retMap.put("body", posChargeDatas);
		}
		return Utility.gson.toJson(retMap);
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
			Map<String, Object> posChargeDatas = chargeSerivce.getParkChargeByDay(parkId, df.format(d));
			comparemap.put(d.getTime(), posChargeDatas);
			time += oneDay;
		}
		return Utility.gson.toJson(comparemap);
	}

	@RequestMapping(value = "/getParkRecordsCountByRange", method = RequestMethod.POST, produces = {
	"application/json;charset=utf-8" })
	@ResponseBody
	public String getParkRecordsCountByRange(@RequestBody Map<String, Object> args) {
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
			Map<String, Object> posChargeDatas = chargeSerivce.getParkChargeCountByDay(parkId, df.format(d));
			comparemap.put(d.getTime(), posChargeDatas);
			time += oneDay;
		}
		return Utility.gson.toJson(comparemap);
	}

	@RequestMapping(value = "getByCardnumber", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCardnumber(@RequestBody Map<String, String> args) {
		String cardNumber = args.get("cardNumber");
		return Utility.createJsonMsg(1001, "success", chargeSerivce.getByCardNumber(cardNumber));
	}

	@RequestMapping(value = "getByCardnumberAuthority", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCardnumberAuthority(@RequestBody Map<String, String> args, HttpSession session) {
		String cardNumber = args.get("cardNumber");
		String username = (String) session.getAttribute("username");
		AuthUser user = authService.getUserByUsername(username);
		List<Park> parkList = parkService.getParks();
		if (username == null)
			return null;
		if (user.getRole() == AuthUserRole.ADMIN.getValue()) {
			return Utility.createJsonMsg(1001, "success", chargeSerivce.getByCardNumber(cardNumber));
		}
		parkList = parkService.filterPark(parkList, username);
		List<PosChargeData> posChargeDatas = new ArrayList<>();
		for (Park park : parkList) {
			posChargeDatas.addAll(chargeSerivce.getByCardNumberAndPark(cardNumber, park.getId()));
		}
		return Utility.createJsonMsg(1001, "success", posChargeDatas);
	}

	//停车记录
	@RequestMapping(value = "getByCarNumberAndPN", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCarNumberAndPN(@RequestBody Map<String, String> args, HttpSession session) {
		int parkId=Integer.parseInt((String)args.get("parkId"));
		try{
			String cardNumber = args.get("cardNumber");
			String parkName = args.get("parkName");
			String operatorId=args.get("operatorId");
			return Utility.createJsonMsg(1001, "success", chargeSerivce.getByCardNumberAndParkName(cardNumber, parkName,parkId,operatorId));
		}catch(Exception e){
			args.get("operatorId");
			System.err.println(args.get("operatorId"));
		}
		
		return args.get("operatorId");
	}

	//停车记录根据车牌号时间段查询
	@RequestMapping(value = "/getByParkDatetime", method = RequestMethod.POST, produces = {
	"application/json;charset=utf-8" })
	@ResponseBody
	public String getByParkDatetime(@RequestBody Map<String, Object> args) throws ParseException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		String cardNumber=(String)args.get("cardNumber");
		String startDate = (String) args.get("startDate");
		String endDate = (String) args.get("endDate");
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
		if(cardNumber!=null||cardNumber==null){
			List<PosChargeData> posChargeDatas = chargeSerivce.getByParkDatetime(cardNumber,startDate, endDate);
			retMap.put("status", 1001);
			retMap.put("message", "success");
			retMap.put("body", posChargeDatas);
		}else{
			retMap.put("status", 1002);
			retMap.put("message", "failed");
		}
		return Utility.gson.toJson(retMap);
	}

	@RequestMapping(value = "getByCardnumberAndPort", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCardnumberAndPort(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		Integer portNumber = (Integer) args.get("portNumber");
		return Utility.createJsonMsg(1001, "success",
				chargeSerivce.getByCardNumberAndPort(cardNumber, portNumber).get(0));
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String count() {
		int count = chargeSerivce.count();
		return Utility.createJsonMsg(1001, "success", count);
	}

	@RequestMapping(value = "/getById", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getById(@RequestBody Map<String, Integer> args) {
		Integer id = args.get("id");
		PosChargeData posChargeDatas = chargeSerivce.getById(id);
		return Utility.createJsonMsg(1001, "success", posChargeDatas);
	}

	@RequestMapping(value = "getByParkName", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getByParkName(@RequestBody Map<String, String> args) {
		String parkName = args.get("parkName");
		return Utility.createJsonMsg(1001, "success", chargeSerivce.getByParkName(parkName));
	}

	@RequestMapping(value = "deleteByParkIdAndDate", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteByParkIdAndDate(@RequestBody Map<String, String> args) {
		int parkId = Integer.parseInt(args.get("parkId"));
		String startDate = args.get("startDate");
		String endDate = args.get("endDate");
		Integer count = chargeSerivce.deleteByParkIdAndDate(parkId, startDate, endDate);
		Map<String, Object> result = new HashMap<>();
		result.put("count", count);
		return Utility.createJsonMsg(1001, "success", result);
	}

	@RequestMapping(value = "deleleById", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleleById(@RequestBody Map<String, String> args) {
		int id = Integer.parseInt(args.get("id"));
		Integer count = chargeSerivce.deleteById(id);
		Map<String, Object> result = new HashMap<>();
		result.put("count", count);
		return Utility.createJsonMsg(1001, "success", result);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String page(@RequestBody Map<String, Object> args, HttpSession session) {
		@SuppressWarnings("unused")
		int low = (int) args.get("low");
		@SuppressWarnings("unused")
		int count = (int) args.get("count");
		String userName = (String) session.getAttribute("username");
		List<PosChargeData> list = chargeSerivce.getByParkAuthority(userName);
		return Utility.createJsonMsg(1001, "success", chargeSerivce.getByParkAuthority(userName));
	}

	@RequestMapping(value = "/getByCount", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getByCount(@RequestBody Map<String, Object> args) {
		int low = (int) args.get("low");
		int count = (int) args.get("count");
		List<PosChargeData> posChargeDatas = chargeSerivce.getPage(low, count);
		return Utility.createJsonMsg(1001, "success", posChargeDatas);
	}

	@RequestMapping(value = "/pageByParkId", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String pageByParkId(@RequestBody Map<String, Object> args, HttpSession session) {
		// String username = (String) session.getAttribute("username");
		int parkId = (int) args.get("parkId");
		int start = (int) args.get("start");
		int count = (int) args.get("count");
		List<PosChargeData> posChargeDatas = chargeSerivce.getPageByParkId(parkId, start, count);
		if (posChargeDatas.isEmpty()) {
			return Utility.createJsonMsg(1001, "success",
					posdataService.selectPosdataByPageAndPark(parkId, start, count));
		}
		return Utility.createJsonMsg(1001, "success", posChargeDatas);
	}

	@RequestMapping(value = "/pageArrearage", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String pageArrearage(@RequestBody Map<String, Object> args) {
		int low = (int) args.get("low");
		int count = (int) args.get("count");
		return Utility.createJsonMsg(1001, "success", chargeSerivce.getPageArrearage(low, count));
	}

	/*
	 * @RequestMapping(value = "/getByCount", method = RequestMethod.POST, produces
	 * = { "application/json;charset=UTF-8" })
	 * 
	 * @ResponseBody public String getByCount(@RequestBody Map<String, Object> args)
	 * { int low = (int) args.get("low"); int count = (int) args.get("count");
	 * List<PosChargeData> posChargeDatas = chargeSerivce.getPage(low, count);
	 * return Utility.createJsonMsg(1001, "success", posChargeDatas); }
	 */
	@RequestMapping(value = "/getParkCarportStatusToday", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String getParkCarportStatusToday(@RequestBody Map<String, Object> args) {
		int parkId = (int) args.get("parkId");
		List<PosChargeData> resultData = new ArrayList<>();
		Date tmpdate = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 5);
		List<PosChargeData> posChargeDatas = chargeSerivce.getParkCarportStatusToday(parkId, tmpdate);
		for (PosChargeData posChargeData : posChargeDatas) {
			// if (posChargeData.getExitDate()==null) {
			resultData.add(posChargeData);
			// }
		}
		return Utility.createJsonMsg(1001, "success", resultData);
	}

	@RequestMapping(value = "/getParkCarportStatusTodaySimple", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String getParkCarportStatusTodaySimple(@RequestBody Map<String, Object> args) {
		int parkId = (int) args.get("parkId");
		Date tmpdate = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 365);
		List<PosChargeData> posChargeDatas = chargeSerivce.getParkCarportStatusToday(parkId, tmpdate);
		List<PosChargeDataSimple> posChargeDataSimples = new ArrayList<>();
		int num = 1;
		for (PosChargeData posChargeData : posChargeDatas) {
			if (num > 10) {
				break;
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			if (posChargeData.isPaidCompleted() == false && posChargeData.getExitDate() != null) {
				num++;
				PosChargeDataSimple posChargeDataSimple = new PosChargeDataSimple();
				posChargeDataSimple.setId(posChargeData.getId());
				posChargeDataSimple.setCardNumber(posChargeData.getCardNumber());
				posChargeDataSimple.setUnPaidMoney(Double.parseDouble(df.format(posChargeData.getUnPaidMoney())));
				posChargeDataSimple.setExitDate(posChargeData.getExitDate());
				posChargeDataSimples.add(posChargeDataSimple);
			}

		}
		return Utility.createJsonMsg(1001, "success", posChargeDataSimples);
	}

	@RequestMapping(value = "/pageArrearageByParkId", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String pageArrearageByParkId(@RequestBody Map<String, Object> args) {
		int parkId = (int) args.get("parkId");
		int start = (int) args.get("start");
		int count = (int) args.get("count");
		return Utility.createJsonMsg(1001, "success", chargeSerivce.getPageArrearageByParkId(parkId, start, count));
	}

	@RequestMapping(value = "/get", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String get(@RequestParam(value = "cardNumber", required = false) String cardNumber) {
		List<PosChargeData> charges = null;
		if (cardNumber != null) {
			try {
				charges = chargeSerivce.getDebt(cardNumber);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Utility.createJsonMsg(1002, "请先绑定计费标准到停车场");
			}
		} else {
			charges = chargeSerivce.getUnCompleted();
		}

		return Utility.createJsonMsg(1001, "success", charges);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String insert(@RequestBody PosChargeData charge) throws ParseException, AlipayApiException {

		int parkId = charge.getParkId();
		Park park = parkService.getParkById(parkId);
		// Outsideparkinfo outsideparkinfo =
		// outsideParkInfoService.getByParkidAndDate(parkId,
		// charge.getEntranceDate());
		if (charge.getParkDesc() == null) {
			charge.setParkDesc(park.getName());
		}
		Boolean isMonthUser = false;
		Boolean isRealMonthUser = false;
		List<Monthuser> monthusers = monthUserService.getByCardNumber(charge.getCardNumber());
		Monthuser monthuserUse = new Monthuser();
		for (Monthuser monthuser : monthusers) {
			if (monthuser.getParkid().intValue() == charge.getParkId()) {
				isMonthUser = true;
				monthuserUse = monthuser;
				break;
			}
		}
		if (isMonthUser) {
			Long diff = (monthuserUse.getEndtime().getTime() - (new Date()).getTime());
			if (diff > 0) {
				isRealMonthUser = true;

			}
		}
		if (isRealMonthUser) {
			charge.setParkDesc(charge.getParkDesc() + "-p包月车");
		} else {
			charge.setParkDesc(charge.getParkDesc() + "-p临停车");
		}
		if (park == null || park.getFeeCriterionId() == null) {
			return Utility.createJsonMsg(1002, "请先绑定计费标准到停车场");
		}
		// if (outsideparkinfo != null) {
		// if (charge.isPaidCompleted() == false) {
		// int Unusedcarportcount = outsideparkinfo.getUnusedcarportcount();
		// outsideparkinfo.setUnusedcarportcount(Unusedcarportcount - 1);
		// outsideparkinfo.setEntrancecount(outsideparkinfo.getEntrancecount() +
		// 1);
		// outsideparkinfo.setPossigndate(new Date());
		// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		// } else {
		// outsideparkinfo.setEntrancecount(outsideparkinfo.getEntrancecount() +
		// 1);
		// outsideparkinfo.setOutcount(outsideparkinfo.getOutcount() + 1);
		// outsideparkinfo.setAmountmoney((float)
		// (outsideparkinfo.getAmountmoney() + charge.getChargeMoney()));
		// outsideparkinfo.setRealmoney((float) (outsideparkinfo.getRealmoney()
		// + charge.getPaidMoney()
		// + charge.getGivenMoney() - charge.getChangeMoney()));
		// outsideparkinfo.setPossigndate(new Date());
		// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		// }
		// }

		if (charge.getEntranceDate() == null) {
			charge.setEntranceDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		int ret = chargeSerivce.insert(charge);
		if (ret == 1) {
			List<Parktoalipark> parktoaliparks = parkToAliparkService.getByParkId(parkId);
			if (!parktoaliparks.isEmpty()) {
				Parktoalipark parktoalipark = parktoaliparks.get(0);
				Map<String, String> argstoali = new HashMap<>();
				argstoali.put("parking_id", parktoalipark.getAliparkingid());
				argstoali.put("car_number", charge.getCardNumber());
				argstoali.put("in_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliEnterInfo");
				// aliparkFeeService.parkingEnterinfoSync(argstoali);
			}
			return Utility.createJsonMsg(1001, "success");
		}

		else
			return Utility.createJsonMsg(1002, "failed");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String modify(@RequestBody PosChargeData charge) {

		int ret = chargeSerivce.update(charge);
		if (ret == 1)
			return Utility.createJsonMsg(1001, "success");
		else
			return Utility.createJsonMsg(1002, "failed");
	}

	@RequestMapping(value = "/rollbackExit", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String rollbackExit(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("plateNumber");
		int id = (int) args.get("id");
		PosChargeData posChargeData = posChargeDataService.getById(id);
		if (posChargeData == null) {
			return Utility.createJsonMsg(1002, "无记录");
		}
		if (!posChargeData.getCardNumber().equals(cardNumber)) {
			return Utility.createJsonMsg(1002, "车牌错误");
		}
		posChargeData.setPaidCompleted(false);
		posChargeData.setChargeMoney(0);
		posChargeData.setChangeMoney(0);
		posChargeData.setPaidMoney(0);
		posChargeData.setDiscount(0.0);
		posChargeData.setExitDate1(null);
		int ret = posChargeDataService.update(posChargeData);
		if (ret == 1) {
			jedisClient.set("P-" + posChargeData.getParkId() + "-" + posChargeData.getCardNumber(),
					String.valueOf(posChargeData.getId()), 2592000);
			return Utility.createJsonMsg(1001, "success");
		}

		else
			return Utility.createJsonMsg(1002, "failed");
	}

	@RequestMapping(value = "/updatePayType", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String updatePayType(@RequestBody Map<String, Object> args) {
		int chargeId = (int) args.get("chargeId");
		int payType = (int) args.get("payType");
		String carNumber = (String) args.get("carNumber");
		PosChargeData posChargeData = posChargeDataService.getById(chargeId);
		if (posChargeData != null && posChargeData.getCardNumber().equals(carNumber)) {
			posChargeData.setPayType(payType);
			posChargeDataService.update(posChargeData);
			return Utility.createJsonMsg(1001, "success");
		}
		return Utility.createJsonMsg(1002, "failed");

	}

	@RequestMapping(value = "/updateWithPlateNumber", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String updateWithPlateNumber(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("plateNumber");
		String operatorId = args.get("operatorId") != null ? (String) args.get("operatorId") : null;
		String rejectReason = args.get("rejectReason") != null ? (String) args.get("rejectReason") : null;
		int id = (int) args.get("id");
		double chargeMoney = args.get("chargeMoney") != null ? (double) args.get("chargeMoney") : -1;
		double paidMoney = args.get("paidMoney") != null ? (double) args.get("paidMoney") : -1;
		double discount = args.get("discount") != null ? (double) args.get("discount") : -1;

		PosChargeData posChargeData = posChargeDataService.getById(id);
		if (posChargeData == null) {
			return Utility.createJsonMsg(1002, "无记录");
		}
		if (!posChargeData.getCardNumber().equals(cardNumber)) {
			return Utility.createJsonMsg(1002, "车牌错误");
		}
		if (operatorId != null) {
			posChargeData.setOperatorId(operatorId);
		}
		if (rejectReason != null) {
			posChargeData.setRejectReason(rejectReason);
		}
		if (chargeMoney != -1) {
			posChargeData.setChargeMoney(chargeMoney);
		}
		if (paidMoney != -1) {
			posChargeData.setPaidMoney(paidMoney);
		}
		if (discount != -1) {
			posChargeData.setDiscount(discount);
		}
		int num = posChargeDataService.update(posChargeData);
		if (num == 1) {
			return Utility.createJsonMsg(1001, "success");
		} else {
			return Utility.createJsonMsg(1002, "failed");
		}
	}

	@RequestMapping(value = "/updateEDate", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String updateEDate(@RequestBody Map<String, String> args) throws ParseException {
		String carNumber = args.get("carNumber");
		String entranceDate = args.get("entranceDate");
		List<PosChargeData> posChargeDatas = chargeSerivce.getLastRecord(carNumber, 1);
		if (posChargeDatas.isEmpty()) {
			return Utility.createJsonMsg(1002, "无记录");
		}
		PosChargeData posChargeData = posChargeDatas.get(0);
		posChargeData.setEntranceDate(entranceDate);
		int num = chargeSerivce.update(posChargeData);
		if (num == 1) {
			return Utility.createJsonMsg(1001, "ok");
		}
		return Utility.createJsonMsg(1002, "failed!");
	}

	@RequestMapping(value = "/updateYj", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String updateYj(@RequestBody Map<String, String> args) {
		int parkId = Integer.parseInt(args.get("parkId"));
		String cardNumber = (String) args.get("cardNumber");
		Double yajin = Double.parseDouble(args.get("yajin"));
		List<PosChargeData> posChargeDatas = chargeSerivce.getByParkIdAndCardNumber(parkId, cardNumber);
		if (posChargeDatas.isEmpty()) {
			return Utility.createJsonMsg(1002, "no record");
		}
		// Outsideparkinfo outsideparkinfo =
		// outsideParkInfoService.getByParkidAndDate(parkId,posChargeDatas.get(0).getEntranceDate());
		// outsideparkinfo.setRealmoney((float) (outsideparkinfo.getRealmoney()
		// + yajin));
		posChargeDatas.get(0).setPaidMoney(yajin);
		// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		int ret = chargeSerivce.update(posChargeDatas.get(0));
		if (ret == 1)
			return Utility.createJsonMsg(1001, "success");
		else
			return Utility.createJsonMsg(1002, "failed");
	}
	
	@RequestMapping(value = "/commonQuery", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String commonQuery(@RequestBody Map<String, Object> args) throws Exception {
		String carNumber = (String) args.get("carNumber");
		int parkId = (int) args.get("parkId");
		String exitDate = (String) args.get("exitDate");
		Park park=parkService.getParkById(parkId);
		FeeCriterion feeCriterion=feeCriterionService.getById(park.getFeeCriterionId());
		List<PosChargeData> queryCharges = null;
		if (exitDate != null) {
			Date eDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
			try {
				queryCharges = chargeSerivce.queryDebtWithParkId(carNumber, eDate, parkId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		} else {
			try {
				queryCharges = chargeSerivce.queryDebtWithParkId(carNumber, new Date(),parkId);
			} catch (Exception e) {
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		}
		if (queryCharges.isEmpty()) {
			List<PosChargeData> posChargeDataList = chargeSerivce.getLastRecordWithPark(carNumber, 1, parkId);
			if (posChargeDataList.isEmpty()) {				
				return Utility.createJsonMsg(1002, "没有获取到数据", queryCharges);
			}
			PosChargeData posChargeData = posChargeDataList.get(0);
			if (posChargeData.getExitDate()!=null) {
				return Utility.createJsonMsg(1002, "没有未支付数据", queryCharges);
			}

			Date payDate = new Date();
			long diff = new Date().getTime() - payDate.getTime();
			if (diff > 1000 * 60 * 15) {
				return Utility.createJsonMsg(1001, "已在15分钟内支付", queryCharges);
			}else {
				Date incomeDate = new Date(payDate.getTime() - (long) (feeCriterion.getFreemins() - 15) * 1000 * 60);

				PosChargeData charge2 = new PosChargeData();
				charge2.setCardNumber(carNumber);
				charge2.setParkId(park.getId());
				charge2.setOperatorId("超时15分钟");
				charge2.setParkDesc(posChargeData.getParkDesc());
				charge2.setEntranceDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(incomeDate));
				int num = chargeSerivce.insert(charge2);
				jedisClient.del("P-"+parkId+"-"+carNumber);
				jedisClient.set("P-"+parkId+"-"+carNumber,String.valueOf(charge2.getId()) , 2592000);
				if (num != 1) {
					logger.info(carNumber+"未成功重新入场");
					return Utility.createJsonMsg(1002, "未成功重新入场", queryCharges);
				}
				queryCharges = chargeSerivce.queryDebtWithParkId(carNumber, new Date(),parkId);
				logger.info(carNumber+"重新入场获取计费");
			}
		}
		return Utility.createJsonMsg(1001, "success", queryCharges);
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String query(@RequestBody Map<String, Object> args) throws ParseException {
		String cardNumber = (String) args.get("cardNumber");
		String exitDate = (String) args.get("exitDate");
		List<PosChargeData> queryCharges = null;
		if (exitDate != null) {
			Date eDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
			try {
				queryCharges = chargeSerivce.queryDebt(cardNumber, eDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		} else {
			try {
				queryCharges = chargeSerivce.queryDebt(cardNumber, new Date());
			} catch (Exception e) {
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		}
		return Utility.createJsonMsg(1001, "success", queryCharges);
	}

	@RequestMapping(value = "/getArrearageByCardNumber", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getArrearageByCardNumber(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		List<PosChargeData> queryCharges = chargeSerivce.getArrearageByCardNumber(cardNumber);

		return Utility.createJsonMsg(1001, "success", queryCharges);
	}

	@RequestMapping(value = "/getArrearageById", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getArrearageById(@RequestBody Map<String, Object> args) {
		String id = (String) args.get("orderId");
		PosChargeData queryCharges = chargeSerivce.getById(Integer.parseInt(id));

		return Utility.createJsonMsg(1001, "success", queryCharges);
	}
	@RequestMapping(value = "/getDebtWithParkId", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getDebtWithParkId(@RequestBody Map<String, Object> args) throws Exception {
		String carNumber = (String) args.get("carNumber");
		int parkId=(int) args.get("parkId");
		List<PosChargeData> queryCharges = chargeSerivce.getDebtWithParkId(carNumber, new Date(), parkId);

		return Utility.createJsonMsg(1001, "success", queryCharges);
	}
	@RequestMapping(value = "/getArrearageByCardNumberSimple", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getArrearageByCardNumberSimple(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		List<PosChargeData> queryCharges = chargeSerivce.getArrearageByCardNumber(cardNumber);
		List<PosChargeDataSimple> posChargeDataSimples = new ArrayList<>();
		for (PosChargeData posChargeData : queryCharges) {
			PosChargeDataSimple posChargeDataSimple = new PosChargeDataSimple();
			posChargeDataSimple.setId(posChargeData.getId());
			posChargeDataSimple.setCardNumber(posChargeData.getCardNumber());
			posChargeDataSimple.setUnPaidMoney(posChargeData.getUnPaidMoney());
			posChargeDataSimple.setExitDate(posChargeData.getExitDate());
			posChargeDataSimples.add(posChargeDataSimple);
		}
		return Utility.createJsonMsg(1001, "success", posChargeDataSimples);
	}

	@RequestMapping(value = "/queryCurrent", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String queryCurrent(@RequestBody Map<String, Object> args) throws ParseException {
		String cardNumber = (String) args.get("cardNumber");
		String exitDate = (String) args.get("exitDate");
		List<PosChargeData> queryCharges = null;
		if (exitDate != null) {
			Date eDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
			try {
				queryCharges = chargeSerivce.queryCurrentDebt(cardNumber, eDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		} else {
			try {
				queryCharges = chargeSerivce.queryCurrentDebt(cardNumber, new Date());
			} catch (Exception e) {
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		}
		return Utility.createJsonMsg(1001, "success", queryCharges);
	}

	@RequestMapping(value = "/unpaid", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String getDebt(@RequestBody Map<String, Object> args) throws ParseException {
		String cardNumber = (String) args.get("cardNumber");
		String exitDate = (String) args.get("exitDate");

		List<PosChargeData> unpaidCharges = null;
		if (exitDate != null) {
			Date eDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
			try {
				unpaidCharges = chargeSerivce.getDebt(cardNumber, eDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		} else {
			try {
				unpaidCharges = chargeSerivce.getDebt(cardNumber);
			} catch (Exception e) {
				return Utility.createJsonMsg(1002, "请先绑定停车场计费标准");
			}
		}

		return Utility.createJsonMsg(1001, "success", unpaidCharges);
	}

	@RequestMapping(value = "/exitAndPay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String exitAndPay(@RequestBody Map<String, Object> args) throws Exception {
		String cardNumber = (String) args.get("cardNumber");
		double money = (double) args.get("money");
		String exitDate = (String) args.get("exitDate");
		String operatorId = (String) args.get("operatorId");
		logger.info("exitAndPay-"+cardNumber+"-"+money);
		PosChargeData payRet = null;

		Date eDate = new Date();
		if (exitDate != null) {
			eDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
		}
		// List<PosChargeData> unpaidCharges = chargeSerivce.getDebt(cardNumber, eDate);
		try {
			if (operatorId == null) {
				payRet = chargeSerivce.pay(cardNumber, money);
			} else {
				payRet = chargeSerivce.payWithOperatorId(cardNumber, money, operatorId);
			}
		} catch (Exception e) {
			return Utility.createJsonMsg(1002, "没有欠费条目或请先绑定停车场计费标准");
		}
		return Utility.createJsonMsg(1001, "success", payRet);
	}

	@RequestMapping(value = "/pay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String pay(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		double money = (double) args.get("money");

		PosChargeData payRet = null;
		try {
			payRet = chargeSerivce.pay(cardNumber, money);
		} catch (Exception e) {
			return Utility.createJsonMsg(1002, "没有欠费条目或请先绑定停车场计费标准");
		}

		return Utility.createJsonMsg(1001, "success", payRet);
	}

	@RequestMapping(value = "/getInfoByCardNumber", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String getInfoByCardNumber(@RequestBody Map<String, Object> args) throws Exception {
		String cardNumber = (String) args.get("cardNumber");
		Map<String, Object> result = new HashMap<>();
		List<PosChargeData> charges = chargeSerivce.queryDebt(cardNumber, new Date());
		if (charges.isEmpty()) {
			result.put("status", 1002);
			return Utility.gson.toJson(result);
		}
		PosChargeData lastCharge = charges.get(0);
		double charge = (lastCharge.getChargeMoney() - lastCharge.getPaidMoney()) > 0
				? (lastCharge.getChargeMoney() - lastCharge.getPaidMoney())
						: 0;
				result.put("deposit", lastCharge.getPaidMoney());
				result.put("charge", charge);
				result.put("chargeId", lastCharge.getId());
				Feeoperator feeoperator = feeOperatorService.getOperatorByAccount(lastCharge.getOperatorId()).get(0);
				if (feeoperator == null) {
					result.put("status", 1002);
					return Utility.gson.toJson(result);
				}
				result.put("operatorName", feeoperator.getName());
				result.put("operatorPhone", feeoperator.getPhone());
				Park park = parkService.getParkById(lastCharge.getParkId());
				FeeCriterion feeCriterion = feeCriterionService.getById(park.getFeeCriterionId());
				if (feeCriterion == null) {
					result.put("status", 1002);
					return Utility.gson.toJson(result);
				}
				result.put("feeCriterion", feeCriterion.getExplaination());
				result.put("status", 1001);
				return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/pushInfoFromWechat", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String pushInfoFromWechat(@RequestBody Map<String, Object> args) throws Exception {
		Integer chargeId = (Integer) args.get("chargeId");
		Map<String, Object> result = new HashMap<>();
		PosChargeData posChargeData = chargeSerivce.getById(chargeId);
		Double charge = (Double) args.get("charge");
		posChargeData.setGivenMoney(charge);
		posChargeData.setPayType(3);
		posChargeData.setExitDate1(new Date());
		posChargeData.setPaidCompleted(true);
		if (chargeSerivce.update(posChargeData) == 1) {
			result.put("status", 1001);
		} else {
			result.put("status", 1002);
		}
		List<Pos> poses = posService.getByParkId(posChargeData.getParkId());
		List<String> audiences = new ArrayList<>();
		for (Pos pos : poses) {
			audiences.add(pos.getNum());
		}
		Map<String, String> extras = new HashMap<>();
		extras.put("parkId", String.valueOf(posChargeData.getParkId()));
		extras.put("portNum", String.valueOf(posChargeData.getPortNumber()));
		Jpush.SendPushToAudiencesWithExtras(audiences, extras, "wechatPaidChanged");
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/rejectReason", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String rejectReason(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		String rejectReason = (String) args.get("rejectReason");
		PosChargeData lastCharge = null;
		try {
			lastCharge = chargeSerivce.updateRejectReason(cardNumber, rejectReason);
		} catch (Exception e) {
			return Utility.createJsonMsg(1002, "异常");
		}
		return Utility.createJsonMsg(1001, "success", lastCharge);
	}

	@RequestMapping(value = "/payById", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String payById(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		double money = (double) args.get("money");
		Integer chargeId = (Integer) args.get("id");
		PosChargeData payRet = chargeSerivce.getById(chargeId);
		if (payRet != null && payRet.getCardNumber().equals(cardNumber)) {
			if (payRet.getUnPaidMoney() <= money) {
				payRet.setGivenMoney(money + payRet.getGivenMoney());
				money -= payRet.getUnPaidMoney();
				payRet.setPaidCompleted(true);
				String data = new DecimalFormat("0.00").format(money);
				payRet.setChangeMoney(Double.parseDouble(data));
				chargeSerivce.update(payRet);
			} else {
				payRet.setGivenMoney(money + payRet.getGivenMoney());
				// payRet.setPaidMoney(payRet.getPaidMoney()+money);
				payRet.setUnPaidMoney(payRet.getUnPaidMoney() - money);
				chargeSerivce.update(payRet);
			}
		} else {
			return Utility.createJsonMsg(1002, "付费失败");
		}

		return Utility.createJsonMsg(1001, "success", payRet);
	}

	@RequestMapping(value = "/getFeeOperatorChargeData", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String getFeeOperatorChargeData(@RequestBody Map<String, String> args) throws ParseException {
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
			parsedEndDay = sdf.parse(endDay + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> results = chargeSerivce.getFeeOperatorChargeData(parsedStartDay, parsedEndDay);
		return Utility.createJsonMsg(1001, "success", results);
	}

	@RequestMapping(value = "/repay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String repay(@RequestBody Map<String, Object> args) {
		String cardNumber = (String) args.get("cardNumber");
		double money = (double) args.get("money");

		List<PosChargeData> payRet = null;
		try {
			payRet = chargeSerivce.repay(cardNumber, money);
		} catch (Exception e) {
			return Utility.createJsonMsg(1002, "没有欠费条目或请先绑定停车场计费标准");
		}
		return Utility.createJsonMsg(1001, "success", payRet);
	}

	@RequestMapping(value = "/getNewFee", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewFee(@RequestBody PosChargeData charge) throws Exception {
		chargeSerivce.newFeeCalcExpense(charge, charge.getExitDate(), false);
		return Utility.createJsonMsg(1001, "success", charge);
	}

	@RequestMapping(value = "/hardwareRecord", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String hardwareRecord(@RequestBody Map<String, Object> args) throws Exception {

		Map<String, Object> result = new HashMap<>();
		result.put("status", 1001);

		return Utility.gson.toJson(result);
	}

	@RequestMapping("/getExcel")
	@ResponseBody
	public void getExcel(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
		List<PosChargeData> posdatas = chargeSerivce.getPage(0, 100000);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
	}

	@RequestMapping(value = "/getExcelByParkAndDay")
	@ResponseBody
	public void getExcelByParkAndDay(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String date = request.getParameter("date");
		String parkId = request.getParameter("parkId");

		List<PosChargeData> posdatas = chargeSerivce.getByParkAndDay(Integer.parseInt(parkId), date);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		String filename = sFormat.format(new Date()) + "poschargedata.xlsx";
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
	}

	@RequestMapping(value = "/getExcelByDay")
	@ResponseBody
	public void getExcelByDay(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String date = request.getParameter("date");
		List<PosChargeData> posdatas = chargeSerivce.getAllByDay(date);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
	}

	@RequestMapping(value = "/getPayTypeCountByRange", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	public @ResponseBody String getPayTypeCountByRange(@RequestBody Map<String, Object> args) throws ParseException {
		String startDate = (String) args.get("startDate");
		String endDate = (String) args.get("endDate");
		int parkId = (int) args.get("parkId");
		List<PosChargeData> posdatas = chargeSerivce.getByParkAndDayRange(parkId, startDate, endDate);
		int type0 = 0;
		int type1 = 0;
		int type2 = 0;
		int type9 = 0;
		for (PosChargeData posChargeData : posdatas) {
			switch (posChargeData.getPayType()) {
			case 0:
				type0 += 1;
				break;
			case 1:
				type1 += 1;
				break;
			case 2:
				type2 += 1;
				break;
			case 9:
				type9 += 1;
				break;

			default:
				break;
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("status", 1001);
		result.put("alipay", type0);
		result.put("weipay", type1);
		result.put("cash", type2);
		result.put("barrier", type9);
		return Utility.gson.toJson(result);
	}

	@RequestMapping(value = "/getExcelByParkAndDayRange")
	@ResponseBody
	public void getExcelByParkAndDayRange(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String parkId = request.getParameter("parkId");
		if(parkId!=""){
			List<PosChargeData> posdatas = chargeSerivce.getByParkAndDayRange(Integer.parseInt(parkId), startDate, endDate);
			String docsPath = request.getSession().getServletContext().getRealPath("/");
			final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
			String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
			OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook();
			excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
		}else{
			List<PosChargeData> posdatas = chargeSerivce.getAllByDayRange(startDate, endDate);
			String docsPath = request.getSession().getServletContext().getRealPath("/");
			final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
			String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
			OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook();
			excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
		}
	}

	@RequestMapping(value = "/getMonthuserCountsByPark")
	@ResponseBody
	public void getMonthuserCountsByPark(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ParseException, FileNotFoundException {
		String startDate = request.getParameter("startDate");
		startDate = startDate + " 00:00:00";
		String endDate = request.getParameter("endDate");
		endDate = endDate + " 23:59:59";
		String parkId = request.getParameter("parkId");
		String count = request.getParameter("count");
		List<Map<String, Object>> datas = monthUserService.getMonthuserCountsByDateRangeAndPark(
				Integer.parseInt(parkId), new SimpleDateFormat(Constants.DATEFORMAT).parse(startDate),
				new SimpleDateFormat(Constants.DATEFORMAT).parse(endDate), Integer.parseInt(count));
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "姓名", "停车次数", "车牌号", "类型" };
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "monthusercount.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceMonthCountsInfoExcel("停车次数", headers, datas, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "monthusercount.xlsx", response);
	}

	@RequestMapping(value = "/getExcelByDayRange")
	@ResponseBody
	public void getExcelByDayRange(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, NumberFormatException, ParseException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		List<PosChargeData> posdatas = chargeSerivce.getAllByDayRange(startDate, endDate);
		String docsPath = request.getSession().getServletContext().getRealPath("/");
		final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
		String[] headers = { "车牌", "停车场名", "车位号", "操作员id", "收费状态", "押金", "应收费", "补交", "返还", "进场时间", "离场时间", "优惠金额" };
		OutputStream out = new FileOutputStream(docsPath + FILE_SEPARATOR + "poschargedata.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.produceExceldataPosChargeData("收费明细", headers, posdatas, workbook);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Utility.download(docsPath + FILE_SEPARATOR + "poschargedata.xlsx", response);
	}
}
