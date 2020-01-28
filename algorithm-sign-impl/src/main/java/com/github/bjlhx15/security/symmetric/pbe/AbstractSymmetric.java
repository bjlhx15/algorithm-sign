package com.github.bjlhx15.security.symmetric.pbe;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.lang.reflect.Constructor;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public abstract class AbstractSymmetric {
    public abstract void decryptBefore() throws Exception;

    private int keyObtentionIterations = 1000;

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] decrypt(String algorithm, Key key, byte[] salt,  byte[] iv,byte[] data) throws Exception {
        this.decryptBefore();
        //解密
        PBEParameterSpec pbeParameterSpec = this.buildPBEParameterSpec(salt, iv);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
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
    public byte[] encrypt(String algorithm, Key key, byte[] salt, byte[] iv, byte[] data) throws Exception {
        this.encryptBefore();
        //加密
        PBEParameterSpec pbeParameterSpec = this.buildPBEParameterSpec(salt, iv);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        return cipher.doFinal(data);
    }

    public abstract SecretKeyFactory initKeyBefore(String algorithm) throws Exception;

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public Key initKey(String algorithm, String password) throws Exception {
        SecretKeyFactory factory = this.initKeyBefore(algorithm);
        //口令与密钥
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        return factory.generateSecret(pbeKeySpec);
    }

    public static byte[] initSalt() throws Exception {
        return initSalt(8);
    }

    public static byte[] initSalt(int length) throws Exception {
        //初始化盐
        SecureRandom random = new SecureRandom();
        return random.generateSeed(length);
    }

    private PBEParameterSpec buildPBEParameterSpec(byte[] salt, byte[] iv) {
        PBEParameterSpec parameterSpec;
        if (iv == null) {
            parameterSpec = new PBEParameterSpec(salt, keyObtentionIterations);
        } else {
            try {
                Class[] parameters = new Class[]{byte[].class, Integer.TYPE, AlgorithmParameterSpec.class};
                Constructor<PBEParameterSpec> java8Constructor = PBEParameterSpec.class.getConstructor(parameters);
                Object[] parameterValues = new Object[]{salt, keyObtentionIterations, new IvParameterSpec(iv)};
                parameterSpec = (PBEParameterSpec) java8Constructor.newInstance(parameterValues);
            } catch (Exception var7) {
                parameterSpec = new PBEParameterSpec(salt, keyObtentionIterations);
            }
        }
        return parameterSpec;
    }
}
