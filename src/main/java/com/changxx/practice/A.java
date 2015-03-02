package com.changxx.practice;

/**
 * @author changxiangxiang
 * @date 2014年10月8日 下午6:14:54
 * @description
 * @since sprint2
 */
public class A {

    public A() {
        System.out.println("A3");
    }

    public A(String s) {
        System.out.println("A3");
    }

    {
        System.out.println("A1");
    }
    static {
        System.out.println("A2");
    }
}
