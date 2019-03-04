package com.lepay.util;

import java.util.Map;

import com.lechinepay.channel.lepay.client.apppay.AppPay;

/**
 * 发送请求
 * 
 * @author nicholas
 *
 */
public class ExcuteRequestUtil {

    public static Map<String, Object> excute(String serverLocation, String shortUrl, Map<String, Object> requestMap) {
        Map<String, Object> responseMap = AppPay.execute(serverLocation, shortUrl, requestMap);
        return responseMap;
    }
}
