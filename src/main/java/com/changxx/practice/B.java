package com.changxx.practice;


/**
 * @author changxiangxiang
 * @date 2014年10月8日 下午6:15:18
 * @description
 * @since sprint2
 */
public class B extends A {

    public B() {
        System.out.println("B3");
    }

    {
        System.out.println("B1");
    }
    static {
        System.out.println("B2");
    }

    public static void main(String[] args) {
        A a1 = new B();
        B a2 = new B();
    }
}
