package com.lepay.util;

import com.lechinepay.channel.lepay.client.apppay.AppPay;

/**
 * 初始化
 * 
 * @author nicholas
 *
 */
public class InitUtil {

    public static void init() {

        String keyStorePassword = "bGl1c2h1aQ==";// 123456的base64编码后的值
        String keyStorePath = "lepay/parklepay.pfx";// 商户私钥证书
        String certificatePath = "lepay/pdtserver.cer";// +Lepay公钥证书

        String keyStoreType = "PKCS12";
        AppPay.init(keyStorePassword, keyStorePath, keyStoreType, certificatePath);
    }
    
    
    public static void initTest() {

        String keyStorePassword = "MTIzNDU2";// 123456的base64编码后的值
        String keyStorePath = "test/testclient.pfx";// 商户私钥证书
        String certificatePath = "test/testserver.cer";// +Lepay公钥证书

        String keyStoreType = "PKCS12";
        AppPay.init(keyStorePassword, keyStorePath, keyStoreType, certificatePath);
    }
}
