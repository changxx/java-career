package com.changxx.practice.thread;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author changxiangxiang
 * @date 2014年8月5日 下午3:36:25
 * @description
 * @since sprint2
 */
public class LinkedBlockingQueueTest {

    public static Queue<String> queue = new LinkedBlockingQueue<String>(10);

    public static void main(String[] args) {
        queue.add("s");
        System.out.println(queue.peek());
        System.out.println(queue);
        System.out.println(queue.poll());
        System.out.println(queue);
    }
}
