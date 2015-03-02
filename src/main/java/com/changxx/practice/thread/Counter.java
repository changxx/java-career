package com.changxx.practice.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午3:25:12
 * @description
 * @since sprint2
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger();

    public int            i       = 0;

    public static void main(String[] args) {
        final Counter counter = new Counter();

        List<Thread> ts = new ArrayList<Thread>();

        long start = System.currentTimeMillis();

        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        counter.count();
                        counter.sateCount();
                    }
                }
            });

            ts.add(t);
        }

        for (Thread tr : ts) {
            tr.start();
        }

        // 等待所有线程执行完成
        for (Thread tr : ts) {
            try {
                tr.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(counter.i);

        System.out.println(counter.atomicI.get());

        System.out.println(System.currentTimeMillis() - start);
    }

    private void sateCount() {
        // atomicI.getAndIncrement();

        // 自旋CAS实现的基本思路就是循环进行CAS操作直到成功为止。
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {
        i++;
    }

}
