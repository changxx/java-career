package com.changxx.practice.sms4;

public class SMS4Test {

    private static final byte[] in  = { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54,
            0x32, 0x10             };

    private static final byte[] key = { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54,
            0x32, 0x10             };

    public static void main(String[] args) {
        SMS4Test sms4Test = new SMS4Test();

        System.out.println(sms4Test.in.length);
    }
}
