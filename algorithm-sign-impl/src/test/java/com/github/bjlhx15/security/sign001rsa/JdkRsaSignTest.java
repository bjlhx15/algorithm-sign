package com.github.bjlhx15.security.sign001rsa;

import com.github.bjlhx15.security.asymmetric001rsa.JdkRsa;
import org.junit.Test;
import sun.security.rsa.SunRsaSign;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JdkRsaSignTest {

    //    SunRsaSign: Signature.SHA256withRSA -> sun.security.rsa.RSASignature$SHA256withRSA
//    aliases: [1.2.840.113549.1.1.11, OID.1.2.840.113549.1.1.11]
//    attributes: {SupportedKeyClasses=java.security.interfaces.RSAPublicKey|java.security.interfaces.RSAPrivateKey}
    @Test
    public void initKeyPair() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(JdkRsaSign.MD2withRSA);
        list.add(JdkRsaSign.MD5withRSA);
        list.add(JdkRsaSign.SHA1withRSA);
        list.add(JdkRsaSign.SHA224withRSA);
        list.add(JdkRsaSign.SHA256withRSA);
        list.add(JdkRsaSign.SHA384withRSA);
        list.add(JdkRsaSign.SHA512withRSA);
        String msg = "cesfds";

        for (String signAlgo : list) {
            Map.Entry<byte[], byte[]> pair = JdkRsaSign.initKeyPair(JdkRsaSign.RSA, 1024);
            System.out.println("pubKey:" + Base64.getEncoder().encodeToString(pair.getKey()));
            System.out.println("priKey:" + Base64.getEncoder().encodeToString(pair.getValue()));
            byte[] encrypt = JdkRsaSign.prikeySign(JdkRsaSign.RSA, signAlgo, pair.getValue(), msg.getBytes());
            System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
            boolean check = JdkRsaSign.pubKeyCheckSign(JdkRsaSign.RSA, signAlgo, pair.getKey(), msg.getBytes(), encrypt);
            System.out.println("txt:" + check);
        }
    }
}