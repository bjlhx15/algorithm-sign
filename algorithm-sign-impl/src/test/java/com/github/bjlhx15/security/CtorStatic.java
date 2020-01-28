package com.github.bjlhx15.security;

/**
 * 静态代码块>构造代码块>构造函数>普通代码块
 */
public class CtorStatic {
    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("构造代码块");
    }
}
