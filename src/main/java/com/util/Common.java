package com.util;

public class Common {
	public static String serverIp = "112.74.109.240:8888";
	public static String getDataDetailUrlAbs = "http://%s/park/pos/charge/getParkByMoney";
	public static String getDataDetailUrl = String.format(getDataDetailUrlAbs, serverIp);

}