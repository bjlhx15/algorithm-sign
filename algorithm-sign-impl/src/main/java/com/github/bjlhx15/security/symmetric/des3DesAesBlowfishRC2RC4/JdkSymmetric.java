package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import javax.crypto.KeyGenerator;
import java.util.Map;


public class JdkSymmetric extends AbstractSymmetric {

    @Override
    public KeyGenerator initKeyBefore(Map.Entry<String, String> algorithm) throws Exception {
        return KeyGenerator.getInstance(algorithm.getKey());
    }

    @Override
    public void encryptBefore() throws Exception {
        return;
    }

    @Override
    public void decryptBefore() throws Exception {
        return;
    }


}
