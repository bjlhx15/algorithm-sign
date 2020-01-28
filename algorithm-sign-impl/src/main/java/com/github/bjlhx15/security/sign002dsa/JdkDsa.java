package com.github.bjlhx15.security.sign002dsa;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

public class JdkDsa {
    public static final String DSA = "DSA";
    public static final String SHA1withDSA = "SHA1withDSA";
    public static final String SHA224withDSA = "SHA224withDSA";
    public static final String SHA256withDSA = "SHA256withDSA";
    public static final String SHA384withDSA = "SHA384withDSA";
    public static final String SHA512withDSA = "SHA512withDSA";

    /**
     * 默认密钥字节数
     *
     * <pre>
     * DSA
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 默认种子
     */
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] sign(String signAlgorithm, byte[] priKeyBytes, byte[] data) throws Exception {
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(JdkDsa.DSA);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(String signAlgorithm, byte[] publKeyBytes, byte[] data, byte[] sign) throws Exception {
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publKeyBytes);
        // ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(JdkDsa.DSA);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(sign);
    }

    /**
     * 生成密钥
     *
     * @param seed 种子
     * @return 密钥对象
     * @throws Exception
     */
    public static Map.Entry<byte[], byte[]> initKey(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(JdkDsa.DSA);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keygen.initialize(JdkDsa.KEY_SIZE, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();
        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    /**
     * 默认生成密钥
     *
     * @return 密钥对象
     * @throws Exception
     */
    public static Map.Entry<byte[], byte[]> initKey() throws Exception {
        return JdkDsa.initKey(JdkDsa.DEFAULT_SEED);
    }
}
