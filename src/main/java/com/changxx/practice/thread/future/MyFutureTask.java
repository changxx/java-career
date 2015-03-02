package com.changxx.practice.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author changxiangxiang
 * @date 2014年10月10日 下午4:52:50
 * @description
 * @since sprint2
 */
public class MyFutureTask {

    /**
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        final ExecutorService exe = Executors.newFixedThreadPool(3);
        Callable<String> call = new Callable<String>() {

            public String call() throws InterruptedException {
                return "Thread is finished";
            }
        };
        Future<String> task = exe.submit(call);
        String obj = task.get();
        System.out.println(obj + "进程结束");
        System.out.println("总进程结束");
        exe.shutdown();
    }
}

class MyThreadTest implements Runnable {

    private String str;

    public MyThreadTest(String str) {
        this.str = str;
    }

    public void run() {
        this.setStr("allen" + str);
    }

    public void addString(String str) {
        this.str = "allen:" + str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
