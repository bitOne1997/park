package com.park.service;
import com.lepay.util.ConfigInfo;
import com.lepay.util.ExcuteRequestUtil;
import com.lepay.util.InitUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lechinepay.channel.lepay.share.LePayParameters;
public class LepayService {
public Map<String, Object> saomaPay(Long amount,int type){
	 InitUtil.init();
     /*** 商户接入参数 ***/
     String version = "1.0.0";
     String encoding = "UTF-8";
     String signature = "";
     String reqReserved = "九比特测试请求";

     // String mchId = "23501";
     // String cmpAppId = "34505";
     String mchId = "35503";
     String cmpAppId = "38501";
     String payTypeCode = "PT0007_MS";
     if (type==1) {
    	 payTypeCode="PT0009_MS";
	}

     String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
     String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
  //   String amount = "1";
     String summary = "九比特停车";
     String summaryDetail = "九比特停车";
     String deviceId = "L897554536354";
     String deviceIp = "127.0.0.1";

     /*** 组装请求报文 ***/
     Map<String, Object> requestMap = new HashMap<String, Object>();

     requestMap.put(LePayParameters.VERSION, version);
     requestMap.put(LePayParameters.ENCODING, encoding);
     requestMap.put(LePayParameters.SIGNATURE, signature);
     requestMap.put(LePayParameters.REQ_RESERVED, reqReserved);
     requestMap.put(LePayParameters.MCH_ID, mchId);
     requestMap.put(LePayParameters.CMP_APP_ID, cmpAppId);
     requestMap.put(LePayParameters.PAY_TYPE_CODE, payTypeCode);
     requestMap.put(LePayParameters.OUT_TRADE_NO, outTradeNo);
     requestMap.put(LePayParameters.TRADE_TIME, tradeTime);
     requestMap.put(LePayParameters.AMOUNT, amount);
     requestMap.put(LePayParameters.SUMMARY, summary);
     requestMap.put(LePayParameters.SUMMARY_DETAIL, summaryDetail);
     requestMap.put(LePayParameters.DEVICE_ID, deviceId);
     requestMap.put(LePayParameters.DEVICE_IP, deviceIp);

     Map<String, Object> responseMap = ExcuteRequestUtil.excute(
             /* ConfigInfo.server_location */"https://openapi.unionpay95516.cc/pre.lepay.api", ConfigInfo.short_url_pay,
             requestMap);
     System.err.println(responseMap);
     return responseMap;
}
public Map<String, Object> saomaPayByPosdataId(Long amount,int type,int posdataId){
	 InitUtil.init();
    /*** 商户接入参数 ***/
    String version = "1.0.0";
    String encoding = "UTF-8";
    String signature = "";
    String reqReserved = "九比特测试请求";

    // String mchId = "23501";
    // String cmpAppId = "34505";
    String mchId = "35503";
    String cmpAppId = "38501";
    String payTypeCode = "PT0007_MS";
    if (type==1) {
   	 payTypeCode="PT0009_MS";
	}

    String outTradeNo = new Date().getTime()+";"+String.valueOf(posdataId);
    String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
 //   String amount = "1";
    String summary = "九比特停车";
    String summaryDetail = "九比特停车";
    String deviceId = "L897554536354";
    String deviceIp = "127.0.0.1";

    /*** 组装请求报文 ***/
    Map<String, Object> requestMap = new HashMap<String, Object>();

    requestMap.put(LePayParameters.VERSION, version);
    requestMap.put(LePayParameters.ENCODING, encoding);
    requestMap.put(LePayParameters.SIGNATURE, signature);
    requestMap.put(LePayParameters.REQ_RESERVED, reqReserved);
    requestMap.put(LePayParameters.MCH_ID, mchId);
    requestMap.put(LePayParameters.CMP_APP_ID, cmpAppId);
    requestMap.put(LePayParameters.PAY_TYPE_CODE, payTypeCode);
    requestMap.put(LePayParameters.OUT_TRADE_NO, outTradeNo);
    requestMap.put(LePayParameters.TRADE_TIME, tradeTime);
    requestMap.put(LePayParameters.AMOUNT, amount);
    requestMap.put(LePayParameters.SUMMARY, summary);
    requestMap.put(LePayParameters.SUMMARY_DETAIL, summaryDetail);
    requestMap.put(LePayParameters.DEVICE_ID, deviceId);
    requestMap.put(LePayParameters.DEVICE_IP, deviceIp);
   
    Map<String, Object> responseMap = ExcuteRequestUtil.excute(
            /* ConfigInfo.server_location */"https://openapi.unionpay95516.cc/pre.lepay.api", ConfigInfo.short_url_pay,
            requestMap);
    System.err.println(responseMap);
    return responseMap;
}

public Map<String, Object> quickPay(Long amount){
	 InitUtil.init();
    /*** 商户接入参数 ***/
    String version = "1.0.0";
    String encoding = "UTF-8";
    String signature = "";
    String reqReserved = "九比特测试请求";

    // String mchId = "23501";
    // String cmpAppId = "34505";
    String mchId = "35503";
    String cmpAppId = "38501";
    String payTypeCode = "quick.h5pay";
   

    String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
 //   String amount = "1";
    String summary = "九比特停车";
    String summaryDetail = "九比特停车";
    String deviceId = "L897554536354";
    String deviceIp = "127.0.0.1";

    /*** 组装请求报文 ***/
    Map<String, Object> requestMap = new HashMap<String, Object>();

    requestMap.put(LePayParameters.VERSION, version);
    requestMap.put(LePayParameters.ENCODING, encoding);
    requestMap.put(LePayParameters.SIGNATURE, signature);
    requestMap.put(LePayParameters.REQ_RESERVED, reqReserved);
    requestMap.put(LePayParameters.MCH_ID, mchId);
    requestMap.put(LePayParameters.CMP_APP_ID, cmpAppId);
    requestMap.put(LePayParameters.PAY_TYPE_CODE, payTypeCode);
    requestMap.put(LePayParameters.OUT_TRADE_NO, outTradeNo);
    requestMap.put(LePayParameters.TRADE_TIME, tradeTime);
    requestMap.put(LePayParameters.AMOUNT, amount);
    requestMap.put(LePayParameters.SUMMARY, summary);
    requestMap.put(LePayParameters.SUMMARY_DETAIL, summaryDetail);
    requestMap.put(LePayParameters.DEVICE_ID, deviceId);
    requestMap.put(LePayParameters.DEVICE_IP, deviceIp);

    Map<String, Object> responseMap = ExcuteRequestUtil.excute(
            /* ConfigInfo.server_location */"https://openapi.unionpay95516.cc/pre.lepay.api", ConfigInfo.short_url_pay,
            requestMap);
    System.err.println(responseMap);
    return responseMap;
}
}
