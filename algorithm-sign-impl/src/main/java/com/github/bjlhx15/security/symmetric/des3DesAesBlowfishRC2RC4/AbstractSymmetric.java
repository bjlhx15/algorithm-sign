package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.Map;

/**
 * ALGORITHM 算法 <br>
 * 可替换为以下任意一种算法，同时key值的size相应改变。
 *
 * <pre>
 * DES                  key size must be equal to 56
 * DESede(TripleDES)    key size must be equal to 112 or 168
 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2                  key size must be between 40 and 1024 bits
 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
 * </pre>
 * <p>
 * 在Key toKey(byte[] key)方法中使用下述代码
 * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
 * <code>
 * DESKeySpec dks = new DESKeySpec(key);
 * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
 * SecretKey secretKey = keyFactory.generateSecret(dks);
 * </code>
 */
public abstract class AbstractSymmetric {
    /**
     * 转换密钥<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static Key toKey(String algorithm, byte[] key) throws Exception {
        SecretKey secretKey = null;
        if (algorithm.equalsIgnoreCase("DES")) {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            secretKey = keyFactory.generateSecret(dks);
        } else {
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
            secretKey = new SecretKeySpec(key, algorithm);
        }
        return secretKey;
    }

    public abstract void decryptBefore() throws Exception;

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] decrypt(Map.Entry<String, String> algorithm, byte[] key, byte[] iv, byte[] data) throws Exception {
        this.decryptBefore();
        Key k = toKey(algorithm.getKey(), key);
        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        if (algorithm.getValue().contains("GCM")) {
            AlgorithmParameterSpec params = algorithm.getValue().contains("GCM")
                    ? new GCMParameterSpec(128, data, 0, 12) : new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, k, params);
            return cipher.doFinal(data, 12, data.length - 12);
        } else {
            if (iv == null || algorithm.getValue().contains("/ECB/")
                    || algorithm.getValue().equalsIgnoreCase("des")
                    || algorithm.getValue().equalsIgnoreCase("DESede")
                    || algorithm.getValue().equalsIgnoreCase("AES")
            ) {
                cipher.init(Cipher.DECRYPT_MODE, k);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(iv));
            }
        }
        return cipher.doFinal(data);
    }

    public abstract void encryptBefore() throws Exception;

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] encrypt(Map.Entry<String, String> algorithm, byte[] key, byte[] iv, byte[] data) throws Exception {
        this.encryptBefore();
        Key k = toKey(algorithm.getKey(), key);
        Cipher cipher = Cipher.getInstance(algorithm.getValue());

        if (iv == null || algorithm.getValue().contains("/ECB/")
                || algorithm.getValue().equalsIgnoreCase("des")
                || algorithm.getValue().equalsIgnoreCase("DESede")
                || algorithm.getValue().equalsIgnoreCase("AES")
                || algorithm.getValue().contains("GCM")
        ) {
            cipher.init(Cipher.ENCRYPT_MODE, k);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, k, new IvParameterSpec(iv));
        }
        if (algorithm.getValue().contains("GCM")) {
            byte[] ivTmp = cipher.getIV();
            assert ivTmp.length == 12;
            byte[] encryptData = cipher.doFinal(data);
            assert encryptData.length == data.length + 16;
            byte[] message = new byte[12 + data.length + 16];
            System.arraycopy(ivTmp, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return message;
        }
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public byte[] initKey(Map.Entry<String, String> algorithm) throws Exception {
        return this.initKey(algorithm, null);
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public byte[] initKey(Map.Entry<String, String> algorithm, Integer keySize) throws Exception {
        return this.initKey(algorithm, keySize, null);
    }

    public abstract KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception;

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public byte[] initKey(Map.Entry<String, String> algorithm, Integer keySize, byte[] seed) throws Exception {
        KeyGenerator kg = this.initKeyBefore(algorithm);

        SecureRandom secureRandom = null;
        if (seed != null) {
            secureRandom = new SecureRandom(Base64.getEncoder().encode((seed)));
        } else {
            secureRandom = new SecureRandom();
        }
        kg.getProvider();
        if (keySize != null) {
            kg.init(keySize, secureRandom);
        } else {
            kg.init(secureRandom);
        }
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static byte[] initIv() throws Exception {
        return initIv(8);
    }

    public static byte[] initIv(int length) throws Exception {
        //初始化盐
        SecureRandom random = new SecureRandom();
        return random.generateSeed(length);
//apache的
//        return RandomStringUtils.randomAlphanumeric(length).getBytes("UTF-8");
    }
}
