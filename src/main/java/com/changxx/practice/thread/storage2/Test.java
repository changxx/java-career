package com.changxx.practice.thread.storage2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author changxiangxiang
 * @date 2014年8月27日 下午2:16:21
 * @description
 * @since sprint2
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<String>(5);

        Producer producer1 = new Producer(blockingQueue);
        Producer producer2 = new Producer(blockingQueue);

        Consumer consumer = new Consumer(blockingQueue);

        for (int i = 0; i < 15; i++) {
            new Thread(producer1).start();
        }

        for (int i = 0; i < 16; i++) {
            new Thread(producer2).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(consumer).start();
        }

        

    }
}
