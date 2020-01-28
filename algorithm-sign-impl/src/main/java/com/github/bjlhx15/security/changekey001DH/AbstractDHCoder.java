package com.github.bjlhx15.security.changekey001DH;

import org.bouncycastle.jcajce.provider.symmetric.Blowfish;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

/**
 * DH密码交换协议<br>
 * DH加密流程：<br>
 * 1.初始化DH算法密钥对：<br>
 * 1.1.发送方—>构建发送方密钥-》公布发送方密钥给接收方-》使用接收者公钥构建发送方本地密钥<br>
 * 1.2.接收方—》使用发送方密钥密钥构建接收方密钥-》公布接收者公钥给发送方—》构建接收方本地密钥<br>
 * 2.DH算法加密消息传递：<br>
 * 2.1.发送方—>使用本地密钥加密消息—》发送加密消息给接收方<br>
 * 2.2.接收方—》使用本地密钥解密消息<br>
 *
 * @author 木子旭
 * @version %I%,%G%
 * @since 2017年3月17日上午9:18:14
 */
public abstract class AbstractDHCoder {
    public static final String ALGORITHM = "DH";

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

    /**
     * 初始化一方密钥对
     *
     * @return
     * @throws Exception
     */
    public static Map.Entry<byte[], byte[]> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(AbstractDHCoder.ALGORITHM);
        keyPairGenerator.initialize(AbstractDHCoder.KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        // 私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    /**
     * 通过公钥初始化密钥对
     *
     * @param pubKeyBytes 公钥
     * @return
     * @throws Exception
     */
    public static Map.Entry<byte[], byte[]> initKeyByPubKey(byte[] pubKeyBytes) throws Exception {
        // 解析甲方公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(AbstractDHCoder.ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 由一方公钥构建另一方密钥
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        keyPairGenerator.initialize(dhParamSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        // 私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    public abstract void encryptBefore(String secretAlgorithm);
    public abstract Cipher getCipher(String secretAlgorithm) throws Exception;

    /**
     * 加密<br>
     *
     * @param data       待加密数据
     * @param publicKey  别人公钥
     * @param privateKey 自己私钥
     * @return
     * @throws Exception
     */
    public byte[] encrypt(String secretAlgorithm, byte[] data, byte[] publicKey, byte[] privateKey) throws Exception {
        this.encryptBefore(secretAlgorithm);
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(secretAlgorithm, publicKey, privateKey);
        // 数据加密
//        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public abstract void decryptBefore(String secretAlgorithm);

    /**
     * 解密<br>
     *
     * @param data       待解密数据
     * @param publicKey  别人公钥
     * @param privateKey 自己私钥
     * @return
     * @throws Exception
     */
    public byte[] decrypt(String secretAlgorithm, byte[] data, byte[] publicKey, byte[] privateKey) throws Exception {
        this.decryptBefore(secretAlgorithm);
        // 生成本地密钥
        SecretKey secretKey = getSecretKey(secretAlgorithm, publicKey, privateKey);
        // 数据解密
//        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    public abstract  void getSecretKeyBefore(String secretAlgorithm);
    /**
     * 构建密钥
     *
     * @param pubKeyBytes 公钥
     * @param priKeyBytes 私钥
     * @return
     * @throws Exception
     */
    private SecretKey getSecretKey(String secretAlgorithm, byte[] pubKeyBytes, byte[] priKeyBytes) throws Exception {
        this.getSecretKeyBefore(secretAlgorithm);
        // 初始化公钥
        KeyFactory keyFactory = KeyFactory.getInstance(AbstractDHCoder.ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        // 初始化私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgree.init(priKey);
        keyAgree.doPhase(pubKey, true);

        // 生成本地密钥
        SecretKey secretKey = keyAgree.generateSecret(secretAlgorithm);
        return secretKey;
    }
}
