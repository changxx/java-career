package com.changxx.practice.thread.threadLocal;

/**
 * @author changxiangxiang
 * @date 2014年10月10日 下午2:14:33
 * @description
 * @since sprint2
 */
public class ThreadLocalDeom implements Runnable {

    private ThreadLocal<Student> local = new ThreadLocal<Student>();

    private Student getStudent() {
        Student student = local.get();
        if (student == null) {
            student = new Student();
            local.set(student);
        }
        return student;
    }

    public static void main(String[] args) {
        Runnable runnable = new ThreadLocalDeom();

        Thread a = new Thread(runnable);
        Thread b = new Thread(runnable);

        a.start();
        b.start();
    }

    public void run() {

        String threadName = Thread.currentThread().getName();

        System.out.println("当前线程：" + threadName);
        Student student = this.getStudent();

        int age = (int) (Math.random() * 100);

        System.out.println(threadName + "设置student年龄" + age);
        student.setAge(age);

        System.out.println(threadName + "第一次读取年龄" + local.get().getAge());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadName + "第二次读取年龄" + local.get().getAge());

    }

}
