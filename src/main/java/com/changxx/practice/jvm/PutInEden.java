package com.changxx.practice.jvm;

/**
 * @author changxx on 15-9-22.
 */
public class PutInEden {

    public static void main(String[] args) {
        byte[] b1, b2, b3, b4;
        b1 = new byte[1024 * 1024 * 10]; // 分配1M堆空间
        b2 = new byte[1024 * 1024];
        b3 = new byte[1024 * 1024];
        b4 = new byte[1024 * 1024];
    }

}
