package com.changxx.practice.thread.storage2;

import java.util.concurrent.BlockingQueue;

/**
 * @author changxiangxiang
 * @date 2014年8月27日 下午2:14:49
 * @description
 * @since sprint2
 */
public class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        super();
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                String uuid = queue.take();
                System.out.println(Thread.currentThread() + "Consumer uuid: " + uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
