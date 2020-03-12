package com.github.bjlhx15.security.encryptSign001BcEcc;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;

public abstract class AbstractBcEccAlgorithmUtil {
    public static final String signAlorithm = "SHA256withECDSA";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    //key
    public static KeyPair initKeyPair(String algorithm) throws Exception {
        return initKeyPair(algorithm, "secp256k1");
//        return initKeyPair(algorithm, "secp192k1");
    }

    public static Map.Entry<String, String> initKeyPairBase64() throws Exception {
        return initKeyPairBase64("ECDH");
    }

    public static Map.Entry<String, String> initKeyPairBase64(String algorithm) throws Exception {
        KeyPair keyPair = initKeyPair(algorithm);
        String pubKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String priKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        return new AbstractMap.SimpleEntry<>(pubKey, priKey);
    }

    private static KeyPair initKeyPair(String algorithm, String spec) throws Exception {
        ECParameterSpec dhParamSpec = ECNamedCurveTable.getParameterSpec(spec);
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm, "BC");
        keyPairGenerator.initialize(dhParamSpec, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //加密
    public static String encryptBase64Utf8(String pubKeyStr, String content) throws Exception {
        PublicKey publicKey = getPublicKey(pubKeyStr);

        byte[] contentBytes = content.getBytes("UTF-8");
        byte[] encrypt = encrypt(contentBytes, publicKey);

        return Base64.getEncoder().encodeToString(encrypt);
    }

    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");//写不写 BC都可以，都是会选择BC实现来做
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //解密
    public static String decryptBase64Utf8(String priKeyStr, String content) throws Exception {
        PrivateKey privateKey = getPrivateKey(priKeyStr);

        byte[] contentBytes = Base64.getDecoder().decode(content);
        byte[] decrypt = decrypt(contentBytes, privateKey);
        return new String(decrypt, "UTF-8");
    }

    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    //签名
    public static String sign(String privateKey, String data) throws Exception {
        return sign(privateKey, data.getBytes("UTF-8"));
    }

    public static String sign(String privateKey, byte[] data) throws Exception {
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(signAlorithm);
        signature.initSign(getPrivateKey(privateKey));
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    //验签
    public static boolean verify(String publKey, String data, String sign) throws Exception {
        return verify(publKey, data.getBytes("UTF-8"), sign);
    }

    public static boolean verify(String publKey, byte[] data, String sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(signAlorithm);
        signature.initVerify(getPublicKey(publKey));
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign));
    }

    //获取key
    public static PrivateKey getPrivateKey(String base64PriKeyStr) throws Exception {
//        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PriKeyStr));
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PriKeyStr));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static PublicKey getPublicKey(String base64PubKeyStr) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PubKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(x509EncodedKeySpec);

    }
}
