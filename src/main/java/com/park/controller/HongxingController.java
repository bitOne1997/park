package com.park.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingAgreementQueryRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderPayRequest;
import com.alipay.api.request.AlipayEcoMycarParkingVehicleQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayEcoMycarParkingAgreementQueryResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderPayResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingVehicleQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.park.model.AlipayChargeInfo;
import com.park.model.Alipayrecord;
import com.park.model.Constants;
import com.park.model.Hongxingrecord;
import com.park.model.Njcarfeerecord;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.AliParkFeeService;
import com.park.service.AlipayrecordService;
import com.park.service.HongxingRecordService;
import com.park.service.HongxingService;
import com.park.service.NjCarFeeRecordService;
import com.park.service.ParkService;
import com.park.service.ParkToAliparkService;
import com.park.service.PosChargeDataService;
import com.park.service.Utility;

@Controller
@RequestMapping("alipay")
public class HongxingController {
	@Autowired
	HongxingService hongxingService;
	@Autowired
	HongxingRecordService hongxingRecordService;
	@Autowired
	AlipayrecordService alipayrecordService;
	@Autowired
	PosChargeDataService poschargedataService;
	@Autowired
	ParkToAliparkService parkToAliparkService;
	@Autowired
	AliParkFeeService parkFeeService;
	@Autowired
	ParkService parkService;
	@Autowired
	NjCarFeeRecordService njCarFeeRecordService;

	private static Log logger = LogFactory.getLog(HongxingController.class);

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
	AlipayClient alipayClient3 = new DefaultAlipayClient(URL, Constants.alipayAppId6, Constants.alipayPrivateKey6,
			FORMAT, CHARSET, Constants.alipayPublicKey6, SIGN_TYPE);

