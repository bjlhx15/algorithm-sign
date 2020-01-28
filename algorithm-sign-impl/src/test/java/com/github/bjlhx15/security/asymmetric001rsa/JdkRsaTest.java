package com.github.bjlhx15.security.asymmetric001rsa;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import static org.junit.Assert.*;

public class JdkRsaTest {

    @Test
    public void initKeyPair() throws Exception {
        String msg="cesfds";
        Map.Entry<byte[], byte[]> pair = JdkRsa.initKeyPair(JdkRsa.RSA, 512);
        System.out.println("pubKey:"+ Base64.getEncoder().encodeToString(pair.getKey()));
        System.out.println("priKey:"+ Base64.getEncoder().encodeToString(pair.getValue()));
        byte[] encrypt = JdkRsa.pubKeyEncrypt(JdkRsa.RSA, pair.getKey(), msg.getBytes());
        System.out.println("encrypt:"+ Base64.getEncoder().encodeToString(encrypt));
        byte[] bytes = JdkRsa.priKeyDecrypt(JdkRsa.RSA, pair.getValue(), encrypt);
        System.out.println("txt:"+ new String(bytes));
    }


    @Test
    public void initKeyPair2() throws Exception {
        String msg="cesfds";
        Map.Entry<byte[], byte[]> pair = JdkRsa.initKeyPair(JdkRsa.RSA, 1024);
        System.out.println("pubKey:"+ Base64.getEncoder().encodeToString(pair.getKey()));
        System.out.println("priKey:"+ Base64.getEncoder().encodeToString(pair.getValue()));
        byte[] encrypt = JdkRsa.pubKeyEncrypt(JdkRsa.RSA, pair.getKey(), msg.getBytes());
        System.out.println("encrypt:"+ Base64.getEncoder().encodeToString(encrypt));
        byte[] bytes = JdkRsa.priKeyDecrypt(JdkRsa.RSA, pair.getValue(), encrypt);
        System.out.println("txt:"+ new String(bytes));
    }

    @Test
    public void initKeyPair2048() throws Exception {
        String msg="cesfds";
        Map.Entry<byte[], byte[]> pair = JdkRsa.initKeyPair(JdkRsa.RSA, 2048);
        System.out.println("pubKey:"+ Base64.getEncoder().encodeToString(pair.getKey()));
        System.out.println("priKey:"+ Base64.getEncoder().encodeToString(pair.getValue()));
        byte[] encrypt = JdkRsa.pubKeyEncrypt(JdkRsa.RSA, pair.getKey(), msg.getBytes());
        System.out.println("encrypt:"+ Base64.getEncoder().encodeToString(encrypt));
        byte[] bytes = JdkRsa.priKeyDecrypt(JdkRsa.RSA, pair.getValue(), encrypt);
        System.out.println("txt:"+ new String(bytes));
    }
}