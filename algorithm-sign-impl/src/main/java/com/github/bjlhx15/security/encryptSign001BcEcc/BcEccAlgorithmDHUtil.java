package com.github.bjlhx15.security.encryptSign001BcEcc;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;

public class BcEccAlgorithmDHUtil extends AbstractBcEccAlgorithmUtil {
    public static final String signAlorithm = "SHA256withECDSA";

    /**
     * 通过公钥初始化密钥对
     *
     * @return
     * @throws Exception
     */
    public static Map.Entry<String, String> initKeyByPubKey(String alogrithm,String pubKeyStr) throws Exception {
        // 解析甲方公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(alogrithm);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        java.security.spec.ECParameterSpec dhParameterSpec = ((ECPublicKey) publicKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(alogrithm);
        keyPairGenerator.initialize(dhParameterSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String pubKey1 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String priKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        return new AbstractMap.SimpleEntry<>(pubKey1, priKey);
    }
    /**
     * 通过公钥初始化密钥对
     *
     * @return
     * @throws Exception
     */
    public static Map.Entry<String, String> initKeyByPubKey(String pubKey) throws Exception {
        return initKeyByPubKey("ECDH",pubKey);
    }

    //加密
    public static String encryptBase64Utf8(String pubKeyStr, String priKeyStr,String content) throws Exception {
        PublicKey publicKey = getPublicKey(pubKeyStr);
        PrivateKey privateKey = getPrivateKey(priKeyStr);

        byte[] contentBytes = content.getBytes("UTF-8");
        byte[] encrypt = encrypt(publicKey,privateKey,contentBytes);

        return Base64.getEncoder().encodeToString(encrypt);
    }

    public static byte[] encrypt(PublicKey publicKey,PrivateKey privateKey,byte[] content) throws Exception {
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), "BC");//写不写 BC都可以，都是会选择BC实现来做
        System.out.println("密码en："+Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(content);
    }

    //解密
    public static String decryptBase64Utf8(String pubKeyStr, String priKeyStr,String content) throws Exception {
        PublicKey publicKey = getPublicKey(pubKeyStr);
        PrivateKey privateKey = getPrivateKey(priKeyStr);
        byte[] contentBytes = Base64.getDecoder().decode(content);
        byte[] decrypt = decrypt(publicKey,privateKey,contentBytes);
        return new String(decrypt, "UTF-8");
    }

    public static byte[] decrypt(PublicKey publicKey,PrivateKey privateKey,byte[] content) throws Exception {
        SecretKey secretKey = getSecretKey(publicKey, privateKey);
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), "BC");//写不写 BC都可以，都是会选择BC实现来做
        System.out.println("密码de："+Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(content);
    }

    /**
     * 构建密钥
     *
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKey(PublicKey pubKey, PrivateKey priKey) throws Exception {
        return getSecretKey("ECDH","AES",pubKey,priKey);
    }
    /**
     * 构建密钥
     *
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKey(String algorithm, String secretAlgorithm, PublicKey pubKey, PrivateKey priKey) throws Exception {
        KeyAgreement keyAgree = KeyAgreement.getInstance(algorithm, "BC");
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);
        // 生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret(secretAlgorithm);
        return secretKey;
    }

    //获取key
    public static PrivateKey getPrivateKey(String base64PriKeyStr) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PriKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static PublicKey getPublicKey(String base64PubKeyStr) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PubKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
        return keyFactory.generatePublic(x509EncodedKeySpec);

    }
}
