package com.changxx.practice.thread.storage;

import java.util.LinkedList;

/**
 * @author changxiangxiang
 * @date 2014年8月7日 上午10:19:39
 * @description
 * @since sprint2
 */
public class Storage {

    // 仓库最大存储量
    private final int          MAX_SIZE = 100;

    // 仓库存储的载体
    private LinkedList<Object> list     = new LinkedList<Object>();

    public void produce(int num) {

        synchronized (list) {

            while (num + list.size() > MAX_SIZE) {

                System.out.println("【要生产的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");

                try {
                    // 由于超过仓库最大存储量，生产阻塞
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            for (int i = 1; i <= num; ++i) {
                list.add(new Object());
            }

            System.out.println("【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    public void consume(int num) {

        synchronized (list) {

            while (num > list.size()) {

                System.out.println("【要消费的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i) {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    public LinkedList<Object> getList() {
        return list;
    }

    public void setList(LinkedList<Object> list) {
        this.list = list;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }

}
