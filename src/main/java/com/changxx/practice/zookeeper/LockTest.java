package com.changxx.practice.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

/**
 * @author changxx on 15-7-21.
 */
public class LockTest {

    private ZooKeeper zk;

    public LockTest(ZooKeeper zk) {
        this.zk = zk;
    }

    void getLock() throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren("/lock", false);

        String[] nodes = list.toArray(new String[list.size()]);

        Arrays.sort(nodes);

        String myZnode = "myZnode";

        if (myZnode.equals("/lock/" + nodes[0])) {
            //do
        } else {
            waitForLock(nodes[0]);
        }
    }

    void waitForLock(String lower) throws KeeperException, InterruptedException {
        Stat stat = zk.exists("/lock/" + lower, true);
        if (stat != null) {

        } else {
            getLock();
        }
    }

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, null);
        LockTest lockTest = new LockTest(zk);

    }

}
