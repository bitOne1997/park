package com.park.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.park.model.Monthuser;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.BaseApiService;
import com.park.service.MonthUserService;
import com.park.service.ParkService;
import com.park.service.PosChargeDataService;
import com.park.service.Utility;

@Controller
@RequestMapping("SmartParkComm")
public class GongHangController extends BaseApiService {
	@Autowired
	PosChargeDataService posChargedataService;
	@Autowired
	MonthUserService monthUserservice;
	@Autowired
	ParkService parkService;
	private static Log logger = LogFactory.getLog(GongHangController.class);

	@RequestMapping(value = "queryOrderTemp", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String queryOrderTemp(@RequestBody Map<String, String> args) {
		logger.info("工行queryOrderTemp" + args.toString());
		String plateNo = args.get("plateNo");
		String parkCode = args.get("parkCode");
		String plateColor = args.get("plateColor");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", "0");
		ret.put("msg", "正确返回");

		if (plateNo == null) {
			ret.put("code", "1");
			ret.put("msg", "错误");
			return Utility.gson.toJson(setResultError("错误"));
		}
		List<PosChargeData> queryCharges = new ArrayList<>();
		try {
			Date exitdate = new Date();
			if (parkCode==null) {
				queryCharges = posChargedataService.queryDebt(plateNo, exitdate);
			}
			else {
				queryCharges = posChargedataService.queryDebtWithParkId(plateNo, exitdate, Integer.parseInt(parkCode));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return Utility.gson.toJson(setResultError("错误"));
		}
		if (queryCharges.isEmpty()) {

			return Utility.gson.toJson(setResultError("无停车记录"));
		}
		PosChargeData posChargeData = queryCharges.get(0);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", String.valueOf(posChargeData.getId()));
		data.put("plateNo", plateNo);
		data.put("plateColor", plateColor);
		data.put("parkCode", String.valueOf(posChargeData.getParkId()));
		data.put("startTime", new SimpleDateFormat("yyyyMMddHHmmss").format(posChargeData.getEntranceDate()));
		double dd = (new Date().getTime() - posChargeData.getEntranceDate().getTime()) / 1000;
		data.put("serviceTime", String.valueOf((int) dd));
		data.put("serviceFee", (int) (posChargeData.getChargeMoney() * 100));
		ret.put("data", data);
		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "payOrderTemp", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String payOrderTemp(@RequestBody Map<String, String> args) {
		logger.info("工行payOrderTemp" + args.toString());
		String orderId = args.get("orderId");
		String money = args.get("money");
		String plateNo = args.get("plateNo");
		String payTime = args.get("payTime");
		String saleType = args.get("saleType");
		String saleTime = args.get("saleTime");
		String salAmount = args.get("salAmount");
		int poschargeId = Integer.parseInt(orderId);
		PosChargeData Chargedata = posChargedataService.getById(poschargeId);
		Chargedata.setDiscount(Chargedata.getChargeMoney() - Double.parseDouble(money) / 100);
		Chargedata.setChargeMoney(Double.parseDouble(money) / 100);
		// Chargedata.setPaidMoney(Double.parseDouble(money) / 100);
		Chargedata.setPaidCompleted(true);
		Chargedata.setOperatorId("工行");
		Chargedata.setPayType(3);
		Chargedata.setExitDate1(new Date());
		Chargedata.setRejectReason(payTime);

		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", "0");
		ret.put("msg", "正确返回");
		if (posChargedataService.update(Chargedata) == 0) {
			ret.put("code", "1");
			ret.put("msg", "错误");
		}
		return Utility.gson.toJson(ret);
	}

	@RequestMapping(value = "queryOrderLong", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String queryOrderLong(@RequestBody Map<String, String> args) {
		logger.info("工行queryOrderLong" + args.toString());
		String parkCode = args.get("parkCode");
		String plateNo = args.get("plateNo");
		String plateColor = args.get("plateColor");
		List<Monthuser> monthusers = monthUserservice.getByCarnumberAndPark(plateNo, Integer.parseInt(parkCode));
		Map<String, Object> ret = new HashMap<String, Object>();
		if (monthusers.isEmpty()) {
			return Utility.gson.toJson(setResultError("无此包月用户"));
		}
		Monthuser monthuser = monthusers.get(0);
		Park park = parkService.getParkById(monthuser.getParkid());
		ret.put("parkCode", parkCode);
		ret.put("parkName", park.getName());
		ret.put("longType", "0");
		// ret.put("startTime", new
		// SimpleDateFormat("yyyyMMdd").format(monthuser.getStarttime()));
		// ret.put("endTime", new
		// SimpleDateFormat("yyyyMMdd").format(monthuser.getEndtime()));
		return Utility.gson.toJson(setResultSuccess(ret));
	}

	/**
	 * 查询包月金额
	 */
	@RequestMapping(value = "queryFeeLong", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String queryFeeLong(@RequestBody Map<String, Object> args) {
		logger.info("工行queryFeeLong" + args.toString());
		String parkCode = (String) args.get("parkCode");
		String plateNo = (String) args.get("plateNo");
		String plateColor = (String) args.get("plateColor");
		String longType = (String) args.get("longType");
		String period = (String) args.get("period");
		int days = Integer.parseInt(period);
		if (longType.equals("0")) {
			days = days * 30;
		}
		List<Monthuser> monthusers = monthUserservice.getByCarnumberAndPark(plateNo, Integer.parseInt(parkCode));
		if (monthusers.isEmpty()) {
			return Utility.gson.toJson(setResultError("无此包月用户"));
		}
		Monthuser monthuser = monthusers.get(0);
		// Park park=parkService.getParkById(monthuser.getParkid());
		int amount = 0;
		if (monthuser.getStatus() != null && monthuser.getStatus() == 0) {
			amount = Integer.parseInt(period) * 80;
		} else {
			amount = 300 * Integer.parseInt(period);
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("beginDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
		// Date now=
		ret.put("endDate", new SimpleDateFormat("yyyyMMdd")
				.format(new Date(System.currentTimeMillis() + (long) 1000 * 60 * 60 * 24 * days)));
		ret.put("serviceFee", String.valueOf(amount * 100));
		return Utility.gson.toJson(setResultSuccess(ret));
	}

	@RequestMapping(value = "chargedataMianmi", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String chargedataMianmi(@RequestBody Map<String, Object> args) {
		int poschargeId = (int)args.get("poschargeId");
		PosChargeData posChargeData=posChargedataService.getById(poschargeId);
		posChargeData.setOther2("工行免密");
		posChargedataService.update(posChargeData);
		Map<String, Object> ret = new HashMap<String, Object>();
		return Utility.gson.toJson(setResultSuccess(ret));
	}

	@RequestMapping(value = "payOrderLong", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String payOrderLong(@RequestBody Map<String, String> args) {
		logger.info("工行payOrderLong" + args.toString());
		String parkCode = args.get("parkCode");
		String plateNo = args.get("plateNo");
		String plateColor = args.get("plateColor");
		String money = args.get("money");
		String payTime = args.get("payTime");
		String longType = args.get("longType");
		String period = args.get("period");
		int days = Integer.parseInt(period);

		List<Monthuser> monthusers = monthUserservice.getByCarnumberAndPark(plateNo, Integer.parseInt(parkCode));
		Map<String, Object> ret = new HashMap<String, Object>();
		if (monthusers.isEmpty()) {
			return Utility.gson.toJson(setResultError("无此包月用户"));
		}
		Monthuser monthuser = monthusers.get(0);

		if (longType.equals("0")) {
			days = days * 30;
			monthuser.setEndtime1(new Date(monthuser.getEndtime().getTime() + (long) 1000 * 60 * 60 * 24 * days));
		} else {
			monthuser.setEndtime1(new Date(monthuser.getEndtime().getTime() + (long) 1000 * 60 * 60 * 24 * 30 * days));
		}
		monthUserservice.updateByPrimaryKeySelective(monthuser);
		ret.put("beginDate", new SimpleDateFormat("yyyyMMdd").format(new Date(monthuser.getStarttime().getTime())));
		ret.put("endDate", new SimpleDateFormat("yyyyMMdd").format(monthuser.getEndtime()));
		return Utility.gson.toJson(setResultSuccess(ret));
	}
}
