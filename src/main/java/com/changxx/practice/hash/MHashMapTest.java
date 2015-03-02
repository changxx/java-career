package com.changxx.practice.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author changxiangxiang
 * @date 2013年11月14日 下午7:23:16
 * @description
 */
public class MHashMapTest {

    public static void main(String[] args) {
        MHashMap<String, String> mhm = new MHashMap<String, String>();

        for (int i = 0; i < 1000000; i++) {
            // System.out.println("添加次数:" + (i + 1));
            mhm.put(String.valueOf(i), "xxxxxxxxx" + i);
        }

        for (int i = 0; i < 1000000; i++) {
            System.out.println(mhm.get(String.valueOf(i)));
        }

        Map<String, String> map = new HashMap<String, String>();

    }
}
