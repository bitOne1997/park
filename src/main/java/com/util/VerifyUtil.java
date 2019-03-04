package com.util;
public class VerifyUtil {

    /**
     * md5加密码
     *
     * @param str
     * @return
     */
    public static String encodeByMD5(String str) {
        return KeyedDigest.getKeyedDigestMD5(str, "");
    }

    /**
     * md5加密码
     *
     * @param str
     * @param key
     * @return
     */
    public static String encodeByMD5(String str, String key) {
        return KeyedDigest.getKeyedDigestMD5(str, key);
    }

    /**
     * sha1加密码
     *
     * @param str
     * @return
     */
    public static String encodeBySHA1(String str) {
        return KeyedDigest.getKeyedDigestSHA1(str, "");
    }

    /**
     * sha1加密码
     *
     * @param str
     * @param key
     * @return
     */
    public static String encodeBySHA1(String str, String key) {
        return KeyedDigest.getKeyedDigestSHA1(str, key);
    }

}
