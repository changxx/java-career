package com.changxx.practice.thread.threadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author changxx on 15-11-8.
 */
public class MyThreadLocal<T> {

    private Map<Thread, T> container = new HashMap<Thread, T>();

    private ReentrantLock lock = new ReentrantLock();

    public void set(T t) {
        try {
            lock.lock();
            container.put(Thread.currentThread(), t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        try {
            lock.lock();
            Thread thread = Thread.currentThread();
            return container.get(thread);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        final MyThreadLocal<String> myThreadLocal = new MyThreadLocal<String>();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThreadLocal.set("thread1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + myThreadLocal.get());
            }
        }, "thread1");
        thread1.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThreadLocal.set("thread2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + myThreadLocal.get());
            }
        }, "thread2");
        thread2.start();
    }

}
