package com.github.bjlhx15.security.asymmetric001rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

public class JdkRsa {
    public final static String RSA = "RSA";

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
     * 公钥加密、私钥解密——加密
     *
     * @param algorithm
     * @param pubKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] pubKeyEncrypt(String algorithm, byte[] pubKey, byte[] data) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密、私钥解密——加密
     *
     * @param algorithm
     * @param priKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] priKeyDecrypt(String algorithm, byte[] priKey, byte[] data) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密、公钥解密——加密
     *
     * @param algorithm
     * @param priKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] priKeyEncrypt(String algorithm, byte[] priKey, byte[] data) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    /**
     * 私钥加密、公钥解密——解密
     *
     * @param algorithm
     * @param pubKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] pubKeyDecrypt(String algorithm, byte[] pubKey, byte[] data) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
}
