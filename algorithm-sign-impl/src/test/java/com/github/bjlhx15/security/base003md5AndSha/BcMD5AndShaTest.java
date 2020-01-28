package com.github.bjlhx15.security.base003md5AndSha;

import org.junit.Test;

import static org.junit.Assert.*;

public class BcMD5AndShaTest {

    @Test
    public void msgSafeBase() throws Exception {
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.MD2));
//        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.MD3)); //没有
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.MD4));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.MD5));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.SHA1));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.SHA224));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.SHA256));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.SHA384));
        System.out.println(BcMD5AndSha.msgSafeBase("测试",BcMD5AndSha.SHA512));
    }
}