package com.github.bjlhx15.security.sign002dsa;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JdkDsaTest {

    @Test
    public void sign() throws Exception {
        String msg = "密文";
        Map.Entry<byte[], byte[]> key = JdkDsa.initKey();
        System.out.println("pub:" + Base64.getEncoder().encodeToString(key.getKey()));
        System.out.println("pri:" + Base64.getEncoder().encodeToString(key.getValue()));
//        sun.security.provider.DSA
        List<String> list = new ArrayList<>();
        list.add(JdkDsa.DSA);//==SHA1withDSA
        list.add(JdkDsa.SHA1withDSA);
        list.add(JdkDsa.SHA224withDSA);
        list.add(JdkDsa.SHA256withDSA);

        for (String algo : list) {
            byte[] dsas = JdkDsa.sign(algo, key.getValue(), msg.getBytes());
            System.out.println("sign:" + Base64.getEncoder().encodeToString(dsas));

            boolean dsa = JdkDsa.verify(algo, key.getKey(), msg.getBytes(), dsas);
            System.out.println("check sign:" + dsa);

        }

    }
}