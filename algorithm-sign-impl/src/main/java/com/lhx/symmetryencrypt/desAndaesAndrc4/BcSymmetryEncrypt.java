package com.lhx.symmetryencrypt.desAndaesAndrc4;

import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BcSymmetryEncrypt {
	/**
	 * 试了des 其他没试
	 *
	 * @param algorithm
	 *            DES key size must be equal to 56 <br/>
	 * @param keySize
	 * @param src
	 */
	public static void encryptAndDencrypt(String algorithm, int keySize, String src) {
		try {
			Security.addProvider(new BouncyCastleProvider());
			// 生成key
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, "BC");
			keyGenerator.getProvider();
			keyGenerator.init(keySize);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] bytesKey = secretKey.getEncoded();
			System.out.println("key:" + Base64.getEncoder().encodeToString(bytesKey));
			// key的转换
			DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

			// 加密
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println(algorithm + "加密：" + Hex.encodeHexString(result));
			System.out.println(algorithm + "加密：" + Base64.getEncoder().encodeToString(result));

			// 解密
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result = cipher.doFinal(result);
			System.out.println(algorithm + "解密：" + new String(result));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
