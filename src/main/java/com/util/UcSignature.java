package com.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class UcSignature {

    /**
     * @param code      用户账号
     * @param timestamp 时间戳 yyyyMMddHHmmss
     * @param token     令牌
     * @return
     */
    public static String signature(final String code, final String timestamp, final String token) {
        String[] array = new String[]{code, timestamp, token};
        Arrays.sort(array);
        StringBuilder builder = new StringBuilder();
        for (String string : array) {
            builder.append(string);
        }
        return VerifyUtil.encodeBySHA1(builder.toString());
    }

    /**
     * 根据请求参数生成签名
     *
     * @param params
     * @return
     */
    public static String signature(Map<String, String> params, String key) {
        // 组装签名串
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value != null && !"".equals(value)) {
                stringBuilder.append(entry.getKey()).append("=").append(value).append("&");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("key=").append(key);
        }
        String stringSignTemp = stringBuilder.toString();
        return VerifyUtil.encodeByMD5(stringSignTemp).toUpperCase();
    }

    public static boolean verifySignature(Map<String, String> params, String key, String msgSign) {
        params.remove("msgSign");
        // 对参数进行排序
        Map<String, String> sortParams = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String key1, String key2) {
                return key1.compareTo(key2);
            }
        });
        sortParams.putAll(params);
        return msgSign.equals(signature(sortParams, key));
    }

    public static void main(String[] args) {
        System.out.println(UcSignature.signature("13588471829", "20160822125025", "f3c74e8782b54bb78d436e7472a11cb0"));
    }
}
