package com.changxx.practice.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author changxx on 15-7-22.
 */
public class LockTest implements Watcher {

    private static final String LOCK_ROOT = "/locks";

    private ZooKeeper zk;

    private volatile boolean mutex = false;

    private String lockNode;

    public LockTest(ZooKeeper zk) {
        this.zk = zk;
    }

    public void check() throws InterruptedException, KeeperException {
        lockNode = zk.create(LOCK_ROOT + "/lock_", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("节点 " + lockNode);
        getLock();
    }

    public void getLock() throws InterruptedException, KeeperException {
        List<String> list = zk.getChildren(LOCK_ROOT, false);
        String[] nodes = list.toArray(new String[list.size()]);

        Arrays.sort(nodes);

        if (lockNode.equals(LOCK_ROOT + "/" + nodes[0])) {
            System.out.println("获取锁成功,节点与本相同");
        } else {
            System.out.println("获取锁失败,节点不同");
            waitForLock(nodes[0], lockNode);
        }
    }

    void waitForLock(String lower, final String lockNode) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(LOCK_ROOT + "/" + lower, true);
        if (stat != null) {
            while (!mutex) {
                // 阻塞等待
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            mutex = true;
            getLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, null);

        try {
            Stat s = zk.exists(LOCK_ROOT, false);
            if (s == null) {
                zk.create(LOCK_ROOT, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockTest lockTest = new LockTest(zk);
        lockTest.check();
    }


}

