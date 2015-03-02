package com.changxx.practice.thread.storage;

/**
 * @author changxiangxiang
 * @date 2014年8月7日 上午10:19:56
 * @description
 * @since sprint2
 */
public class Producer extends Thread {

    // 每次生产的产品数量
    private int     num;

    // 所在放置的仓库
    private Storage storage;

    public Producer(Storage storage) {
        super();
        this.storage = storage;
    }

    public Producer(int num, Storage storage) {
        super();
        this.num = num;
        this.storage = storage;
    }

    @Override
    public void run() {
        storage.produce(num);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
