package com.github.bjlhx15.security.symmetric.pbe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.security.Security;

public class BcPbe extends AbstractSymmetric {
    @Override
    public void decryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

    }

    @Override
    public void encryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

    }

    @Override
    public SecretKeyFactory initKeyBefore(String algorithm) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm,"BC");
        return factory;
    }
}
