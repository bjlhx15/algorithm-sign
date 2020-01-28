package com.github.bjlhx15.security.sign001rsa;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

/**
 * RSA签名流程：
 * <p>
 * 发送方—>构建密钥对-》公布密钥给接收方—>使用私钥对数据签名-》发送签名、数据给接收方。<br>
 * 接收方—》使用公钥、签名验证数据
 *
 * @author Administrator
 */
/**
 *
 * 使用rsa签名 发送方—>构建密钥对-》公布密钥给接收方。 发送方—>使用私钥对数据签名-》发送签名、数据给接收方—》接收方使用公钥、签名验证数据
 */
/**
 * 算法 密钥长度 默认长度 签名长度 实现的方<br>
 *                      MD2withRSA 512-65536（64的整数倍） 1024 同密钥 JDK8<br>
 *                      MD5withRSA 同上 1024 同密钥 JDK8<br>
 *                      SHA1withRSA ... 1024 同密钥 JDK8<br>
 *                      SHA224withRSA ... 2048 同密钥 JDK8<br>
 *                      SHA256withRSA ... 2048 同密钥 JDK8<br>
 *                      SHA384withRSA ... 2048 同密钥 JDK8<br>
 *                      SHA512withRSA ... 2048 同密钥 JDK8<br>
 *                      RIPEMD128withRSA 2048 同密钥 BC<br>
 *                      RIPEMD160withRSA 同上 2048 同密钥 BC<br>
 */
public class JdkRsaSign {
    public final static String RSA = "RSA";
    public final static String MD2withRSA = "MD2withRSA";
    public final static String MD5withRSA = "MD5withRSA";
    public final static String SHA1withRSA = "SHA1withRSA";
    public final static String SHA224withRSA = "SHA224withRSA";
    public final static String SHA256withRSA = "SHA256withRSA";
    public final static String SHA384withRSA = "SHA384withRSA";
    public final static String SHA512withRSA = "SHA512withRSA";

    /**
     * // 1.初始化密钥
     *
     * @param algorithm
     * @param keySize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map.Entry<byte[], byte[]> initKeyPair(String algorithm, Integer keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new AbstractMap.SimpleEntry<>(rsaPublicKey.getEncoded(), rsaPrivateKey.getEncoded());
    }

    /**
     * 执行签名
     *
     * @return
     */
    public static byte[] prikeySign(String algorithm, String signAlgorithm, byte[] priKey, byte[] data) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 执行签名
     *
     * @return
     */
    public static boolean pubKeyCheckSign(String algorithm, String signAlgorithm, byte[] pubKey, byte[] data, byte[] sign) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);// 为了数据的完整性
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}

