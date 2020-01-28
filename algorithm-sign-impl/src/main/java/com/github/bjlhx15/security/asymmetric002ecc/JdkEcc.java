package com.github.bjlhx15.security.asymmetric002ecc;

import javax.crypto.Cipher;
import java.security.*;

/**
 *
 */
public class JdkEcc {
    public static KeyPair initKeyPair(String algorithm, Integer keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(keySize, new SecureRandom());

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
       throw new RuntimeException("暂不支持");
    }

    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        throw new RuntimeException("暂不支持");
    }
}
