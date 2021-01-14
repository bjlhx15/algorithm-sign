package com.github.bjlhx15.security.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className BcSm4Util
 * @description TODO
 * @date 2021-01-12 22:34
 */
public class BcSm4Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final String ALGORITHM_NAME = "SM4";
    public static final String DEFAULT_KEY = "random_seed";
    // 128-32位16进制；256-64位16进制
    public static final int DEFAULT_KEY_SIZE = 128;


    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] generateKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        return generateKey(DEFAULT_KEY, DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(String seed) throws NoSuchAlgorithmException, NoSuchProviderException {
        return generateKey(seed, DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(String seed, int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        if (null != seed && !"".equals(seed)) {
            random.setSeed(seed.getBytes());
        }
        kg.init(keySize, random);
        return kg.generateKey().getEncoded();
    }

    /**
     * @description 加密
     */
    public static byte[] encrypt(String algorithmName, byte[] key, byte[] iv, byte[] data) throws Exception {
        return sm4core(algorithmName,Cipher.ENCRYPT_MODE, key, iv, data);
    }

    /**
     * @description 解密
     */
    public static byte[] decrypt(String algorithmName, byte[] key, byte[] iv, byte[] data) throws Exception {
        return sm4core(algorithmName, Cipher.DECRYPT_MODE, key, iv, data);
    }

    private static byte[] sm4core(String algorithmName, int type, byte[] key, byte[] iv, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        if (algorithmName.contains("/ECB/")) {
            cipher.init(type, sm4Key);
        } else {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(type, sm4Key, ivParameterSpec);
        }

        return cipher.doFinal(data);
    }
}
