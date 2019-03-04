package com.park.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.park.dao.GongzxrecordMapper;
import com.park.model.Constants;
import com.park.model.GongzxRecord;
import com.park.model.Gongzxrecord2;
import com.park.model.Monthuser;
import com.park.model.Njcarfeerecord;
import com.park.model.Njmonthuser;
import com.park.service.AliParkFeeService;
import com.park.service.GongzxRecordService;
import com.park.service.NjCarFeeRecordService;
import com.park.service.NjMonthUserService;
import com.park.service.Utility;



@Controller
@RequestMapping("njpark")
public class NjparkController {
	@Autowired
	NjMonthUserService njMonthUserService;
	@Autowired
	NjCarFeeRecordService njcarFeeRecordService;
	@Autowired
	AliParkFeeService parkFeeService;
	@Autowired
	GongzxRecordService gongzxRecordService;
	@Autowired
	GongzxrecordMapper gongzxrecordMapper;
	
	private static Log logger = LogFactory.getLog(NjparkController.class);
	
	@RequestMapping(value="carArrive",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String carArrive(@RequestBody Map<String, Object> args) throws ParseException{	
		logger.info("carArrive:"+args.toString());
		Njcarfeerecord njcarfeerecord=new Njcarfeerecord();
		
		GongzxRecord gongzxRecord=new GongzxRecord();
		Gongzxrecord2 gongzxrecord2=new Gongzxrecord2();
		
		if (args.get("tradeDate")!=null) {
			try {
				njcarfeerecord.setTradedate(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("tradeDate")));				

			} catch (Exception e) {
				njcarfeerecord.setTradedate(new SimpleDateFormat("yyyyMMddHHmmss").parse((String) args.get("tradeDate")));
			}
			
		}
		else{
			njcarfeerecord.setTradedate(new Date());
		}
		
		njcarfeerecord.setCarnumber((String) args.get("carNumber"));
		gongzxRecord.setCarNumber(njcarfeerecord.getCarnumber());
		gongzxrecord2.setCarnumber(njcarfeerecord.getCarnumber());
		njcarfeerecord.setCartype(args.get("carType")!=null?(String) args.get("carType"):"");
		
		try {
			njcarfeerecord.setArrivetime(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("arriveTime")));
		} catch (Exception e) {
			// TODO: handle exception
			njcarfeerecord.setArrivetime(new SimpleDateFormat("yyyyMMddHHmmss").parse((String) args.get("arriveTime")));
		}
		
		gongzxRecord.setArriveTime(njcarfeerecord.getArrivetime());
		gongzxrecord2.setArrivetime(gongzxRecord.getArriveTime());
		njcarfeerecord.setParkid((int)args.get("parkId"));
		
		gongzxRecord.setParkId(njcarfeerecord.getParkid());
		gongzxrecord2.setParkid(njcarfeerecord.getParkid());
		
		njcarfeerecord.setParkname(args.get("parkName")!=null?(String) args.get("parkName"):"");
		gongzxRecord.setParkName(njcarfeerecord.getParkname());
		gongzxrecord2.setParkname(njcarfeerecord.getParkname());
		
		njcarfeerecord.setTradenumber(args.get("tradeNumber")!=null?(String) args.get("tradeNumber"):"");
		gongzxRecord.setTradeNumber(njcarfeerecord.getTradenumber());
		gongzxrecord2.setTradenumber(njcarfeerecord.getTradenumber());
		
		njcarfeerecord.setStoptype(args.get("stopType")!=null?(String) args.get("stopType"):"");
		gongzxRecord.setStopType(njcarfeerecord.getStoptype());
		gongzxrecord2.setStoptype(njcarfeerecord.getStoptype());
		
		njcarfeerecord.setPaidmoney(args.get("paidMoney")!=null?(int)args.get("paidMoney"):0);
		gongzxRecord.setRealPay(Double.valueOf(String.valueOf(njcarfeerecord.getPaidmoney())));
		gongzxrecord2.setRealpay(Double.valueOf(String.valueOf(njcarfeerecord.getPaidmoney())));
		
