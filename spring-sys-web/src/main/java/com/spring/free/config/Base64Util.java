package com.spring.free.config;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    /**
     * 加密
     * @param value
     * @return
     */
    public static String encode(String value) {
        Base64 base64 = new Base64();
        return base64.encodeToString(value.getBytes());
    }

    /**
     * 解密
     * @param value
     * @return
     */
    public static String decode(String value) {
        Base64 base64 = new Base64();
        return base64.encodeAsString(value.getBytes());
    }

}