	@RequestMapping(value = "getRecord", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getRecord(@RequestBody Map<String, String> args, ModelMap modelMap) throws Exception {

		String code = hongxingService.creatPayOrder("20171008170709140-A1N603", "c1648ccf33314dc384155896cf4d00b9");

		// Boolean
		// success=hongxingService.payOrderNotify("2.5","20170926085443491-AV738F","20170926085443491-AV738F");
		return Utility.createJsonMsg(1001, "success", code);
	}

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
			logger.info("alipaytoken返回:"+oauthTokenResponse.getBody());
		} catch (AlipayApiException e) {
			// 处理异常
			e.printStackTrace();
		}
	//	AlipayUserInfoShareRequest request2 = new AlipayUserInfoShareRequest();
		String access_token = oauthTokenResponse.getAccessToken();
		String userId = oauthTokenResponse.getUserId();
		AlipayEcoMycarParkingVehicleQueryRequest request3 = new AlipayEcoMycarParkingVehicleQueryRequest();
		request3.setBizContent("{" + "\"car_id\":\"" + car_id + "\"" + "  }");
		String carNumber = "";
		try {
			AlipayEcoMycarParkingVehicleQueryResponse response3 = alipayClient.execute(request3, access_token);
			logger.info("alipay停车信息返回:"+response3.getBody());
			carNumber = response3.getCarNumber();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("alipay停车查询:"+carNumber+" userId:"+userId);
		modelMap.addAttribute("carNumber", carNumber);
		
		AlipayChargeInfo alipayChargeInfo=alipayrecordService.getChargeDataByCarNumber(carNumber);
		if (!alipayChargeInfo.isValidate()) {
			return "alipayh5/noRecord";
		}
		PosChargeData lastCharge =alipayChargeInfo.getPosChargeData();
		AlipayClient alipayclientU=alipayChargeInfo.getAlipayClient();
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

	//	poschargedataService.getCharges(lastCharge.getCardNumber());
		String total_amount = String.valueOf(lastCharge.getChargeMoney());
		request5.setNotifyUrl("http://www.iotclouddashboard.com/park/alipay/notifyUrl");
		request5.setBizContent(
				"{" + "\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":" + total_amount + "," +

						"\"subject\":\"停车费-" + carNumber + "\"," +

						"\"buyer_id\":\"" + userId + "\"," +

						"\"extend_params\":{" + "\"sys_service_provider_id\":\""
								+ alipayChargeInfo.getServiceProvideId()
								+ "\"" + "}" +

						"}");
		modelMap.addAttribute("outTradeNO", out_trade_no);
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("money", total_amount);

		AlipayTradeCreateResponse response = alipayclientU.execute(request5);
		logger.error("zhifubao:" + response.getBody() + response.getSubMsg());
		modelMap.addAttribute("tradeNO", response.getTradeNo());

		Alipayrecord alipayrecord = new Alipayrecord();
		alipayrecord.setOutTradeNo(out_trade_no);
		alipayrecord.setAlitradeno(response.getTradeNo());
		alipayrecord.setPoschargeid(lastCharge.getId());
		alipayrecord.setUserid(userId);
		alipayrecord.setParkingid(parking_id);
		alipayrecord.setDate(new Date());
		alipayrecord.setStatus("0");
		alipayrecordService.insertSelective(alipayrecord);

		return "alipayh5/index";
	}
	
	
	// @RequestMapping(value = "quickPayWeb/{chargeId}/{userId}/{parkingId}",
	// method = RequestMethod.GET, produces = {
	// "application/json;charset=UTF-8" })
	// @ResponseBody
	// public void quickPayWeb(HttpServletRequest httpRequest,
	// HttpServletResponse httpResponse,
	// @PathVariable int chargeId, @PathVariable String userId, @PathVariable
	// String parkingId) throws Exception {
	// AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
	// alipayRequest.setReturnUrl("http://www.iotclouddashboard.com/park/alipay/returnUrlWebPay/"
	// + chargeId);
	// alipayRequest.setNotifyUrl("http://www.iotclouddashboard.com/park/alipay/notifyUrlWebPay");
	// PosChargeData lastCharge = poschargedataService.getById(chargeId);
	// try {
	// poschargedataService.getCharges(lastCharge.getCardNumber());
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// String out_trade_no = lastCharge.getOperatorId();
	// String total_amount = String.valueOf(lastCharge.getChargeMoney());
	// Alipayrecord alipayrecord = new Alipayrecord();
	// alipayrecord.setOutTradeNo(out_trade_no);
	// alipayrecord.setPoschargeid(chargeId);
	// alipayrecord.setUserid(userId);
	// alipayrecord.setParkingid(parkingId);
	// alipayrecord.setDate(new Date());
	// alipayrecord.setStatus("0");
	// alipayrecord.setMoney(lastCharge.getChargeMoney());
	// alipayrecordService.insertSelective(alipayrecord);
	//
	// String subject = "美凯龙停车费";
	// alipayRequest.setBizContent(
	// "{" + " \"out_trade_no\":\"" + out_trade_no + "\"," + "
	// \"total_amount\":\"" + total_amount + "\","
	// + " \"subject\":\"" + subject + "\"," + "
	// \"product_code\":\"QUICK_WAP_PAY\"" + " }");// 填充业务参数
	// String form = "";
	// try {
	// form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
	// } catch (AlipayApiException e) {
	// e.printStackTrace();
	// }
	// httpResponse.setContentType("text/html;charset=" + CHARSET);
	// httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
	// httpResponse.getWriter().flush();
	// httpResponse.getWriter().close();
	// }
	@RequestMapping(value = "notifyJSPay", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String notifyJSPay(HttpServletRequest request) {
		String tradeNo = request.getParameter("tradeNo");
		logger.info("js成功通知调用:" + tradeNo);
		List<Alipayrecord> alipayrecords = alipayrecordService.getByAliTradeNO(tradeNo);
		Alipayrecord alipayrecord = alipayrecords.get(0);
		PosChargeData lastCharge = poschargedataService.getById(alipayrecord.getPoschargeid());
		alipayrecord.setStatus("2");
		alipayrecord.setMoney(lastCharge.getChargeMoney());
		alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
		lastCharge.setPaidCompleted(true);
		lastCharge.setPayType(0);
		if (lastCharge.getRejectReason().length() > 4 && lastCharge.getRejectReason().length() < 10) {
			return Utility.createJsonMsg(1001, "已通知到!");
		}
		if (lastCharge.getParkId()==3) {
			Njcarfeerecord njcarfeerecord = njCarFeeRecordService.selectByCarNumber(lastCharge.getCardNumber()).get(0);
			String parkKey = "c1648ccf33314dc384155896cf4d00b9";
			if (njcarfeerecord.getParkname().contains("家乐福")) {
				parkKey = "ff8993a40b3a4249924f34044403b5bf";
			}

			String code = lastCharge.getRejectReason();
			// 通知
			Boolean success = hongxingService.payOrderNotify(String.valueOf(lastCharge.getChargeMoney()), code, code,
					parkKey);
			logger.info("调用接口参数:" + String.valueOf(lastCharge.getChargeMoney()) + code + parkKey);
			if (success) {
				logger.info("调用美凯龙支付通知成功");
				lastCharge.setRejectReason("成功通知");
			} else {
				logger.info("调用美凯龙支付失败");
				lastCharge.setRejectReason("失败通知");
			}
		}
		
		poschargedataService.update(lastCharge);
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
		long parkingDuration = (lastCharge.getExitDate().getTime() - lastCharge.getEntranceDate().getTime()) / 60000;
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

		return Utility.createJsonMsg(1001, "ok", dString);
	}

	@RequestMapping(value = "notifyUrltest", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public void notifyUrl1(HttpServletRequest request) {
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			logger.error("notifyUrl所收到的参数:" + paraName + ": " + request.getParameter(paraName));
		}
	}
	// @RequestMapping(value = "notifyUrlWebPay", method = RequestMethod.POST,
	// produces = {
	// "application/json;charset=UTF-8" })
	// @ResponseBody
	// public String notifyUrlWebPay(@RequestParam("out_trade_no") String
	// out_trade_no, HttpServletRequest request) {
	// String trade_no = request.getParameter("trade_no");
	// String trade_status = request.getParameter("trade_status");
	// String receipt_amount = request.getParameter("receipt_amount");
	// List<Alipayrecord> alipayrecords =
	// alipayrecordService.getByOutTradeNO(out_trade_no);
	// Alipayrecord alipayrecord = alipayrecords.get(0);
	// PosChargeData lastCharge =
	// poschargedataService.getById(alipayrecord.getPoschargeid());
	// if (trade_status.equals("TRADE_SUCCESS")) {
	// alipayrecord.setStatus("2");
	// alipayrecord.setMoney(Double.parseDouble(receipt_amount));
	// alipayrecord.setAlitradeno(trade_no);
	// alipayrecordService.updateByPrimaryKeySelective(alipayrecord);
	// lastCharge.setPaidCompleted(true);
	//
	// String code = hongxingService.creatPayOrder(lastCharge.getOperatorId());
	// // 通知
	// Boolean success = hongxingService.payOrderNotify(receipt_amount, code,
	// code);
	// if (success) {
	// lastCharge.setRejectReason("成功通知");
	// } else {
	// lastCharge.setRejectReason("失败通知");
	// }
	// poschargedataService.update(lastCharge);
	// Map<String, String> args = new HashMap<>();
	// args.put("user_id", alipayrecord.getUserid());
	//
	// args.put("out_parking_id", "mkl");
	// args.put("parking_name", "美凯龙停车场");
	// args.put("car_number", lastCharge.getCardNumber());
	// args.put("out_order_no", out_trade_no);
	// args.put("order_status", "0");
	// args.put("order_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
	// args.put("order_no", trade_no);
	// args.put("pay_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
	// args.put("pay_type", "1");
	// args.put("pay_money", receipt_amount);
	// args.put("in_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
	// args.put("parking_id", alipayrecord.getParkingid());
	// long parkingDuration = (lastCharge.getExitDate().getTime() -
	// lastCharge.getEntranceDate().getTime())
	// / 60000;
	// args.put("in_duration", String.valueOf(parkingDuration));
	// args.put("card_number", "*");
	//
	// try {
	// parkFeeService.parkingOrderSync(args);
	// } catch (AlipayApiException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	//
	// Map<String, String> args = new HashMap<>();
	// args.put("user_id", alipayrecord.getUserid());
	// args.put("out_parking_id", "mkl");
	// args.put("parking_name", "美凯龙停车场");
	// args.put("car_number", lastCharge.getCardNumber());
	// args.put("out_order_no", out_trade_no);
	// args.put("order_status", "1");
	// args.put("order_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(alipayrecord.getDate()));
	// args.put("order_no", trade_no);
	// args.put("pay_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
	// args.put("pay_type", "1");
	// args.put("pay_money", receipt_amount);
	// args.put("in_time", new
	// SimpleDateFormat(Constants.DATEFORMAT).format(lastCharge.getEntranceDate()));
	// args.put("parking_id", alipayrecord.getParkingid());
	// long parkingDuration = (lastCharge.getExitDate().getTime() -
	// lastCharge.getEntranceDate().getTime())
	// / 60000;
	// args.put("in_duration", String.valueOf(parkingDuration));
	// args.put("card_number", "*");
	//
	// try {
	// parkFeeService.parkingOrderSync(args);
	// } catch (AlipayApiException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return "success";
	// }

	//
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
			//如果是美凯龙这边的停车场
			if (lastCharge.getParkId()==3) {
				Njcarfeerecord njcarfeerecord = njCarFeeRecordService.selectByCarNumber(lastCharge.getCardNumber()).get(0);			
				String parkKey = "c1648ccf33314dc384155896cf4d00b9";
				if (njcarfeerecord.getParkname().contains("家乐福")) {
					parkKey = "ff8993a40b3a4249924f34044403b5bf";
				}
				String code = lastCharge.getRejectReason();
				// 通知
				Boolean success = hongxingService.payOrderNotify(String.valueOf(lastCharge.getChargeMoney()), code, code,
						parkKey);
				logger.info("调用接口参数:" + String.valueOf(lastCharge.getChargeMoney()) + code + parkKey);
				if (success) {
					logger.info("调用美凯龙支付通知成功");
					lastCharge.setRejectReason("成功通知nt");
				} else {
					logger.info("调用美凯龙支付失败");
					lastCharge.setRejectReason("失败通知");
				}
			}else {
				lastCharge.setRejectReason(trade_no);
			}
			
			lastCharge.setPaidCompleted(true);
			lastCharge.setPayType(0);
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

	// 停车ISV系统配置
	@RequestMapping(value = "testset", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String testset() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		// String
		// str2="https%3a%2f%2f139.196.19.162%3a8080%2fpark%2falipay%2fparkingPayH5";
		String str2 = "https%3a%2f%2fwww.iotclouddashboard.com%2falipay%2fparkingPayH5hx";
		args.put("merchant_name", "红星美凯龙");
		args.put("merchant_service_phone", "13814132270");
		args.put("account_no", "1481674188@qq.com");
		args.put("interface_url", str2);
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkConfigSet(args));
	}

