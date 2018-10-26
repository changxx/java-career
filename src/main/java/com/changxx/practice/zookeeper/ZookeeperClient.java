package com.changxx.practice.zookeeper;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author changxx on 15-7-21.
 */
public class ZookeeperClient {

    private static ZooKeeper zk = null;

    public static ZooKeeper getInstance() {
        synchronized (ZookeeperClient.class) {
            if (zk == null) {
                try {
                    zk = new ZooKeeper("kaola-soa-zk1.jd.163.org:2181", 2000, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return zk;
    }

}
