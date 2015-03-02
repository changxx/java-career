package com.changxx.practice.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

    public static void main(String[] args) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:hh:mm:ss");

        System.out.println("开始:" + sdf.format(new Date()));
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {

            public void run() {
                System.out.println("运行中:" + sdf.format(new Date()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        service.scheduleAtFixedRate(task, 2, 3, TimeUnit.SECONDS);
    }
}
