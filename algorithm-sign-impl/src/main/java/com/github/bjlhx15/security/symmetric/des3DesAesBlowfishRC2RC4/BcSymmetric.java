package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyGenerator;
import java.security.Security;
import java.util.Map;

public class BcSymmetric extends AbstractSymmetric {

    @Override
    public void decryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        return;
    }

    @Override
    public void encryptBefore() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        return;
    }

    @Override
    public KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(algorithm.getKey(), "BC");
        return kg;
    }
}
