package com.park.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoMycarParkingConfigQueryRequest;
import com.alipay.api.request.AlipayEcoMycarParkingConfigSetRequest;
import com.alipay.api.request.AlipayEcoMycarParkingEnterinfoSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingExitinfoSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderSyncRequest;
import com.alipay.api.request.AlipayEcoMycarParkingOrderUpdateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoCreateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingParkinglotinfoUpdateRequest;
import com.alipay.api.request.AlipayEcoMycarParkingVehicleQueryRequest;
import com.alipay.api.response.AlipayEcoMycarParkingConfigQueryResponse;
import com.alipay.api.response.AlipayEcoMycarParkingConfigSetResponse;
import com.alipay.api.response.AlipayEcoMycarParkingEnterinfoSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingExitinfoSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderSyncResponse;
import com.alipay.api.response.AlipayEcoMycarParkingOrderUpdateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoCreateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingParkinglotinfoUpdateResponse;
import com.alipay.api.response.AlipayEcoMycarParkingVehicleQueryResponse;
import com.park.model.Constants;
@Service
public class AliParkFeeService {
	public String APP_PRIVATE_KEY=Constants.alipayPrivateKey5;
	public String ALIPAY_PUBLIC_KEY=Constants.alipayPublicKey5;
	String URL = "https://openapi.alipay.com/gateway.do";
	String APP_ID = Constants.alipayAppId5;
	String FORMAT = "json";
	String CHARSET = "UTF-8";
	String SIGN_TYPE = "RSA";
	AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET,
			ALIPAY_PUBLIC_KEY, SIGN_TYPE);
	//停车ISV系统配置
	public AlipayEcoMycarParkingConfigSetResponse parkConfigSet(Map<String, String> args) throws AlipayApiException{
		String merchant_name=args.get("merchant_name");
		String merchant_service_phone=args.get("merchant_service_phone");
		String account_no=args.get("account_no");
		String interface_url=args.get("interface_url");
		AlipayEcoMycarParkingConfigSetRequest request = new AlipayEcoMycarParkingConfigSetRequest();
		request.setBizContent("{" +
				"\"merchant_name\":\""
				+ merchant_name
				+ "\"," +
				"\"merchant_service_phone\":\""
				+ merchant_service_phone
				+ "\"," +
				"\"account_no\":\""
				+ account_no
				+ "\"," +
				"      \"interface_info_list\":[{" +
				"        \"interface_name\":\"alipay.eco.mycar.parking.userpage.query\"," +
				"\"interface_type\":\"interface_page\"," +
				"\"interface_url\":\""
				+ interface_url
				+ "\"" +
				"        }]" +
				"  }");
		AlipayEcoMycarParkingConfigSetResponse response = alipayClient.execute(request);
		return response;
	}
	//停车ISV系统配置查询
	public AlipayEcoMycarParkingConfigQueryResponse parkConfigQuery(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingConfigQueryRequest request = new AlipayEcoMycarParkingConfigQueryRequest();
		request.setBizContent("{" +
				"\"interface_name\":\"alipay.eco.mycar.parking.userpage.query\"," +
				"\"interface_type\":\"interface_page\"" +
				"  }");
		AlipayEcoMycarParkingConfigQueryResponse response = alipayClient.execute(request);
		return response;		
	}
	//订单同步
	public AlipayEcoMycarParkingOrderSyncResponse parkingOrderSync(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingOrderSyncRequest request = new AlipayEcoMycarParkingOrderSyncRequest();
		String user_id=args.get("user_id");
		String out_parking_id=args.get("out_parking_id");
		String parking_name=args.get("parking_name");
		String car_number=args.get("car_number");
		String out_order_no=args.get("out_order_no");
		String order_status=args.get("order_status");
		String order_time=args.get("order_time");
		String order_no=args.get("order_no");
		String pay_time=args.get("pay_time");
		String pay_type=args.get("pay_type");
		String pay_money=args.get("pay_money");
		String in_time=args.get("in_time");
		String parking_id=args.get("parking_id");
		String in_duration=args.get("in_duration");
		String card_number=args.get("card_number");
		request.setBizContent("{" +
				"\"user_id\":\""
				+ user_id
				+ "\"," +
				"\"out_parking_id\":\""
				+ out_parking_id
				+ "\"," +
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"car_number\":\""
				+ car_number
				+ "\"," +
				"\"out_order_no\":\""
				+ out_order_no
				+ "\"," +
				"\"order_status\":\""
				+ order_status
				+ "\"," +
				"\"order_time\":\""
				+ order_time
				+ "\"," +
				"\"order_no\":\""
				+ order_no
				+ "\"," +
				"\"pay_time\":\""
				+ pay_time
				+ "\"," +
				"\"pay_type\":\""
				+ pay_type
				+ "\"," +
				"\"pay_money\":\""
				+ pay_money
				+ "\"," +
				"\"in_time\":\""
				+ in_time
				+ "\"," +
				"\"parking_id\":\""
				+ parking_id
				+ "\"," +
				"\"in_duration\":\""
				+ in_duration
				+ "\"," +
				"\"card_number\":\""
				+ card_number
				+ "\"" +
				"  }");
				AlipayEcoMycarParkingOrderSyncResponse response = alipayClient.execute(request);
				return response;	
	}
	//订单更新
	public AlipayEcoMycarParkingOrderUpdateResponse parkingOrderUpdate(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingOrderUpdateRequest request = new AlipayEcoMycarParkingOrderUpdateRequest();
		String user_id=args.get("user_id");
		String order_no=args.get("order_no");
		String order_status=args.get("order_status");
		request.setBizContent("{" +
		"\"user_id\":\""
		+ user_id
		+ "\"," +
		"\"order_no\":\""
		+ order_no
		+ "\"," +
		"\"order_status\":\""
		+ order_status
		+ "\"" +
		"  }");
		AlipayEcoMycarParkingOrderUpdateResponse response = alipayClient.execute(request);
		return response;
	}
	//车辆入场
	public AlipayEcoMycarParkingEnterinfoSyncResponse  parkingEnterinfoSync(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingEnterinfoSyncRequest request = new AlipayEcoMycarParkingEnterinfoSyncRequest();
		String parking_id=args.get("parking_id");
		String car_number=args.get("car_number");
		String in_time=args.get("in_time");
		request.setBizContent("{" +
				"\"parking_id\":\""
				+ parking_id
				+ "\"," +
				"\"car_number\":\""
				+ car_number
				+ "\"," +
				"\"in_time\":\""
				+ in_time
				+ "\"" +
				"  }");
				AlipayEcoMycarParkingEnterinfoSyncResponse response = alipayClient.execute(request);
				return response;
	}
	//车辆出场
	public AlipayEcoMycarParkingExitinfoSyncResponse parkingExitinfoSync(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingExitinfoSyncRequest request = new AlipayEcoMycarParkingExitinfoSyncRequest();
		String parking_id=args.get("parking_id");
		String car_number=args.get("car_number");
		String in_time=args.get("out_time");
		request.setBizContent("{" +
				"\"parking_id\":\""
				+ parking_id
				+ "\"," +
				"\"car_number\":\""
				+ car_number
				+ "\"," +
				"\"out_time\":\""
				+ in_time
				+ "\"" +
				"  }");
		AlipayEcoMycarParkingExitinfoSyncResponse response = alipayClient.execute(request);
		return response;
	}
	//车牌信息查询
	public AlipayEcoMycarParkingVehicleQueryResponse parkingCardNumberQuery(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingVehicleQueryRequest request = new AlipayEcoMycarParkingVehicleQueryRequest();
		String car_id=args.get("car_id");
		request.setBizContent("{" +
		"\"car_id\":\""
		+ car_id
		+ "\"" +
		"  }");
		String accessToken="";
		AlipayEcoMycarParkingVehicleQueryResponse response = alipayClient.execute(request,accessToken);
		return response;
	}
	//停车场信息录入
	public AlipayEcoMycarParkingParkinglotinfoCreateResponse parkingInfoCreate(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingParkinglotinfoCreateRequest request = new AlipayEcoMycarParkingParkinglotinfoCreateRequest();
		String city_id=args.get("city_id");
		String equipment_name=args.get("equipment_name");
		String out_parking_id=args.get("out_parking_id");
		String parking_address=args.get("parking_address");
		String longitude=args.get("longitude");
		String latitude=args.get("latitude");
		String parking_start_time=args.get("parking_start_time");
		String parking_end_time=args.get("parking_end_time");
		String parking_number=args.get("parking_number");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_type=args.get("parking_type");
		String payment_mode=args.get("payment_mode");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String contact_name=args.get("contact_name");
		String contact_mobile=args.get("contact_mobile");
		String parking_name=args.get("parking_name");
		request.setBizContent("{" +
				"\"city_id\":\""
				+ city_id
				+ "\"," +
				"\"equipment_name\":\""
				+ equipment_name
				+ "\"," +
				"\"out_parking_id\":\""
				+ out_parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"longitude\":\""
				+ longitude
				+ "\"," +
				"\"latitude\":\""
				+ latitude
				+ "\"," +
				"\"parking_start_time\":\""
				+ parking_start_time
				+ "\"," +
				"\"parking_end_time\":\""
				+ parking_end_time
				+ "\"," +
				"\"parking_number\":\""
				+ parking_number
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_type\":\""
				+ parking_type
				+ "\"," +
				"\"payment_mode\":\""
				+ payment_mode
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
				"\"contact_name\":\""
				+ contact_name
				+ "\"," +
				"\"contact_mobile\":\""
				+ contact_mobile
				+ "\"," +				
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\"13\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoCreateResponse response = alipayClient.execute(request);
		return response;
	}
	
	//录入停车场信息
	public AlipayEcoMycarParkingParkinglotinfoCreateResponse parkingInfoInput(Map<String, String> args) throws AlipayApiException {
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
		return response;
	}
	
	//录入停车场信息
		public AlipayEcoMycarParkingParkinglotinfoUpdateResponse  parkingInfoUpdate2(Map<String, String> args) throws AlipayApiException {
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
			return response;
		}
	
	//停车场信息修改
	public AlipayEcoMycarParkingParkinglotinfoUpdateResponse parkingInfoUpdate(Map<String, String> args) throws AlipayApiException{
		AlipayEcoMycarParkingParkinglotinfoUpdateRequest request = new AlipayEcoMycarParkingParkinglotinfoUpdateRequest();
		String city_id=args.get("city_id");
		String equipment_name=args.get("equipment_name");
		String out_parking_id=args.get("out_parking_id");
		String parking_address=args.get("parking_address");
		String longitude=args.get("longitude");
		String latitude=args.get("latitude");
		String parking_start_time=args.get("parking_start_time");
		String parking_end_time=args.get("parking_end_time");
		String parking_number=args.get("parking_number");
		String parking_lot_type=args.get("parking_lot_type");
		String parking_type=args.get("parking_type");
		String payment_mode=args.get("payment_mode");
		String pay_type=args.get("pay_type");
		String shopingmall_id=args.get("shopingmall_id");
		String parking_fee_description=args.get("parking_fee_description");
		String contact_name=args.get("contact_name");
		String contact_mobile=args.get("contact_mobile");
		String parking_name=args.get("parking_name");
		request.setBizContent("{" +
				"\"city_id\":\""
				+ city_id
				+ "\"," +
				"\"equipment_name\":\""
				+ equipment_name
				+ "\"," +
				"\"out_parking_id\":\""
				+ out_parking_id
				+ "\"," +
				"\"parking_address\":\""
				+ parking_address
				+ "\"," +
				"\"longitude\":\""
				+ longitude
				+ "\"," +
				"\"latitude\":\""
				+ latitude
				+ "\"," +
				"\"parking_start_time\":\""
				+ parking_start_time
				+ "\"," +
				"\"parking_end_time\":\""
				+ parking_end_time
				+ "\"," +
				"\"parking_number\":\""
				+ parking_number
				+ "\"," +
				"\"parking_lot_type\":\""
				+ parking_lot_type
				+ "\"," +
				"\"parking_type\":\""
				+ parking_type
				+ "\"," +
				"\"payment_mode\":\""
				+ payment_mode
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
				"\"contact_name\":\""
				+ contact_name
				+ "\"," +
				"\"contact_mobile\":\""
				+ contact_mobile
				+ "\"," +				
				"\"parking_name\":\""
				+ parking_name
				+ "\"," +
				"\"time_out\":\"13\"" +
				"  }");
		AlipayEcoMycarParkingParkinglotinfoUpdateResponse response = alipayClient.execute(request);
		return response;
	}
	
}
