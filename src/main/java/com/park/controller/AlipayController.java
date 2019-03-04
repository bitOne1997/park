package com.park.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.AgeFileFilter;
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
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayEcoMycarParkingAgreementQueryRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderPayRequest;
import com.alipay.api.request.AlipayEcoMycarParkingVehicleQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayEcoMycarParkingAgreementQueryResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderPayResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingVehicleQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.park.model.Alipayrecord;
import com.park.model.Constants;
import com.park.model.Njcarfeerecord;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.ActiveMqService;
import com.park.service.AliParkFeeService;
import com.park.service.AlipayrecordService;
import com.park.service.ParkService;
import com.park.service.ParkToAliparkService;
import com.park.service.PosChargeDataService;
import com.park.service.Utility;

@Controller
@RequestMapping("alipay3")
public class AlipayController {
	@Autowired
	AliParkFeeService parkFeeService;
	@Autowired
	PosChargeDataService poschargedataService;
	@Autowired
	ParkService parkService;
	@Autowired
	AlipayrecordService alipayrecordService;
	@Autowired
	ParkToAliparkService parkToAliparkService;
	
	private static Log logger = LogFactory.getLog(AlipayController.class);
	
	public String APP_PRIVATE_KEY = Constants.alipayPrivateKey;
	public String ALIPAY_PUBLIC_KEY = Constants.alipayPublicKey;
	String URL = "https://openapi.alipay.com/gateway.do";
	String APP_ID = Constants.alipayAppId;
	String FORMAT = "json";
	String CHARSET = "UTF-8";
	String SIGN_TYPE = "RSA";
	AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
			ALIPAY_PUBLIC_KEY, SIGN_TYPE);
	AlipayClient alipayClient2 = new DefaultAlipayClient(URL, Constants.alipayAppId4, Constants.alipayPrivateKey4,
			FORMAT, CHARSET, Constants.alipayPublicKey4, SIGN_TYPE);

	@RequestMapping(value = "/parkingPayH5", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	public String aliParkingPayH5(ModelMap modelMap, @RequestParam("auth_code") String auth_code,
			@RequestParam("car_id") String car_id, @RequestParam("parking_id") String parking_id) throws Exception {
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(auth_code);
		request.setGrantType("authorization_code");
		AlipaySystemOauthTokenResponse oauthTokenResponse = null;
		try {
			oauthTokenResponse = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			// 处理异常
			e.printStackTrace();
		}
		//AlipayUserInfoShareRequest request2 = new AlipayUserInfoShareRequest();
		String access_token = oauthTokenResponse.getAccessToken();
		String userId = oauthTokenResponse.getUserId();
		AlipayEcoMycarParkingVehicleQueryRequest request3 = new AlipayEcoMycarParkingVehicleQueryRequest();
		request3.setBizContent("{" + "\"car_id\":\"" + car_id + "\"" + "  }");
		String carNumber = "";
		try {
			AlipayEcoMycarParkingVehicleQueryResponse response3 = alipayClient.execute(request3, access_token);
			carNumber = response3.getCarNumber();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelMap.addAttribute("carNumber", carNumber);
		List<PosChargeData> charges = poschargedataService.queryDebt(carNumber, new Date());

		if (charges.isEmpty()) {
			return "alipayh5/noRecord";
		}

		PosChargeData lastCharge = charges.get(0);

		modelMap.addAttribute("charge", lastCharge.getChargeMoney());
		modelMap.addAttribute("enterDate",
				new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
		if (lastCharge.getExitDate() == null) {
			lastCharge.setExitDate1(new Date());
		}
		long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime()) / 60000;
		modelMap.addAttribute("parkingDuration", parkingDuration);
		Park park = parkService.getParkById(lastCharge.getParkId());
		modelMap.addAttribute("parkName", park.getName());
		modelMap.addAttribute("chargeId", lastCharge.getId());
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("parkingId", parking_id);

		AlipayTradeCreateRequest request5 = new AlipayTradeCreateRequest();
		String out_trade_no = new Date().getTime() + "parkingfee";
		// PosChargeData lastCharge1 =
		// poschargedataService.getById(lastCharge.getId());
	//	poschargedataService.getCharges(lastCharge.getCardNumber());
		String total_amount = String.valueOf(lastCharge.getChargeMoney());
		request5.setNotifyUrl("http://www.iotclouddashboard.com/park/alipay/notifyUrl");
		request5.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," +
		// "\"seller_id\":\"2088601016329110\"," +
				"\"total_amount\":" + total_amount + "," +

				"\"subject\":\"停车费-"+carNumber
				+ "\"," +

				"\"buyer_id\":\"" + userId + "\"," +

				"\"extend_params\":{" + "\"sys_service_provider_id\":\"2088721707329444\"" + "}" +

				"}");
		modelMap.addAttribute("outTradeNO", out_trade_no);
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("money", total_amount);
		AlipayTradeCreateResponse response = alipayClient2.execute(request5);
		modelMap.addAttribute("tradeNO", response.getTradeNo());

		Alipayrecord alipayrecord = new Alipayrecord();
		alipayrecord.setOutTradeNo(out_trade_no);
		alipayrecord.setAlitradeno(response.getTradeNo());
		alipayrecord.setPoschargeid(lastCharge.getId());
		alipayrecord.setUserid(userId);
		alipayrecord.setParkingid(parking_id);
		alipayrecord.setDate(new Date());
		alipayrecord.setStatus("0");
		alipayrecord.setMoney(lastCharge.getChargeMoney());
		alipayrecordService.insertSelective(alipayrecord);

		return "alipayh5/index";
	}

//	@RequestMapping(value = "/testparkingPayH5", method = RequestMethod.GET, produces = {
//			"application/json;charset=UTF-8" })
//	public String testparkingPayH5(ModelMap modelMap) throws Exception {
//		String carNumber = "川A1LM97";
//		modelMap.addAttribute("carNumber", carNumber);
//		List<PosChargeData> charges = poschargedataService.queryDebt(carNumber, new Date());
//		if (charges.isEmpty()) {
//			return "alipayh5/noRecord";
//		}
//		PosChargeData lastCharge = charges.get(0);
//		modelMap.addAttribute("charge", lastCharge.getChargeMoney());
//		modelMap.addAttribute("enterDate",
//				new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
//		if (lastCharge.getExitDate() == null) {
//			lastCharge.setExitDate1(new Date());
//		}
//		long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime()) / 60000;
//		modelMap.addAttribute("parkingDuration", parkingDuration);
//		Park park = parkService.getParkById(lastCharge.getParkId());
//		modelMap.addAttribute("parkName", park.getName());
//		modelMap.addAttribute("chargeId", lastCharge.getId());
//		modelMap.addAttribute("userId", 112);
//		modelMap.addAttribute("parkingId", 206);
//
//		return "alipayh5/index";
//	}
	@RequestMapping(value = "barcodePay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String payerweima(@RequestBody Map<String, Object> args) throws Exception {
		logger.info("barcodepayin"+args);
		Map<String, Object> result = new HashMap<>();
		String auth_code=(String) args.get("authCode");
		String mac=(String) args.get("mac");
		int poschargeId=(int) args.get("posChargeId");
		
		PosChargeData posChargeData=poschargedataService.getById(poschargeId);
		if (posChargeData==null) {
			return Utility.createJsonMsg(1002, "pochargeId不存在");
		}
		double chargeMoney=0.0;
		if (!posChargeData.isPaidCompleted()) {
			List<PosChargeData> charges = poschargedataService.queryDebtWithParkId(posChargeData.getCardNumber(), new Date(),posChargeData.getParkId());
			chargeMoney=charges.get(0).getChargeMoney();
		}
		else {
			chargeMoney=posChargeData.getChargeMoney();
		}
		
		String out_trade_no = new Date().getTime() + "-"+mac;
		AlipayTradePayRequest request = new AlipayTradePayRequest(); 
		request.setNotifyUrl("https://www.iotclouddashboard.com/park/alipay3/notifyUrlBarcode");
		request.setBizContent("{" +
				"\"out_trade_no\":\""+out_trade_no
				+ "\"," +
				"\"scene\":\"bar_code\"," +
				"\"auth_code\":\""+auth_code
				+ "\"," +
				"\"subject\":\""+posChargeData.getParkDesc()+posChargeData.getCardNumber()+"停车费"
				+ "\"," +
				"\"total_amount\":"
				+ chargeMoney +
				"}");
		logger.info("发送参数:"+request.getBizContent());
		AlipayTradePayResponse response = alipayClient.execute(request);
		logger.info(poschargeId+":"+response.getBody());
		Alipayrecord alipayrecord = new Alipayrecord();
		alipayrecord.setOutTradeNo(out_trade_no);
		alipayrecord.setAlitradeno(response.getTradeNo());
		alipayrecord.setPoschargeid(poschargeId);
		alipayrecord.setUserid(mac);
		//alipayrecord.setParkingid(posChargeData.getParkId());
		alipayrecord.setDate(new Date());
		alipayrecord.setStatus("0");
		alipayrecord.setMoney(chargeMoney);
		alipayrecordService.insertSelective(alipayrecord);
		
		if(response.isSuccess()){
			result.put("status", 1001);
			result.put("body", response.getTradeNo());
			} else {
			result.put("status", 1002);
			result.put("body", response.getSubMsg());
			}
		logger.info("barcodePayreturn:"+result);
		return Utility.gson.toJson(result);
	}
	@RequestMapping(value = "notifyJSPay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String notifyJSPay(HttpServletRequest request) {
		String tradeNo = request.getParameter("tradeNo");
		List<Alipayrecord> alipayrecords = alipayrecordService.getByAliTradeNO(tradeNo);
		Alipayrecord alipayrecord = alipayrecords.get(0);
		PosChargeData lastCharge = poschargedataService.getById(alipayrecord.getPoschargeid());
		alipayrecord.setStatus("2");
		alipayrecord.setMoney(lastCharge.getChargeMoney());
		alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
		lastCharge.setPaidCompleted(true);
		lastCharge.setPayType(0);
		poschargedataService.update(lastCharge);

		Map<String, String> args = new HashMap<>();
		args.put("user_id", alipayrecord.getUserid());
		int parkid = parkToAliparkService.getByAliParkId(alipayrecord.getParkingid()).get(0).getParkid();
		Park park = parkService.getParkById(parkid);
		args.put("out_parking_id", String.valueOf(parkid));
		args.put("parking_name", park.getName());
		args.put("car_number", lastCharge.getCardNumber());
		args.put("out_order_no", alipayrecord.getOutTradeNo());
		args.put("order_status", "0");
		args.put("order_time", new SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
		args.put("order_no", alipayrecord.getAlitradeno());
		args.put("pay_time", new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
		args.put("pay_type", "1");
		args.put("pay_money", String.valueOf(alipayrecord.getMoney()));
		args.put("in_time", new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
		args.put("parking_id", alipayrecord.getParkingid());
		long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime()) / 60000;
		args.put("in_duration", String.valueOf(parkingDuration));
		args.put("card_number", "*");

		try {
			
			logger.info("js结算后支付宝出场结果"+parkFeeService.parkingOrderSync(args));
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, String> result = new HashMap<>();
		return Utility.createJsonMsg(1001, "ok");
	}
	@RequestMapping(value = "notifyUrlBarcode", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String notifyUrlBarcode(HttpServletRequest request) {
		String trade_status = request.getParameter("trade_status");
		String trade_no = request.getParameter("trade_no");
		String receipt_amount = request.getParameter("receipt_amount");
		String out_trade_no = request.getParameter("out_trade_no");
		logger.info("notifyUrlBarcode:" + out_trade_no+receipt_amount+trade_no);
		 String mac = out_trade_no.split("-")[1];
		
		if (trade_status.equals("TRADE_SUCCESS")) {
			
			//通知
			ActiveMqService.SendWithQueueName(out_trade_no,"barcodeAlipay");
			
			List<Alipayrecord> alipayrecords = alipayrecordService.getByOutTradeNO(out_trade_no);
			Alipayrecord alipayrecord = alipayrecords.get(0);
			PosChargeData lastCharge = poschargedataService.getById(alipayrecord.getPoschargeid());	
			lastCharge.setChargeMoney(Double.parseDouble(receipt_amount));
			lastCharge.setPaidCompleted(true);
			lastCharge.setPayType(10);
		//	lastCharge.setExitDate1(new Date());
		//	poschargedataService.update(lastCharge);					
			
			alipayrecord.setStatus("1");
			alipayrecord.setMoney(Double.parseDouble(receipt_amount));
			alipayrecord.setAlitradeno(trade_no);
			alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
			
			Park park=parkService.getParkById(lastCharge.getParkId());
			ActiveMqService.SendTopicWithMac(lastCharge,String.valueOf(park.getId()),mac,park.getPortLeftCount(),10);
			lastCharge.setPayType(0);
			poschargedataService.update(lastCharge);
		}
		return "success";
	}
	@RequestMapping(value = "notifyUrl", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String notifyUrl(HttpServletRequest request) {
		String trade_status = request.getParameter("trade_status");
		String trade_no = request.getParameter("trade_no");
		String receipt_amount = request.getParameter("receipt_amount");
		String out_trade_no = request.getParameter("out_trade_no");
		logger.error("alipayUrlNotify:" + out_trade_no);
		if (trade_status.equals("TRADE_SUCCESS")) {
			List<Alipayrecord> alipayrecords = alipayrecordService.getByOutTradeNO(out_trade_no);
			Alipayrecord alipayrecord = alipayrecords.get(0);
			PosChargeData lastCharge = poschargedataService.getById(alipayrecord.getPoschargeid());	
			try {
				poschargedataService.getCharges(lastCharge.getCardNumber());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lastCharge.setChargeMoney(Double.parseDouble(receipt_amount));
			lastCharge.setPaidCompleted(true);
			lastCharge.setPayType(0);
			lastCharge.setExitDate1(new Date());
			poschargedataService.update(lastCharge);

			alipayrecord.setStatus("1");
			alipayrecord.setMoney(Double.parseDouble(receipt_amount));
			alipayrecord.setAlitradeno(trade_no);
			alipayrecordService.updateByPrimaryKeySelective(alipayrecord);

			Map<String, String> args = new HashMap<>();
			args.put("user_id", alipayrecord.getUserid());
			args.put("out_parking_id", "3");
			args.put("parking_name", "美凯龙停车场");
			args.put("car_number", lastCharge.getCardNumber());
			args.put("out_order_no", alipayrecord.getOutTradeNo());
			args.put("order_status", "0");
			args.put("order_time", new SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
			args.put("order_no", alipayrecord.getAlitradeno());
			args.put("pay_time", new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
			args.put("pay_type", "1");
			args.put("pay_money", String.valueOf(alipayrecord.getMoney()));
			args.put("in_time", new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
			args.put("parking_id", alipayrecord.getParkingid());
			long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime())
					/ 60000;
			args.put("in_duration", String.valueOf(parkingDuration));
			args.put("card_number", "*");
			AlipayEcoMycarParkingOrderSyncResponse dString = new AlipayEcoMycarParkingOrderSyncResponse();
			try {
				dString = parkFeeService.parkingOrderSync(args);
				logger.info("支付宝出场结果:" + dString.getBody());
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}
//	@RequestMapping(value = "testAlipayCreate", method = RequestMethod.GET, produces = {
//			"application/json;charset=UTF-8" })
//	@ResponseBody
//	public String testAlipayCreate() throws AlipayApiException {
//		AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
//		String out_trade_no = new Date().getTime() + "parkingfee";
//		// request.setBizContent("{" + "\"out_trade_no\":\""+out_trade_no+"\","
//		// + "\"total_amount\":2," +
//		//
//		// "\"subject\":\"2088021992523142\"," +
//		//
//		// "\"buyer_id\":\""+"2088021992523142"+"\"" +
//		//
//		// "}");
//		request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":" + "0.5" + "," +
//
//				"\"subject\":\"九比特停车费\"," +
//
//				"\"buyer_id\":\"" + "2088002280776369" + "\"," +
//
//				"\"extend_params\":{" + "\"sys_service_provider_id\":\"2088721707329444\"" + "}" +
//
//				"}");
//		AlipayTradeCreateResponse response = alipayClient2.execute(request);
//
//		if (response.isSuccess()) {
//			System.out.println("调用成功");
//			System.out.println(response.getTradeNo());
//		} else {
//			System.out.println("调用失败");
//		}
//		return Utility.gson.toJson(response);
//	}

//	@RequestMapping(value = "quickPayWeb/{chargeId}/{userId}/{parkingId}", method = RequestMethod.GET, produces = {
//			"application/json;charset=UTF-8" })
//	public String quickPayWeb(ModelMap modelMap, HttpServletRequest httpRequest, HttpServletResponse httpResponse,
//			@PathVariable int chargeId, @PathVariable String userId, @PathVariable String parkingId) throws Exception {
//		AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
//		String out_trade_no = new Date().getTime() + "parkingfee";
//		PosChargeData lastCharge = poschargedataService.getById(chargeId);
//		poschargedataService.getCharges(lastCharge.getCardNumber());
//		String total_amount = String.valueOf(lastCharge.getChargeMoney());
//		Alipayrecord alipayrecord = new Alipayrecord();
//		alipayrecord.setOutTradeNo(out_trade_no);
//		alipayrecord.setPoschargeid(chargeId);
//		alipayrecord.setUserid(userId);
//		alipayrecord.setParkingid(parkingId);
//		alipayrecord.setDate(new Date());
//		alipayrecord.setStatus("0");
//		alipayrecordService.insertSelective(alipayrecord);
//
//		request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," +
//
//				"\"total_amount\":" + total_amount + "," +
//
//				"\"subject\":\"九比特停车费\"," +
//
//				"\"buyer_id\":\"" + userId + "\"" +
//
//				"}");
//		modelMap.addAttribute("outTradeNO", out_trade_no);
//		modelMap.addAttribute("userId", userId);
//		modelMap.addAttribute("money", total_amount);
//		AlipayTradeCreateResponse response = alipayClient.execute(request);
//		modelMap.addAttribute("tradeNO", response.getTradeNo());
//		if (response.isSuccess()) {
//
//		} else {
//
//		}
//		return "alipayh5/pay";
//	}

//	@RequestMapping(value = "returnUrlWebPay/{chargeId}", method = RequestMethod.GET, produces = {
//			"application/json;charset=UTF-8" })
//	public String returnUrlWebPay(ModelMap modelMap, @PathVariable int chargeId) {
//		PosChargeData posChargeData = poschargedataService.getById(chargeId);
//
//		modelMap.addAttribute("chargeMoney", posChargeData.getChargeMoney());
//		return "alipayh5/success";
//	}
//
//	// 支付宝通知回调
//	@RequestMapping(value = "notifyUrlWebPay", method = RequestMethod.POST, produces = {
//			"application/json;charset=UTF-8" })
//	@ResponseBody
//	public String notifyUrlWebPay(@RequestParam("out_trade_no") String out_trade_no, HttpServletRequest request) {
//		String trade_no = request.getParameter("trade_no");
//		String trade_status = request.getParameter("trade_status");
//		String receipt_amount = request.getParameter("receipt_amount");
//		List<Alipayrecord> alipayrecords = alipayrecordService.getByOutTradeNO(out_trade_no);
//		Alipayrecord alipayrecord = alipayrecords.get(0);
//		PosChargeData lastCharge = poschargedataService.getById(alipayrecord.getPoschargeid());
//		if (trade_status.equals("TRADE_SUCCESS")) {
//			alipayrecord.setStatus("2");
//			alipayrecord.setMoney(Double.parseDouble(receipt_amount));
//			alipayrecord.setAlitradeno(trade_no);
//			alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
//			lastCharge.setPaidCompleted(true);
//			lastCharge.setPayType(0);
//			poschargedataService.update(lastCharge);
//
//			Map<String, String> args = new HashMap<>();
//			args.put("user_id", alipayrecord.getUserid());
//			int parkid = parkToAliparkService.getByAliParkId(alipayrecord.getParkingid()).get(0).getParkid();
//			Park park = parkService.getParkById(parkid);
//			args.put("out_parking_id", String.valueOf(parkid));
//			args.put("parking_name", park.getName());
//			args.put("car_number", lastCharge.getCardNumber());
//			args.put("out_order_no", out_trade_no);
//			args.put("order_status", "0");
//			args.put("order_time", new SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
//			args.put("order_no", trade_no);
//			args.put("pay_time", new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//			args.put("pay_type", "1");
//			args.put("pay_money", receipt_amount);
//			args.put("in_time", new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
//			args.put("parking_id", alipayrecord.getParkingid());
//			long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime())
//					/ 60000;
//			args.put("in_duration", String.valueOf(parkingDuration));
//			args.put("card_number", "*");
//
//			try {
//				parkFeeService.parkingOrderSync(args);
//			} catch (AlipayApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//
//			Map<String, String> args = new HashMap<>();
//			args.put("user_id", alipayrecord.getUserid());
//			int parkid = parkToAliparkService.getByAliParkId(alipayrecord.getParkingid()).get(0).getParkid();
//			Park park = parkService.getParkById(parkid);
//			args.put("out_parking_id", String.valueOf(parkid));
//			args.put("parking_name", park.getName());
//			args.put("car_number", lastCharge.getCardNumber());
//			args.put("out_order_no", out_trade_no);
//			args.put("order_status", "1");
//			args.put("order_time", new SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
//			args.put("order_no", trade_no);
//			args.put("pay_time", new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//			args.put("pay_type", "1");
//			args.put("pay_money", receipt_amount);
//			args.put("in_time", new SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
//			args.put("parking_id", alipayrecord.getParkingid());
//			long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime())
//					/ 60000;
//			args.put("in_duration", String.valueOf(parkingDuration));
//			args.put("card_number", "*");
//
//			try {
//				parkFeeService.parkingOrderSync(args);
//			} catch (AlipayApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return "success";
//	}

	@RequestMapping(value = "notifyUrlAppPay", method = RequestMethod.POST)
	@ResponseBody
	public String notifyUrlAppPay(HttpServletRequest request) {
		String trade_no = request.getParameter("trade_no");
		String trade_status = request.getParameter("trade_status");
		String receipt_amount = request.getParameter("receipt_amount");
		String out_trade_no = request.getParameter("out_trade_no");
		if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")) {
			Alipayrecord alipayrecord = new Alipayrecord();
			alipayrecord.setAlitradeno(trade_no);
			alipayrecord.setOutTradeNo(out_trade_no);
			alipayrecord.setStatus("3");
			alipayrecord.setMoney(Double.parseDouble(receipt_amount));
			alipayrecordService.insertSelective(alipayrecord);
		}
		return "success";
	}

	@RequestMapping(value = "getAndroidAppPay", method = RequestMethod.POST)
	@ResponseBody
	public String getAndroidAppPay(@RequestBody Map<String, Object> args) {
		String amount = (String) args.get("amount");
		// String outTradeNo=(String)args.get("outTradeNo");
		String subject = (String) args.get("subject");
		String content = (String) args.get("content");
		String outTradeNo = new Date().getTime() + "android";
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(content);
		model.setSubject(subject);
		model.setOutTradeNo(outTradeNo);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(amount);
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("http://www.iotclouddashboard.com/park/alipay/notifyUrlAppPay");
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			return response.getBody();
			// System.out.println(response.getBody());//就是orderString
			// 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return "error";
	}

	@RequestMapping(value = "getAliPayUrl", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String getAliPayUrl(@RequestBody Map<String, Object> args) throws AlipayApiException {
		String amount = "";
		if (args.get("chargeId") != null) {
			int chargeId = (int) args.get("chargeId");
			PosChargeData lastCharge = poschargedataService.getById(chargeId);
			String out_trade_no = new Date().getTime() + "parkingfeequick";
			String total_amount = String.valueOf(lastCharge.getChargeMoney());
			amount = total_amount;
			Alipayrecord alipayrecord = new Alipayrecord();
			alipayrecord.setOutTradeNo(out_trade_no);
			alipayrecord.setPoschargeid(chargeId);
			alipayrecord.setDate(new Date());
			alipayrecord.setStatus("0");
			alipayrecordService.insert(alipayrecord);
		}
		if (args.get("amount") != null) {
			amount = (String) args.get("amount");
		}
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
		request.setBizModel(model);
		String outTradeNo = new Date().getTime() + "dmf";
		request.setNotifyUrl("http://www.iotclouddashboard.com/park/alipay/notifyUrl/");
		model.setOutTradeNo(outTradeNo);
		model.setTotalAmount(amount);
		model.setSubject("parking_fee");

		AlipayTradePrecreateResponse response = alipayClient.execute(request);
		return Utility.gson.toJson(response);
	}

//	@RequestMapping(value = "notifyUrl", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
//	@ResponseBody
//	public String notifyUrl(HttpServletRequest request) {
//		String trade_status = request.getParameter("trade_status");
//		String trade_no = request.getParameter("trade_no");
//		String receipt_amount = request.getParameter("receipt_amount");
//		String out_trade_no = request.getParameter("out_trade_no");
//		if (trade_status.equals("TRADE_SUCCESS")) {
//			List<Alipayrecord> alipayrecords = alipayrecordService.getByOutTradeNO(out_trade_no);
//			Alipayrecord alipayrecord = alipayrecords.get(0);
//			alipayrecord.setStatus("1");
//			alipayrecord.setMoney(Double.parseDouble(receipt_amount));
//			alipayrecord.setAlitradeno(trade_no);
//			alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
//		}
//		return "success";
//	}

	@RequestMapping(value = "queryTradeNo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String queryTradeNo(@RequestBody Map<String, Object> args) throws AlipayApiException {
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		String outTradeNO = (String) args.get("outTradeNO");
		request.setBizContent("{" + "\"out_trade_no\":\"" + outTradeNO + "\"" +
		// "\"trade_no\":\"2014112611001004680 073956707\"" +
				"  }");
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		return Utility.gson.toJson(response);
	}

	@RequestMapping(value = "getUserAccessToken", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void getUserAccessToken(@RequestParam("auth_code") String auth_code) throws AlipayApiException {

		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(auth_code);
		request.setGrantType("authorization_code");
		AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
		String access_token = oauthTokenResponse.getAccessToken();
	}

	@RequestMapping(value = "testParking", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testparking(@RequestBody Map<String, Object> args) {
		Map<String, Object> parkconfigdata = new HashMap<>();
		parkconfigdata.put("merchant_name", "九比特停车");
		parkconfigdata.put("merchant_service_phone", "15882009230");

		return null;
	}

	@RequestMapping(value = "testset", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testset(@RequestBody Map<String, String> arg) throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		String str1 = "https%3a%2f%2fopenauth.alipay.com%2foauth2%2fpublicAppAuthorize.htm%3fapp_id%3d2015101400439228%26scope%3dauth_user%26redirect_uri%3d";
		String str2 = "https%3a%2f%2fwww.iotclouddashboard.com%2falipay%2fparkingPayH5";
		args.put("merchant_name", "包泊停车场");
		args.put("merchant_service_phone", "13813966110");
		args.put("account_no", "xuling19820224@sina.com");
		args.put("interface_url", str2);
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkConfigSet(args));
	}

	@RequestMapping(value = "testquery", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testQuery() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();

		return Utility.createJsonMsg("1001", "success", parkFeeService.parkConfigQuery(args));
	}

	@RequestMapping(value = "testParkCreate", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testParkCreate() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		args.put("city_id", "320100");
		args.put("equipment_name", "包泊测试停车场-九比特");
		args.put("out_parking_id", "206");
		args.put("parking_address", "江苏省-南京市-江宁区 ");
//		args.put("longitude", "117.814557");
//		args.put("latitude", "32.067111");
		args.put("parking_lot_type", "B0019060PW");
		args.put("parking_start_time", "08:00:00");
		args.put("parking_end_time", "18:00:00");
		args.put("parking_number", "60");
		args.put("parking_lot_type", "1");
		args.put("parking_type", "1");
		args.put("payment_mode", "1");
		args.put("pay_type", "3");
		args.put("shopingmall_id", "test");
		args.put("parking_fee_description", "小车首小时内，每15分钟0.5元，首小时后，每15分钟0.5元。前15分钟免费");
		args.put("contact_name", "刘猛");
		args.put("contact_mobile", "13668378108");
		args.put("parking_name", "包泊测试停车场");
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkingInfoCreate(args));
	}
	@RequestMapping(value = "parkinfoInput", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoInput(@RequestBody Map<String, String> args) throws AlipayApiException {
		
		return Utility.createJsonMsg("1001", "success",parkFeeService.parkingInfoInput(args));
	}
	@RequestMapping(value = "parkinfoUpdate", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoUpdate(@RequestBody Map<String, String> args) throws AlipayApiException {
		
		return Utility.createJsonMsg("1001", "success",parkFeeService.parkingInfoUpdate2(args));
	}
	@RequestMapping(value = "testParkingEnterinfoSync", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testParkingEnterinfoSync() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		args.put("parking_id", "PI1501317472942184881");
		args.put("car_number", "川A1LM97");
		args.put("in_time", "2017-08-06 09:07:50");
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkingEnterinfoSync(args));
	}

	@RequestMapping(value = "testParkingExitinfoSync", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testParkingExitinfoSync() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		args.put("parking_id", "PI1501317472942184881");
		args.put("car_number", "川A1LM97");
		args.put("in_time", "2017-08-07 11:07:50");
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkingEnterinfoSync(args));
	}

	@RequestMapping(value = "testParkingOrderSync", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testParkingOrderSync() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		args.put("user_id", "2088002280776369");
		args.put("out_parking_id", "3");
		args.put("parking_name", "众彩物流园");
		args.put("car_number", "川A1LM97");
		args.put("out_order_no", "1502419188869parkingfee");
		args.put("order_status", "0");
		args.put("order_time", "2017-08-11 12:27:30");
		args.put("order_no", "2017081321001004360285068540");
		args.put("pay_time", "2017-08-11 12:27:30");
		args.put("pay_type", "1");
		args.put("pay_money", "2.50");
		args.put("in_time", "2017-08-12 12:20:30");
		args.put("parking_id", "PI1501317472942184881");
		args.put("in_duration", "120");
		args.put("card_number", "*");
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkingOrderSync(args));
	}

	@RequestMapping(value = "testCarStatusQuery", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testCarStatusQuery() throws AlipayApiException {
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		request.setBizContent("{" + " \"car_number\":\"川A1LM97\"" + " }");
		AlipayEcoMycarParkingAgreementQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			result.put("status", 1001);
			data.put("agreementStatus", true);
			if (response.getAgreementStatus().equals("1")) {
				data.put("agreementStatus", false);
			}
		} else {
			result.put("status", 1002);
		}

		result.put("body", data);
		return Utility.gson.toJson(result);

	}

	@RequestMapping(value = "QueryCarFeeStatus", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String QueryCarFeeStatus(@RequestBody Map<String, String> args) throws AlipayApiException {
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		String carNumber = args.get("carNumber");
		logger.info("查询车牌是否支持代扣,车牌号:"+carNumber);
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		data.put("carNumber", carNumber);
		request.setBizContent("{" + " \"car_number\":\"" + carNumber + "\"" + " }");
		AlipayEcoMycarParkingAgreementQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			result.put("status", 1001);
			data.put("agreementStatus", true);
			if (response.getAgreementStatus().equals("1")) {
				data.put("agreementStatus", false);
			}
		} else {
			result.put("status", 1002);
		}

		result.put("body", data);
		logger.info(carNumber+"返回结果:"+result.toString());
		return Utility.gson.toJson(result);

	}
/**
 * 
 * */
	@RequestMapping(value = "OperateCarFee", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String OperateCarFee(@RequestBody Map<String, String> args) throws AlipayApiException {
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		String money = args.get("money");
		String carNumber = args.get("carNumber");
		logger.info("代扣扣费:"+carNumber+"金额"+money);
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		data.put("carNumber", carNumber);
		request.setBizContent("{" + " \"car_number\":\"" + carNumber + "\"" + " }");
		AlipayEcoMycarParkingAgreementQueryResponse response = alipayClient.execute(request);
				
		if (response.isSuccess()) {
			result.put("status", 1001);
			data.put("agreementStatus", true);
			if (response.getAgreementStatus().equals("1")) {
				data.put("agreementStatus", false);
			}
			String out_trade_no = new Date().getTime() + "wuganfee";
			AlipayEcoMycarParkingOrderPayRequest request2 = new AlipayEcoMycarParkingOrderPayRequest();
			request2.setBizContent("{" +
			"\"car_number\":\""+carNumber+"\"," +
			"\"out_trade_no\":\""+out_trade_no+"\"," +
			"\"subject\":\"九比特停车缴费\"," +
			"\"total_fee\":"+money+"," +
			"\"seller_id\":\"2088021992523142\"," +
			"\"parking_id\":\"PI1501317472942184881\"," +
			"\"out_parking_id\":\"3\"," +
			"\"agent_id\":\"2088601016329110\"," +
			"\"car_number_color\":\"blue\"" +
			"}");
			AlipayEcoMycarParkingOrderPayResponse response2 = alipayClient.execute(request2);
			logger.info("代扣扣费:"+carNumber+"结果:"+response2.getBody());
			result.put("daikou", response2);
			
		} else {
			result.put("status", 1002);
		}

		result.put("body", data);
		return Utility.gson.toJson(result);
	}
}
