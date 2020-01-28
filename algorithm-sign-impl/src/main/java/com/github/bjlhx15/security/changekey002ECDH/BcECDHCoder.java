package com.github.bjlhx15.security.changekey002ECDH;

import com.github.bjlhx15.security.changekey001DH.AbstractDHCoder;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public abstract class BcECDHCoder {
    public static final String DH = "DH";
    public static final String ECDH = "ECDH";
    public static final String ECDHC = "ECDHC";

    /**
     * 默认密钥字节数
     *
     * <pre>
     * DH
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;

    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */
    public static final String DES = "DES";
    public static final String AES = "AES";
    public static final String DESede = "DESede";
    public static final String Blowfish = "Blowfish";

    //k Koblitz曲线
    public static final String secp192k1 = "secp192k1";
    public static final String secp224k1 = "secp224k1";
    public static final String secp256k1 = "secp256k1";

    //r 随机 ECC是伪随机曲线
    public static final String secp192r1 = "secp192r1";
    public static final String secp224r1 = "secp224r1";
    public static final String secp256r1 = "secp256r1";

    public static final String prime256v1 = "prime256v1";


    /**
     * 初始化一方密钥对
     *
     * @return
     * @throws Exception
     */
    public static KeyPair initKey(String algorithm, String keySpec) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

        ECParameterSpec dhParamSpec = ECNamedCurveTable.getParameterSpec(keySpec);
        SecureRandom sr = new SecureRandom();

        keyPairGenerator.initialize(dhParamSpec, sr);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 通过公钥初始化密钥对
     *
     * @return
     * @throws Exception
     */
    public static KeyPair initKeyByPubKey(String algorithm, PublicKey pubKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        java.security.spec.ECParameterSpec dhParameterSpec = ((ECPublicKey) pubKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(dhParameterSpec);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 通过公钥初始化密钥对
     *
     * @return
     * @throws Exception
     */
    public static KeyPair initKeyByPubKey(String algorithm, String pubKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 解析甲方公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        return initKeyByPubKey(algorithm, publicKey);
    }

    /**
     * 加密<br>
     *
     * @param data       待加密数据
     * @param publicKey  别人公钥
     * @param privateKey 自己私钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String algorithm, String secretAlgorithm, byte[] data, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(algorithm, secretAlgorithm, publicKey, privateKey);
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        // 数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), "BC");
//        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     *
     * @param data       待解密数据
     * @param publicKey  别人公钥
     * @param privateKey 自己私钥
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String algorithm, String secretAlgorithm, byte[] data, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(algorithm, secretAlgorithm, publicKey, privateKey);
        // 数据解密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm(), "BC");
//        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    /**
     * 构建密钥
     *
     * @return
     * @throws Exception
     */
    private static SecretKey getSecretKey(String algorithm, String secretAlgorithm, PublicKey pubKey, PrivateKey priKey) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 初始化公钥
//        KeyFactory keyFactory = KeyFactory.getInstance(BcECDHCoder.DH);
//        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
//        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        // 初始化私钥
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
//        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        KeyAgreement keyAgree = KeyAgreement.getInstance(algorithm, "BC");
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);

        // 生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret(secretAlgorithm);
        return secretKey;
    }
}
