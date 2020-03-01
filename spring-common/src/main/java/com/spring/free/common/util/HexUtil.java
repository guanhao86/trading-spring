package com.spring.free.common.util;/**
 * Created by hengpu on 2019/3/4.
 */

/**
 * @ClassName HexUtil
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/4 16:05
 * @Version 1.0
 **/
public class HexUtil {
    private HexUtil() {
    }

    /**
     * Converts a byte array to hex string.
     *
     * @param b -
     *          the input byte array
     * @return hex string representation of b.
     */

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(HexUtil.HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
            sb.append(HexUtil.HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Converts a hex string into a byte array.
     *
     * @param s -
     *          string to be converted
     * @return byte array converted from s
     */
    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character.digit(s.charAt(j++), 16));
        }
        return buf;
    }

    private static final String HEX_CHARS = "0123456789abcdef";

    public static String appendParam(String returnStr, String paramId, String paramValue) {
        if (!returnStr.equals("")) {
            if (!paramValue.equals("")) {
                returnStr = returnStr + "&" + paramId + "=" + paramValue;
            }
        } else {
            if (!paramValue.equals("")) {
                returnStr = paramId + "=" + paramValue;
            }
        }
        return returnStr;
    }

    /**
     * ascii码转字符.
     *
     * @param b -
     *          the input byte array
     * @return hex string representation of b.
     */

    public static String toAscString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append((char) Integer.parseInt(Byte.toString(b[i])));
        }
        return sb.toString().trim();
    }
}
