package com.github.bjlhx15.security.base004hmac;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class HmacUtils {
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String HmacSHA224 = "HmacSHA224";
    public static final String HmacSHA256 = "HmacSHA256";
    public static final String HmacSHA384 = "HmacSHA384";
    public static final String HmacSHA512 = "HmacSHA512";

    public static String initMacKey(String algorithm) throws Exception {
        // HmacMD5,HmacSHA1,HmacSHA256,HmacSHA384,HmacSHA512
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);// 初始化KeyGenerator
        SecretKey secretKey = keyGenerator.generateKey();// 产生密钥
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());// 获得密钥
    }

    public static String hashMsgCode(String algorithm, String key, byte[] data) throws Exception {
        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());// 实例化MAC
        mac.init(secretKey);// 初始化Mac
        return new String(Hex.encodeHex(mac.doFinal(data)));// 执行摘要
    }

    public static boolean check(String algorithm, String key, String hash, String data) throws Exception {
        String hash2 = hashMsgCode(algorithm, key, data.getBytes("utf-8"));
        return hash.equals(hash2);
    }
}