		njcarfeerecord.setPicturepath(args.get("picturePath")!=null?(String) args.get("picturePath"):"");
		gongzxRecord.setPicturePath(njcarfeerecord.getPicturepath());
		gongzxrecord2.setPicturepath(njcarfeerecord.getPicturepath());
		
		int num=njcarFeeRecordService.insertSelective(njcarfeerecord);
		
		
		logger.info("test:"+gongzxrecord2);
		gongzxrecordMapper.insertSelective(gongzxrecord2);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		if (num==1) {
			return Utility.createJsonMsg(1001, "success");
		}
		else{
			return Utility.createJsonMsg(1002, "failed");
		}
	}
	@RequestMapping(value="carLeave",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String carLeave(@RequestBody Map<String, Object> args) throws ParseException{
		logger.info("carleave:"+args.toString());
		Njcarfeerecord njcarfeerecord=new Njcarfeerecord();
		Gongzxrecord2 gongzxRecord2=new Gongzxrecord2();
	//	njcarfeerecord.setTradedate(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("tradeDate")));
		njcarfeerecord.setCarnumber((String) args.get("carNumber"));
		gongzxRecord2.setCarnumber(njcarfeerecord.getCarnumber());
		
		logger.info((String) args.get("leaveTime"));
		njcarfeerecord.setCartype(args.get("carType")!=null?(String) args.get("carType"):"");
		
		try {
			njcarfeerecord.setArrivetime(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("arriveTime")));
		} catch (Exception e) {
			// TODO: handle exception
		//	njcarfeerecord.setArrivetime(new SimpleDateFormat("yyyyMMddHHmmss").parse((String) args.get("arriveTime")));
		}
		
		njcarfeerecord.setLeavetime(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("leaveTime")));
		
		njcarfeerecord.setParkid((int)args.get("parkId"));
		njcarfeerecord.setParkname(args.get("parkName")!=null?(String) args.get("parkName"):"");
		njcarfeerecord.setTradenumber((String) args.get("tradeNumber")!=null?(String) args.get("tradeNumber"):"");
		njcarfeerecord.setStoptype((String) args.get("stopType")!=null?(String) args.get("stopType"):"");
		njcarfeerecord.setPaidmoney(args.get("realPay")!=null?(int)args.get("realPay"):0);
		njcarfeerecord.setPicturepath(args.get("picturePath")!=null?(String) args.get("picturePath"):"");
		
		gongzxRecord2.setParkid(njcarfeerecord.getParkid());
		gongzxRecord2.setArrivetime(njcarfeerecord.getArrivetime());
		gongzxRecord2.setLeavetime(njcarfeerecord.getLeavetime());
		gongzxRecord2.setParkname(njcarfeerecord.getParkname());
		gongzxRecord2.setStoptype(njcarfeerecord.getStoptype());
		gongzxRecord2.setRealpay(Double.valueOf(String.valueOf(njcarfeerecord.getPaidmoney())));
		gongzxRecord2.setPicturepath(njcarfeerecord.getPicturepath());
		
