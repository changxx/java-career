package com.changxx.practice.thread.future;

import java.util.concurrent.*;

/**
 * @author changxx on 15-7-15.
 */
public class FutureTest {
    public static void main(String[] args) {

        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("task execute");
                Thread.sleep(5000);
                return "task end";
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(futureTask);

        try {
            String result = futureTask.get(300, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // executor.shutdown();
    }
}
