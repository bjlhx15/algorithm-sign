package com.github.bjlhx15.security.changekey001DH;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * DH密码交换协议<br>
 * DH加密流程：<br>
 * 1.初始化DH算法密钥对：<br>
 * 1.1.发送方—>构建发送方密钥-》公布发送方密钥给接收方-》使用接收者公钥构建发送方本地密钥<br>
 * 1.2.接收方—》使用发送方密钥密钥构建接收方密钥-》公布接收者公钥给发送方—》构建接收方本地密钥<br>
 * 2.DH算法加密消息传递：<br>
 * 2.1.发送方—>使用本地密钥加密消息—》发送加密消息给接收方<br>
 * 2.2.接收方—》使用本地密钥解密消息<br>
 *
 * @author 木子旭
 * @version %I%,%G%
 * @since 2017年3月17日上午9:18:14
 */
public class JdkDHCoder extends AbstractDHCoder {

    @Override
    public void encryptBefore(String secretAlgorithm) {

    }

    @Override
    public Cipher getCipher(String secretAlgorithm) throws Exception {
        return Cipher.getInstance(secretAlgorithm);
    }

    @Override
    public void decryptBefore(String secretAlgorithm) {

    }

    @Override
    public void getSecretKeyBefore(String secretAlgorithm) {

    }
}
