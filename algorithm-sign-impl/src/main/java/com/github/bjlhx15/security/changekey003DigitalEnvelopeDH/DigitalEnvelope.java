package com.github.bjlhx15.security.changekey003DigitalEnvelopeDH;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class DigitalEnvelope {

    /** Client端与Server端交换数据的队列, 模拟两者之间通讯的通道, 现实中两者可能是通过Socket通讯的. */
    private static final BlockingQueue<Map<String,byte[]>> CHANNEL = new LinkedBlockingQueue<>(1);
    /**
     * 生成RSA算法的公私密钥对.
     *
     * @return 生成RSA算法的公私密钥对.
     */
    public static final KeyPair generatorKeyPair() {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        random.setSeed(53);
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        return pair;
    }

    /**
     * 模拟密钥交换的服务器端, 服务器端与客户端通过共享内存来交换Digital Envelope.
     */
    static class Server extends Thread {

        /**
         * 实际中有可能是客户端在请求服务器端时上送了自己的公钥, 也有可能是在注册
         * 时就在服务器端登记了公钥.
         *
         * @param clientPublicKey 客户端的公钥.
         */
        public Server(PublicKey clientPublicKey) {
            this.clientPublicKey = clientPublicKey;
        }

        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            try {
                String msg = "Legend of AK47";
                KeyGenerator generator = KeyGenerator.getInstance("DES");
                SecretKey sessionKey = generator.generateKey();
//                Key sessionKey = KeyGeneratorDemo.generatePlainDES();
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, sessionKey);
                byte[] p = msg.getBytes("UTF-8");
                byte[] msgCipher = cipher.doFinal(p);
                cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, clientPublicKey);
                byte[] keyCipher = cipher.doFinal(sessionKey.getEncoded());
                Map result = new HashMap();
                result.put("msg", msgCipher);
                result.put("key", keyCipher);
                CHANNEL.offer(result);
            } catch (Exception e) {
                // TODO Exception handling...
            }
        }

        /** 客户端的公钥. */
        private final PublicKey clientPublicKey;

    }

    /**
     * 模拟密钥交换的客户端, 服务器端与客户端通过共享内存来交换Digital Envelope.
     *
     * @author Rich, 2012-6-14.
     * @version 1.0
     * @since 1.0
     */
    static class Client extends Thread {

        /**
         * 密钥对应该在客户端内部产生, 然后客户端在请求服务器端时上送了自己的公钥, 也有可能是在注册时就在服务器端登记了公钥.
         *
         * @param keyPair 客户端的公私密钥对.
         */
        public Client(KeyPair keyPair) {
            this.keyPair = keyPair;
        }
        @Override
        public void run() {
            try {
                Map<String,byte[]> received = CHANNEL.take();
                byte[] msgCipher = received.get("msg");
                byte[] keyCipher = received.get("key");
                PrivateKey privateKey = keyPair.getPrivate();
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] encoded = cipher.doFinal(keyCipher);
                KeySpec keySpec = new DESKeySpec(encoded);
                SecretKeyFactory fac = SecretKeyFactory.getInstance("DES");
                Key key = fac.generateSecret(keySpec);
                cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] msg = cipher.doFinal(msgCipher);
                String plainText = new String(msg, "UTF-8");
                System.out.println(plainText);
            } catch (Exception e) {
                // TODO Exception handling...
            }
        }
        /** 客户端的公私密钥对. */
        private final KeyPair keyPair;
    }

}
