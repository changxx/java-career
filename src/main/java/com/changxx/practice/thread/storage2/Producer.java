package com.changxx.practice.thread.storage2;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * @author changxiangxiang
 * @date 2014年8月27日 下午2:11:46
 * @description
 * @since sprint2
 */
public class Producer implements Runnable {

    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        super();
        this.queue = queue;
    }

    public void run() {
        while(true){
            
            String uuid = UUID.randomUUID().toString();
            try {
                queue.put(uuid);
                System.out.println(Thread.currentThread() + "Producer uuid: " + uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
