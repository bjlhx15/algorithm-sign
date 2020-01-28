package com.github.bjlhx15.security.sign003ecc;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class JdkEcdsa {
    public static final String EC = "EC";
    public static final String NONEwithECDSA = "NONEwithECDSA";
    public static final String RIPEMD160withECDSA = "RIPEMD160withECDSA";
    public static final String SHA1withECDSA = "SHA1withECDSA";
    public static final String SHA224withECDSA = "SHA224withECDSA";
    public static final String SHA256withECDSA = "SHA256withECDSA";
    public static final String SHA384withECDSA = "SHA384withECDSA";
    public static final String SHA512withECDSA = "SHA512withECDSA";

    public static KeyPair initKey(int keySize, byte[] seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(JdkEcdsa.EC);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keygen.initialize(keySize, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        return keys;
    }

    public static KeyPair initKey(int keySize) throws Exception {
        return initKey(keySize, new SecureRandom().generateSeed(8));
    }

    public static byte[] sign(String signAlgorithm, PrivateKey privateKey, byte[] data) throws Exception {
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static byte[] sign(String signAlgorithm, byte[] privateKeyByte, byte[] data) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        return sign(signAlgorithm, privateKey, data);
    }

    public static boolean verify(String signAlgorithm, byte[] publKeyBytes, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        return verify(signAlgorithm,publicKey,data,sign);
    }

    public static boolean verify(String signAlgorithm, PublicKey publicKey, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}
