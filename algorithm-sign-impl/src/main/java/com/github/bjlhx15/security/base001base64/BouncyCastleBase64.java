package com.github.bjlhx15.security.base001base64;

import org.bouncycastle.util.encoders.Base64;

public class BouncyCastleBase64 {

    public String base64Encoder(String msg, String charsetName) throws Exception {
        return new String(Base64.encode(msg.getBytes(charsetName)), charsetName);
    }

    public String base64Decoder(String msg, String charsetName) throws Exception {
        return new String(Base64.decode(msg), charsetName);
    }
}
