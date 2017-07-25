package com.changxx.practice.schedule;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimerTest
 *
 * @author changxiangxiang
 * @date 2017/5/30
 */
public class TimerTest extends TimerTask {

    public void run() {
        System.out.println("xxxx");
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay1 = 1000;
        long period1 = 1000;
        // 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
        timer.schedule(new TimerTest(), delay1, period1);
        long delay2 = 2 * 1000;
        long period2 = 2000;
        // 从现在开始 2 秒钟之后，每隔 2 秒钟执行一次 job2
        timer.schedule(new TimerTest(), delay2, period2);
    }

}