//		gongzxRecordService.insert(gongzxRecord);
	//	logger.info("test:"+gongzxRecord2);
		gongzxrecordMapper.insertSelective(gongzxRecord2);
		
		List<Gongzxrecord2> selectrecord2s=gongzxrecordMapper.selectByTradeNumber(njcarfeerecord.getTradenumber());
		
		if (selectrecord2s.isEmpty()) {
			return Utility.createJsonMsg(1002, "无入场信息");
		}
		
		Gongzxrecord2 selectrecord2=selectrecord2s.get(0);
		selectrecord2.setLeavetime(njcarfeerecord.getLeavetime());
		List<Njcarfeerecord> njcarfeeSelecteds=njcarFeeRecordService.selectByTradeNumber(args.get("tradeNumber")!=null?(String) args.get("tradeNumber"):"");
		if (njcarfeeSelecteds.isEmpty()) {
			return Utility.createJsonMsg(1002, "无入场信息");
		}
		njcarfeerecord=njcarfeeSelecteds.get(0);
		gongzxrecordMapper.updateByPrimaryKey(selectrecord2);
		
		try {
			njcarfeerecord.setLeavetime(new SimpleDateFormat(Constants.DATEFORMAT).parse((String)args.get("leaveTime")));
		} catch (Exception e) {
			// TODO: handle exception
			njcarfeerecord.setLeavetime(new SimpleDateFormat("yyyyMMddHHmmss").parse((String)args.get("leaveTime")));
			
		}
		njcarfeerecord.setShouldcharge(args.get("shouldCharge")!=null?(int) args.get("shouldCharge"):0);
		njcarfeerecord.setArrearspay(args.get("arrearsPay")!=null?(int) args.get("arrearsPay"):0);
		njcarfeerecord.setDiscount(args.get("discount")!=null?(int) args.get("discount"):0);
		njcarfeerecord.setOtherpay(args.get("otherPay")!=null?(int) args.get("otherPay"):0);
		njcarfeerecord.setRealpay(args.get("realPay")!=null?(int) args.get("realPay"):0);
		njcarfeerecord.setInvoiceurl(args.get("invoiceUrl")!=null?(String) args.get("invoiceUrl"):"");
		int num=njcarFeeRecordService.updateByPrimaryKeySelective(njcarfeerecord);
		if (num==1) {
			return Utility.createJsonMsg(1001, "success");
		}
		else {
			return Utility.createJsonMsg(1002, "failed");
		}	
	}
	@RequestMapping(value="monthUserInsert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String monthUserInsert(@RequestBody Map<String, Object> args) throws ParseException{
		Njmonthuser njmonthuser=new Njmonthuser();
		njmonthuser.setCardnumber((String) args.get("cardNumber"));
		njmonthuser.setCarnumber((String) args.get("carNumber"));
		njmonthuser.setMembername((String) args.get("memberName"));
		njmonthuser.setMonthid((String) args.get("monthId"));
		njmonthuser.setCartype((String) args.get("carType"));
		njmonthuser.setMonthtype((String) args.get("monthType"));
		njmonthuser.setTradenumber((String) args.get("tradeNumber"));
		njmonthuser.setTradedate(new SimpleDateFormat(Constants.DATEFORMAT).parse((String)args.get("tradeDate")));
		njmonthuser.setEffectivetimes((String) args.get("effectiveTimes"));
		njmonthuser.setMonthfee((int) args.get("monthFee"));
		njmonthuser.setMonthstart(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("monthStart")));
		njmonthuser.setMonthend(new SimpleDateFormat(Constants.DATEFORMAT).parse((String) args.get("monthEnd")));
		njmonthuser.setRechargebefore((int) args.get("rechargeBefore"));
		njmonthuser.setRechargeafter((int) args.get("rechargeAfter"));
		njmonthuser.setRechargemoney((int) args.get("rechargeMoney"));
		njmonthuser.setRealpay((int) args.get("realPay"));
		njmonthuser.setShouldcharge((int) args.get("shouldCharge"));
		njmonthuser.setDiscount((int) args.get("discount"));
		njmonthuser.setStandardfees((int) args.get("standardFees"));
		njmonthuser.setPreferential((int)args.get("preferential"));
		njmonthuser.setPicturepath((String) args.get("picturePath"));
		njmonthuser.setDescription((String) args.get("description"));
		int num=njMonthUserService.insert(njmonthuser);
		if (num==1) {
			return Utility.createJsonMsg(1001, "success");
		}
		else {
			return Utility.createJsonMsg(1002, "failed");
		}	
	}
	///此为美凯龙的调用接口
	@RequestMapping(value="enterCar",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String carArrivehx(HttpServletRequest request) throws ParseException, UnsupportedEncodingException, AlipayApiException{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String parkKey=request.getParameter("parkKey");
		String carNo=request.getParameter("carNo");
		String orderNo=request.getParameter("orderNo");
		String enterTime=request.getParameter("enterTime");
		String carType=request.getParameter("carType");
		String gateName=request.getParameter("gateName");
		String operatorName=request.getParameter("operatorName");
		
		logger.debug("入场:"+carNo+"key:"+parkKey);
		
		Njcarfeerecord njcarfeerecord=new Njcarfeerecord();
		njcarfeerecord.setArrivetime(new Date());
		njcarfeerecord.setCarnumber(carNo);
		njcarfeerecord.setCartype(carType);
		
		njcarfeerecord.setStoptype("入场");
		
		Map<String, String> args=new HashMap<>();
		
		if (parkKey.equals("c1648ccf33314dc384155896cf4d00b9")) {
			njcarfeerecord.setParkname("红星美凯龙");
			args.put("parking_id", "PI1509946268841654545");
		}
		else if (parkKey.equals("ff8993a40b3a4249924f34044403b5bf")) {
			njcarfeerecord.setParkname("家乐福停车场");
			args.put("parking_id", "PI1501836360593635616");
		}
		
		args.put("parking_id", "PI1509946268841654545");
		args.put("car_number", carNo);
		args.put("in_time", enterTime);
		parkFeeService.parkingEnterinfoSync(args);
		
		njcarfeerecord.setInvoiceurl(enterTime);
		njcarFeeRecordService.insertSelective(njcarfeerecord);
		return Utility.createJsonMsg(1001, "ok");
	}
	///此为美凯龙的调用接口
	@RequestMapping(value="outCar",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String outcar(HttpServletRequest request) throws ParseException{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String carNo=request.getParameter("carNo");
		String orderNo=request.getParameter("orderNo");
		String carType=request.getParameter("carType");
		String outTime=request.getParameter("outTime");
		String gateName=request.getParameter("gateName");
		String operatorName=request.getParameter("operatorName");

		Njcarfeerecord njcarfeerecord=new Njcarfeerecord();
		njcarfeerecord.setArrivetime(new Date());
		njcarfeerecord.setCarnumber(carNo);
		njcarfeerecord.setInvoiceurl(orderNo);
		njcarfeerecord.setCartype(carType);
		njcarfeerecord.setParkname(gateName);
		njcarfeerecord.setStoptype("出场");
	
		njcarfeerecord.setInvoiceurl(outTime);
		njcarFeeRecordService.insertSelective(njcarfeerecord);
		return Utility.createJsonMsg(1001, "ok");
	}
	
	@RequestMapping(value="getFeeT",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getFeeT(@RequestBody Map<String, Object> args) throws UnknownHostException, IOException {
		String ip = "120.25.234.195";
		int port = 9090;
		Socket sck = new Socket(ip, port);
		int parkId=108;
		String cardNumber="川A00011";		
		String content = "{\"parkId\":\""
				+ parkId
				+ "\",\"cardNumber\":\""
				+ cardNumber
				+ "\"}\r\n";
		byte[] bstream = content.getBytes("GBK"); // 转化为字节流
		OutputStream os = sck.getOutputStream(); // 输出流
		os.write(bstream);
		os.flush();
		
		InputStream is = sck.getInputStream();		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
		String info=br.readLine();
		os.close();
		br.close();
		sck.close();
		
		return info;
	}
	
	//获取前多少条记录
	@RequestMapping(value="getByCount",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public String getByCount(@RequestBody Map<String, Object> args){
		int count=(int) args.get("count");
		List<Njcarfeerecord> njcarfeerecords=njcarFeeRecordService.selectByCount(count);
		Map<String, Object> result=new HashMap<>();
		if (!njcarfeerecords.isEmpty()) {
			result.put("status", 1001);
			result.put("body", njcarfeerecords);
		}
		else {
			result.put("status", 1001);
		}
		return Utility.gson.toJson(result);
	}
}
