package com.changxx.practice.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author changxiangxiang
 * @date 2014年10月20日 下午2:45:07
 * @description
 */
public class ExecutorServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            futures.add(executorService.submit(new MyCall(i)));
        }

        executorService.shutdown();

        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }
    }

}

class MyCall implements Callable<Integer> {

    private int num;

    public MyCall(int num) {
        super();
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer call() throws Exception {
        Thread.sleep(500);
        System.out.println("call run num : " + num);
        return num;
    }
}
