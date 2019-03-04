package com.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class KeyedDigest {

    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String UTF8 = "utf-8";

    private static String getKeyedDigest(String algorithm, String strSrc, String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(strSrc.getBytes(UTF8));
            return getFormattedText(messageDigest.digest(key.getBytes(UTF8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFormattedText(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toHexString((0x000000ff & bytes[i]) | 0xffffff00).substring(6));
        }
        return result.toString();
    }

    public static String getKeyedDigestMD5(String strSrc, String key) {
        return getKeyedDigest(MD5, strSrc, key);
    }

    public static String getKeyedDigestSHA1(String strSrc, String key) {
        return getKeyedDigest(SHA1, strSrc, key);
    }

}
