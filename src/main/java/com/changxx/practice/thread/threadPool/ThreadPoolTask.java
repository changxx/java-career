package com.changxx.practice.thread.threadPool;

/**
 * @author changxx on 15-7-16.
 */
public class ThreadPoolTask implements Runnable {

    private Object attachData;

    ThreadPoolTask(Object tasks) {
        this.attachData = tasks;
    }

    public void run() {

        System.out.println("开始执行任务：" + attachData);

        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        attachData = null;
    }

    public Object getTask() {
        return this.attachData;
    }

}
