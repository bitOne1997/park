package com.park.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoCreateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoUpdateRequest;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoCreateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoUpdateResponse;
import com.park.model.Constants;
import com.park.service.Utility;

@Controller
@RequestMapping("registerToAlipay")
public class ParkRegisterToAlipayController {
	String URL = "https://openapi.alipay.com/gateway.do";
	String FORMAT = "json";
	String CHARSET = "UTF-8";
	String SIGN_TYPE = "RSA";
	@RequestMapping(value = "parkinfoInputBb", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoInputBb(@RequestBody Map<String, String> args) throws AlipayApiException {		
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Constants.alipayAppId5, Constants.alipayPrivateKey5, FORMAT, CHARSET,
				Constants.alipayPublicKey5, SIGN_TYPE);
		AlipayEcoMycarParkingParkinglotinfoCreateRequest request = new AlipayEcoMycarParkingParkinglotinfoCreateRequest();
		String out_parking_id=args.get("out_parking_id");
		String parking_address=args.get("parking_address");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_poiid=args.get("parking_poiid");
		String parking_mobile=args.get("parking_mobile");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String parking_name	=args.get("parking_name");
		String time_out	=args.get("time_out");
		String agent_id	=args.get("agent_id");
		String mchnt_id	=args.get("mchnt_id");
		request.setBizContent("{" +
				"\"out_parking_id\":\""
				+ out_parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_poiid\":\""
				+ parking_poiid
				+ "\"," +
				"\"parking_mobile\":\""
				+ parking_mobile
				+ "\"," +
				"\"pay_type\":\""
				+ pay_type
				+ "\"," +
				"\"shopingmall_id\":\""
				+ shopingmall_id
				+ "\"," +
				"\"parking_fee_description\":\""
				+ parking_fee_description
				+ "\"," +
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\""
				+ time_out
				+ "\"," +
				"\"agent_id\":\""
				+ agent_id
				+ "\"," +
				"\"mchnt_id\":\""
				+ mchnt_id
				+ "\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoCreateResponse response = alipayClient.execute(request);
		return Utility.createJsonMsg("1001", "success",response);
	}
	@RequestMapping(value = "parkinfoUpdateBb", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoUpdateBb(@RequestBody Map<String, String> args) throws AlipayApiException {		
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Constants.alipayAppId5, Constants.alipayPrivateKey5, FORMAT, CHARSET,
				Constants.alipayPublicKey5, SIGN_TYPE);
		AlipayEcoMycarParkingParkinglotinfoUpdateRequest  request = new AlipayEcoMycarParkingParkinglotinfoUpdateRequest ();
		String parking_id=args.get("parking_id");
		String parking_address=args.get("parking_address");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_poiid=args.get("parking_poiid");
		String parking_mobile=args.get("parking_mobile");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String parking_name	=args.get("parking_name");
		String time_out	=args.get("time_out");
		String agent_id	=args.get("agent_id");
		String mchnt_id	=args.get("mchnt_id");
		request.setBizContent("{" +
				"\"parking_id\":\""
				+ parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_poiid\":\""
				+ parking_poiid
				+ "\"," +
				"\"parking_mobile\":\""
				+ parking_mobile
				+ "\"," +
				"\"pay_type\":\""
				+ pay_type
				+ "\"," +
				"\"shopingmall_id\":\""
				+ shopingmall_id
				+ "\"," +
				"\"parking_fee_description\":\""
				+ parking_fee_description
				+ "\"," +
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\""
				+ time_out
				+ "\"," +
				"\"agent_id\":\""
				+ agent_id
				+ "\"," +
				"\"mchnt_id\":\""
				+ mchnt_id
				+ "\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoUpdateResponse response = alipayClient.execute(request);
		return Utility.createJsonMsg("1001", "success",response);
	}
	@RequestMapping(value = "parkinfoInputJbt", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoInputJbt(@RequestBody Map<String, String> args) throws AlipayApiException {
		
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Constants.alipayAppId, Constants.alipayPrivateKey, FORMAT, CHARSET,
				Constants.alipayPublicKey, SIGN_TYPE);
		AlipayEcoMycarParkingParkinglotinfoCreateRequest request = new AlipayEcoMycarParkingParkinglotinfoCreateRequest();
		String out_parking_id=args.get("out_parking_id");
		String parking_address=args.get("parking_address");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_poiid=args.get("parking_poiid");
		String parking_mobile=args.get("parking_mobile");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String parking_name	=args.get("parking_name");
		String time_out	=args.get("time_out");
		String agent_id	=args.get("agent_id");
		String mchnt_id	=args.get("mchnt_id");
		request.setBizContent("{" +
				"\"out_parking_id\":\""
				+ out_parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_poiid\":\""
				+ parking_poiid
				+ "\"," +
				"\"parking_mobile\":\""
				+ parking_mobile
				+ "\"," +
				"\"pay_type\":\""
				+ pay_type
				+ "\"," +
				"\"shopingmall_id\":\""
				+ shopingmall_id
				+ "\"," +
				"\"parking_fee_description\":\""
				+ parking_fee_description
				+ "\"," +
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\""
				+ time_out
				+ "\"," +
				"\"agent_id\":\""
				+ agent_id
				+ "\"," +
				"\"mchnt_id\":\""
				+ mchnt_id
				+ "\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoCreateResponse response = alipayClient.execute(request);
		return Utility.createJsonMsg("1001", "success",response);
	}
	@RequestMapping(value = "parkinfoUpdateJbt", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@ResponseBody
	public String parkinfoUpdateJbt(@RequestBody Map<String, String> args) throws AlipayApiException {		
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Constants.alipayAppId, Constants.alipayPrivateKey, FORMAT, CHARSET,
				Constants.alipayPublicKey, SIGN_TYPE);
		AlipayEcoMycarParkingParkinglotinfoUpdateRequest  request = new AlipayEcoMycarParkingParkinglotinfoUpdateRequest ();
		String parking_id=args.get("parking_id");
		String parking_address=args.get("parking_address");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_poiid=args.get("parking_poiid");
		String parking_mobile=args.get("parking_mobile");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String parking_name	=args.get("parking_name");
		String time_out	=args.get("time_out");
		String agent_id	=args.get("agent_id");
		String mchnt_id	=args.get("mchnt_id");
		request.setBizContent("{" +
				"\"parking_id\":\""
				+ parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_poiid\":\""
				+ parking_poiid
				+ "\"," +
				"\"parking_mobile\":\""
				+ parking_mobile
				+ "\"," +
				"\"pay_type\":\""
				+ pay_type
				+ "\"," +
				"\"shopingmall_id\":\""
				+ shopingmall_id
				+ "\"," +
				"\"parking_fee_description\":\""
				+ parking_fee_description
				+ "\"," +
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\""
				+ time_out
				+ "\"," +
				"\"agent_id\":\""
				+ agent_id
				+ "\"," +
				"\"mchnt_id\":\""
				+ mchnt_id
				+ "\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoUpdateResponse response = alipayClient.execute(request);
		return Utility.createJsonMsg("1001", "success",response);
	}
}
