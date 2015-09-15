package com.changxx.practice.thread;

/**
 * @author changxx on 15-8-9.
 */
public class ThreadTest {

    public static void main(String[] args) {
        int i = 0;
        while (true) {
            TestRunnable testRunnable = new TestRunnable();
            Thread thread = new Thread(testRunnable);
            thread.start();
            System.out.println(i++);
        }
    }

    private static class TestRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
