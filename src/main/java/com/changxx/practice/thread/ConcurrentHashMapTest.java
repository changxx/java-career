package com.changxx.practice.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author changxiangxiang
 * @date 2014年7月3日 下午1:55:53
 * @description
 * @since sprint2
 */
public class ConcurrentHashMapTest {

    private static Map<Long, ServiceDO> widgetCacheMap = new ConcurrentHashMap<Long, ServiceDO>();

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            Thread tt = new Thread(new Rund());
            tt.start();
        }

    }

    static class Rund implements Runnable {

        public void run() {
            test();
        }

        /**
         * 1W次，总有那么几次线程不安全
         */
        public void test() {
            synchronized ("") {
                ConcurrentHashMapTest tt = new ConcurrentHashMapTest();
                tt.set();
                int s1 = widgetCacheMap.get(1L).getStatus();
                tt.change();
                int s2 = widgetCacheMap.get(1L).getStatus();
                if (s1 == s2) {
                    System.out.println(s1 + ":" + s2);
                }
            }
        }

    }

    public void set() {
        ServiceDO ss = new ServiceDO();
        ss.setStatus(1);
        widgetCacheMap.put(1L, ss);
    }

    public void change() {
        ServiceDO ss = new ServiceDO();
        ss.setStatus(2);
        widgetCacheMap.put(1L, ss);
    }
}
