package com.github.bjlhx15.security.base003md5AndSha;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class JdkMD5AndSha {
    public static final String MD2 = "MD2";
    public static final String MD3 = "MD3";
    public static final String MD4 = "MD4";
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA224 = "SHA-224";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";

    public static String msgSafeBase(String msg, String algorithmName) throws Exception {
        MessageDigest m = MessageDigest.getInstance(algorithmName);
        m.update(msg.getBytes("UTF8"));
        byte s[] = m.digest();
        return Hex.encodeHexString(s);
    }

}
