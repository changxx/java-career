package com.changxx.practice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author changxx on 15-9-16.
 */
public class MD5Util {
    private MD5Util() {
    }

    /**
     * Returns a MessageDigest for the given <code>algorithm</code>.
     *
     * @return An MD5 digest instance.
     * @throws RuntimeException
     *         when a {@link java.security.NoSuchAlgorithmException} is
     *         caught
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
     * @param data
     *         Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     *
     * @param data
     *         Data to digest
     * @param encoding
     *         charset encoding
     * @return MD5 digest
     */
    public static byte[] md5(String data, String encoding) {
        byte[] bytes = new byte[0];
        try {
            bytes = data.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return md5(bytes);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     *
     * @param data
     *         Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     *
     * @param data
     *         Data to digest
     * @param encoding
     *         charset encoding
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(String data, String encoding) {
        return HexUtil.toHexString(md5(data, encoding));
    }

}
