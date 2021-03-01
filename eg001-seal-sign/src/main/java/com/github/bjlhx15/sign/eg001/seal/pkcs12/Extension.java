package com.github.bjlhx15.sign.eg001.seal.pkcs12;

/**
 * @author lihongxu6
 * @version 1.0
 * @className Extension
 * @description TODO
 * @date 2021-01-20 13:57
 */
public class Extension {
    private String oid;

    private boolean critical;

    private byte[] value;

    public String getOid() {
        return oid;
    }

    public byte[] getValue() {
        return value;
    }
    public boolean isCritical() {
        return critical;
    }
}
