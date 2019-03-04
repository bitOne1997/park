package com.park.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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

import com.lechinepay.channel.lepay.client.DefaultLePay;
import com.lechinepay.channel.lepay.client.apppay.AppPay;
import com.lechinepay.channel.lepay.share.LePayParameters;
import com.park.model.Lepayrecord;
import com.park.model.PosChargeData;
import com.park.service.LepayRecordService;
import com.park.service.PosChargeDataService;
import com.park.service.Utility;

@Controller
@RequestMapping("fee")
public class FeeDataController {
private static Log logger = LogFactory.getLog(FeeDataController.class);
@Autowired
LepayRecordService lepayRecord;
@Autowired
PosChargeDataService poschargedataService;
@RequestMapping(value="/alipaydata/insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
public void alipaydataInsert(HttpServletRequest request) throws IOException {
    String encoding = request.getParameter(LePayParameters.ENCODING);
    if (null == encoding || encoding.trim().isEmpty()) {
        encoding = DefaultLePay.ENCODING_DEFAULT_VALUE;
    }
    request.setCharacterEncoding(encoding);

    Map<String, Object> params = new HashMap<String, Object>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
        String name = (String) iter.next();
        String[] values = (String[]) requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
            valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
        }
        params.put(name, valueStr);
    }	
	Lepayrecord lepayrecord=new Lepayrecord();
	lepayrecord.setPaytype((short) 0);//支付宝为0
	String amount=(String) params.get("amount");
	lepayrecord.setAmount((int) Float.parseFloat(amount));;
	String mchId=(String) params.get("mchId");
	lepayrecord.setMchid(mchId);
	String cmpAppId=(String) params.get("cmpAppId");
	lepayrecord.setCmpappid(cmpAppId);
	
	String[] tmpStr=((String)params.get("outTradeNo")).split(";");
	int poschargeId=Integer.parseInt(tmpStr[1]);
	lepayrecord.setOuttradeno(tmpStr[1]);
	String payTypeTradeNo=(String) params.get("payTypeOrderNo");
	lepayrecord.setPaytypetradeno(payTypeTradeNo);
	String orderNo=(String) params.get("orderNo");	
	lepayrecord.setOrderno(orderNo);
	 if (AppPay.verify(params)) {// 验证成功
		 Lepayrecord lepayrecord2=lepayRecord.getByOutTradeNo(tmpStr[1]);
		 if (lepayrecord2==null) {
			 lepayRecord.insertSelective(lepayrecord);
		}
		 if (!lepayrecord.getOuttradeno().equals("none")) {			
			PosChargeData posChargeData=poschargedataService.getById(poschargeId);
			posChargeData.setPayType(0);
			poschargedataService.update(posChargeData);
		}
     } else {// 验证失败
       
     }
	
	
}
@RequestMapping(value="/wechartdata/insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
public void wechartdataInsert(HttpServletRequest request) throws IOException{
    String encoding = request.getParameter(LePayParameters.ENCODING);
    if (null == encoding || encoding.trim().isEmpty()) {
        encoding = DefaultLePay.ENCODING_DEFAULT_VALUE;
    }
    request.setCharacterEncoding(encoding);
    Map<String, Object> params = new HashMap<String, Object>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
        String name = (String) iter.next();
        String[] values = (String[]) requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
            valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
        }
        params.put(name, valueStr);
    }	
	Lepayrecord lepayrecord=new Lepayrecord();

	lepayrecord.setPaytype((short) 1);//微信为1
	String amount=(String) params.get("amount");
	lepayrecord.setAmount((int) Float.parseFloat(amount));;
	String mchId=(String) params.get("mchId");
	lepayrecord.setMchid(mchId);
	String cmpAppId=(String) params.get("cmpAppId");
	lepayrecord.setCmpappid(cmpAppId);
	String[] tmpStr=((String)params.get("outTradeNo")).split(";");
	int poschargeId=Integer.parseInt(tmpStr[1]);
	lepayrecord.setOuttradeno(tmpStr[1]);
	String payTypeTradeNo=(String) params.get("payTypeOrderNo");
	lepayrecord.setPaytypetradeno(payTypeTradeNo);
	String orderNo=(String) params.get("orderNo");
	lepayrecord.setOrderno(orderNo);
	
	 if (AppPay.verify(params)) {// 验证成功
		 Lepayrecord lepayrecord2=lepayRecord.getByOutTradeNo(tmpStr[1]);
		 if (lepayrecord2==null) {
			 lepayRecord.insertSelective(lepayrecord);
		}	
		 if (!lepayrecord.getOuttradeno().equals("none")) {			   	
				PosChargeData posChargeData=poschargedataService.getById(poschargeId);
				posChargeData.setPayType(1);
				poschargedataService.update(posChargeData);
			}
     } else {// 验证失败
       
     }
}

@RequestMapping(value="/quickpaydata/insert",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
public void quickpaydataInsert(HttpServletRequest request) throws IOException {
	 String encoding = request.getParameter(LePayParameters.ENCODING);
	    if (null == encoding || encoding.trim().isEmpty()) {
	        encoding = DefaultLePay.ENCODING_DEFAULT_VALUE;
	    }
	    request.setCharacterEncoding(encoding);

	    Map<String, Object> params = new HashMap<String, Object>();
	    Map requestParams = request.getParameterMap();
	    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	        String name = (String) iter.next();
	        String[] values = (String[]) requestParams.get(name);
	        String valueStr = "";
	        for (int i = 0; i < values.length; i++) {
	            valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
	        }
	        params.put(name, valueStr);
	    }	
		Lepayrecord lepayrecord=new Lepayrecord();
		lepayrecord.setPaytype((short) 2);//2为快捷支付
		String amount=(String) params.get("amount");
		lepayrecord.setAmount((int) Float.parseFloat(amount));;
		String mchId=(String) params.get("mchId");
		lepayrecord.setMchid(mchId);
		String cmpAppId=(String) params.get("cmpAppId");
		lepayrecord.setCmpappid(cmpAppId);
		
		String[] tmpStr=((String)params.get("outTradeNo")).split(";");
		int poschargeId=Integer.parseInt(tmpStr[1]);
		lepayrecord.setOuttradeno(tmpStr[1]);
		String payTypeTradeNo=(String) params.get("payTypeOrderNo");
		lepayrecord.setPaytypetradeno(payTypeTradeNo);
		String orderNo=(String) params.get("orderNo");	
		lepayrecord.setOrderno(orderNo);
		 if (AppPay.verify(params)) {// 验证成功
			 Lepayrecord lepayrecord2=lepayRecord.getByOutTradeNo(tmpStr[1]);
			 if (lepayrecord2==null) {
				 lepayRecord.insertSelective(lepayrecord);
			}
			 if (!lepayrecord.getOuttradeno().equals("none")) {			
				PosChargeData posChargeData=poschargedataService.getById(poschargeId);
				posChargeData.setPayType(0);
				poschargedataService.update(posChargeData);
			}
	     } else {// 验证失败
	       
	     }
	
}
}
