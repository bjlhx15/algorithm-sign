package com.github.bjlhx15.security.base003md5AndSha;

import org.junit.Test;

import static org.junit.Assert.*;

public class JdkMD5AndShaTest {

    @Test
    public void msgSafeBase() throws Exception {
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.MD2));
//        System.out.println(MD5AndSha.msgSafeBase("测试",MD5AndSha.MD3));//没有
//        System.out.println(MD5AndSha.msgSafeBase("测试",MD5AndSha.MD4));//没有
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.MD5));
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.SHA1));
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.SHA224));
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.SHA256));
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.SHA384));
        System.out.println(JdkMD5AndSha.msgSafeBase("测试",JdkMD5AndSha.SHA512));
    }

    @Test
    public void hashCheck() throws Exception {
        System.out.println("AaAaAa".hashCode());//1952508096
        System.out.println("BBAaBB".hashCode());//1952508096
    }
}