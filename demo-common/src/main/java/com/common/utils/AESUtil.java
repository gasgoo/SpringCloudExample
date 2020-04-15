package com.common.utils;


import com.google.common.collect.Lists;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * AES（Advanced Encryption Standard，对称加密的一种 ）加解密算法封装，主要包括：<br />
 * 1. 基于AES的加密算法实现方法；<br />
 * 2. 基于AES的解密算法实现方法；<br />
 *
 */

public class AESUtil {

    /**
     * 加密算法：基于明文串，密钥对明文信息进行加密
     *
     * @param sSrc 待加密的字符串。
     * @param sKey 密钥。长度必须=16个字节(长度为128位)
     * @return 加密后的经base64编码的字符串。
     * @throws GeneralSecurityException
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String sSrc, String sKey)
        throws Exception {
        if (sSrc == null || sSrc.trim().length() == 0 || sKey == null
            || sKey.trim().length() != 16) {

            throw new Exception();
        }

        try {
            // 构造密钥。
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(
                "0102030405060708".getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new Exception("[AES加密异常]", e);
        }
    }

    /**
     * 解密 算法：根据加密串和密钥对密文进行解密.
     *
     * @param encryptedString 待解密的加密串。
     * @param sKey            密钥。
     * @return 解密后的字符串。
     */
    public static String decrypt(String encryptedString, String sKey)
        throws Exception {
        if (encryptedString == null || encryptedString.trim().length() == 0
            || sKey == null || sKey.trim().length() != 16) {
            throw new Exception();
        }

        try {
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(encryptedString);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            throw new Exception("[AES解密异常]", e);
        }
    }

    public static String md5(String inputText) throws NoSuchAlgorithmException {
        return md5Encrypt(inputText, "md5");
    }

    public static String md5Encrypt(String inputText, String algorithmName) throws NoSuchAlgorithmException {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("please input source string");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        MessageDigest m = MessageDigest.getInstance(algorithmName);
        m.update(inputText.getBytes(Charset.forName("UTF-8")));
        byte[] s = m.digest();
        return hex(s);
    }

    /**
     * 返回十六进制字符串
     *
     * @param arr
     * @return
     */
    public static String hex(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }

    /**
     * 对传入参数进行加密
     *
     * @param param 为排序参数
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String md5(Map<String, String> param, String md5Key) throws NoSuchAlgorithmException {
        if (param == null || param.size() == 0) {
            return null;
        }
        String msg = null;

        Set<Map.Entry<String, String>> set = param.entrySet();
        List<Map.Entry<String, String>> list = Lists.newArrayList(set);

        // 对传入参数排序
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));

        // 对传入参数拼接字符串
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, String> entry;
        for (int i = 0, j = list.size(); i < j; i++) {
            entry = list.get(i);
            sb.append(entry.getKey()).append(entry.getValue());
        }
        // 加入MD5KEY
        sb.append(md5Key);
        msg = sb.toString();
        return md5(msg);
    }

}
