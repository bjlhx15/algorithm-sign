package com.github.bjlhx15.security.symmetric.pbe;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.util.Map;

public class JdkPbe extends AbstractSymmetric {
    @Override
    public void decryptBefore() throws Exception {

    }

    @Override
    public void encryptBefore() throws Exception {

    }

    @Override
    public SecretKeyFactory initKeyBefore(String algorithm) throws Exception {
        return SecretKeyFactory.getInstance(algorithm);
    }
}
