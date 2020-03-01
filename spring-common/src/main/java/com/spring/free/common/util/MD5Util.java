package com.spring.free.common.util;/**
 * Created by hengpu on 2019/3/4.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5Util
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/4 16:04
 * @Version 1.0
 **/
public class MD5Util {
        private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };

        private MD5Util() {
        }

        /**
         * Returns a MessageDigest for the given <code>algorithm</code>.
         *
         * @return An MD5 digest instance.
         * @throws RuntimeException when a {@link NoSuchAlgorithmException} is caught
         */
        static MessageDigest getDigest() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Calculates the MD5 digest and returns the value as a 16 element
         * <code>byte[]</code>.
         *
         * @param data Data to digest
         * @return MD5 digest
         */
        public static byte[] md5(byte[] data) {
            return getDigest().digest(data);
        }

        /**
         * Calculates the MD5 digest and returns the value as a 16 element
         * <code>byte[]</code>.
         *
         * @param data Data to digest
         * @return MD5 digest
         */
        public static byte[] md5(String data) {
            return md5(data.getBytes());
        }

        /**
         * Calculates the MD5 digest and returns the value as a 32 character hex
         * string.
         *
         * @param data Data to digest
         * @return MD5 digest as a hex string
         */
        public static String md5Hex(byte[] data) {
            return HexUtil.toHexString(md5(data));
        }

        /**
         * Calculates the MD5 digest and returns the value as a 32 character hex
         * string.
         *
         * @param data Data to digest
         * @return MD5 digest as a hex string
         */
        public static String md5Hex(String data) {
            return HexUtil.toHexString(md5(data));
        }

        private static String toHexString(byte[] b) {
            //String to  byte
            StringBuilder sb = new StringBuilder(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
                sb.append(HEX_DIGITS[b[i] & 0x0f]);
            }
            return sb.toString();
        }

        public static String md5ByWs(String s) {
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                return toHexString(messageDigest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return "";
        }
}
