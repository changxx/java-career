package com.changxx.practice.zookeeper.locks;

import com.changxx.practice.zookeeper.TestMainClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

/**
 * locks
 * <p/>
 * Author By: junshan
 * Created Date: 2010-9-7 16:49:40
 */
public class Locks extends TestMainClient implements Runnable {
    public static final Logger logger = Logger.getLogger(Locks.class);
    private String myZnode;

    public Locks(String connectString, String root) {
        super(connectString);
        this.root = root;
        if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                logger.error(e);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }

    void getLock() throws KeeperException, InterruptedException {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        List<String> list = zk.getChildren(root, false);
        String[] nodes = list.toArray(new String[list.size()]);

        Arrays.sort(nodes);

        if (myZnode.equals(root + "/" + nodes[0])) {
            System.out.println(Thread.currentThread().getName() + "获取锁成功,节点与本相同");
            doAction();
        } else {
            System.out.println(Thread.currentThread().getName() + "获取锁失败,节点不同");
            waitForLock(nodes[0]);
        }
    }

    void check() throws InterruptedException, KeeperException {
        myZnode = zk.create(root + "/lock_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(Thread.currentThread().getName() + "創建 myZnode:" + myZnode);
        getLock();
    }

    void waitForLock(String lower) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(root + "/" + lower, true);
        if (stat != null) {
            synchronized (mutex) {
                System.out.println(Thread.currentThread().getName() + "有序号小的节点,等待" + lower);
                mutex.wait();
            }
        } else {
            getLock();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDeleted) {
            System.out.println(Thread.currentThread().getName() + "得到通知");
            super.process(event);
            doAction();
        }
    }

    /**
     * 執行其他任務
     */
    private void doAction() {
        System.out.println(myZnode + "ͬ同步隊列得到同步,可以開始執行任務");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("操作完成 删除node: " + myZnode);
        try {
            zk.delete(myZnode, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.check();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        final String connectString = "localhost:2181";

        for (int i = 0; i < 3; i++) {
            Thread.sleep(100);
            new Thread(new Locks(connectString, "/locks")).start();
        }

        try {
            Thread.sleep(1000 * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
