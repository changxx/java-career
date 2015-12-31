package com.changxx.practice.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author changxx on 15-12-31.
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        latch.countDown();

        System.out.println(latch.getCount());

        new Thread(new HH(latch)).start();

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("await end");

    }
}

class HH implements Runnable {

    private CountDownLatch latch;

    public HH(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(latch.getCount());
            latch.countDown();
        }
    }
}