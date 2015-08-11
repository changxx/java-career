package com.changxx.practice.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * TestMainClient
 * <p/>
 * Author By: junshan
 * Created Date: 2010-9-7 14:11:44
 */
public class TestMainClient implements Watcher {
    protected static ZooKeeper zk = null;
    protected static Object mutex = new Object();
    int sessionTimeout = 10000;
    protected String root;

    public TestMainClient(String connectString) {
        if (zk == null) {
            try {
                zk = new ZooKeeper(connectString, sessionTimeout, this);
            } catch (IOException e) {
                zk = null;
            }
        }
    }

    synchronized public void process(WatchedEvent event) {
        System.out.println(
                "触发了" + event.getType() + "事件!"
                        + " key: " + event.getPath()
                        + " state: " + event.getState());
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
