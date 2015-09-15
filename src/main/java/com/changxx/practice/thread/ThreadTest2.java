package com.changxx.practice.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author changxx on 15-8-9.
 */
public class ThreadTest2 {

    private static final int COUNT = 1000;

    public static void main(String[] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        for (int j = 0; j < COUNT; j++) {
            TestRunnable testRunnable = new TestRunnable(countDownLatch);
            Thread thread = new Thread(testRunnable);
            thread.start();
        }
        System.out.println(System.currentTimeMillis() - start);

        long start2 = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch2 = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            executorService.execute(new TestRunnable(countDownLatch2));
        }

        executorService.shutdown();

        System.out.println(System.currentTimeMillis() - start2);
    }

    private static class TestRunnable implements Runnable {
        private final CountDownLatch countDownLatch;

        public TestRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            System.out.println("执行");
        }
    }

}