	// 停车场的建立
	@RequestMapping(value = "testParkCreate", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String testParkCreate() throws AlipayApiException {
		Map<String, String> args = new HashMap<>();
		args.put("city_id", "320100");
		args.put("equipment_name", "红星美凯龙停车场");
		args.put("out_parking_id", "3");
		args.put("parking_address", "江苏省-南京市-江宁区 ");
		args.put("longitude", "117.814557");
		args.put("latitude", "32.067111");
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
		args.put("contact_mobile", "13814132270");
		args.put("parking_name", "红星美凯龙停车场");
		return Utility.createJsonMsg("1001", "success", parkFeeService.parkingInfoCreate(args));
	}

	@RequestMapping(value = "QueryCarFeeStatus", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String QueryCarFeeStatus(@RequestBody Map<String, String> args) throws AlipayApiException {
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		String carNumber = args.get("carNumber");
		logger.info("查询车牌是否支持代扣,车牌号:" + carNumber);
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
		logger.info(carNumber + "返回结果:" + result.toString());
		return Utility.gson.toJson(result);

	}

	// 车辆代扣缴费
	@RequestMapping(value = "OperateCarFee", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public String OperateCarFee(@RequestBody Map<String, String> args) throws AlipayApiException {
		AlipayEcoMycarParkingAgreementQueryRequest request = new AlipayEcoMycarParkingAgreementQueryRequest();
		String money = args.get("money");
		String carNumber = args.get("carNumber");
		logger.info("代扣扣费:" + carNumber + "金额:" + money);
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
				logger.info(carNumber + "不支持代扣");
			}
			String out_trade_no = new Date().getTime() + "wuganfee";
			AlipayEcoMycarParkingOrderPayRequest request2 = new AlipayEcoMycarParkingOrderPayRequest();
			request2.setBizContent("{" + "\"car_number\":\"" + carNumber + "\"," + "\"out_trade_no\":\"" + out_trade_no
					+ "\"," + "\"subject\":\"停车缴费\"," + "\"total_fee\":" + money + ","
					+ "\"seller_id\":\"2088721707329444\"," + "\"parking_id\":\"PI1509946268841654545\","
					+ "\"out_parking_id\":\"206\"," + "\"agent_id\":\"2088601016329110\","
					+ "\"car_number_color\":\"blue\"" + "}");
			AlipayEcoMycarParkingOrderPayResponse response2 = alipayClient.execute(request2);
			logger.info("代扣扣费:" + carNumber + "结果:" + response2.getBody());
			result.put("daikou", response2);

		} else {
			result.put("status", 1002);
		}

		result.put("body", data);
		return Utility.gson.toJson(result);
	}
}
