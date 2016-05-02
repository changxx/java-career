package com.changxx.practice.zookeeper;


import org.apache.zookeeper.*;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author changxx on 15-7-20.
 */
public class ZookeeperTest {

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(
                        "触发了" + watchedEvent.getType() + "事件!"
                                + " key: " + watchedEvent.getPath()
                                + " state: " + watchedEvent.getState());
            }

        });

//        System.out.println("创建root");
//        // 创建一个节点root，数据是mydata，不进行ACL权限控制，节点为永久性的
//        zk.create("/root", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//
//        System.out.println("创建/root/children");
//        // 在root下面创建一个children znode，数据为children，不进行ACL权限控制，节点为永久性的
//        zk.create("/root/children", "children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//
//        System.out.println("获取/root子节点名称");
        // 获得/root节点下面的子节点名称，返回List<Strig>
        List<String> childrenNames = zk.getChildren("/locks", true);
        for (int i = 0; i < childrenNames.size(); i++) {
            System.out.println("children:" + i + " name:" + childrenNames.get(i));
        }
//
//        System.out.println("获取/root/children节点下的数据");
//        // 获得/root/children节点下的数据，返回byte[]
//        byte[] childrenByte = zk.getData("/root/children", true, null);
//        System.out.println("children value : " + new String(childrenByte));
//
//        System.out.println("修改/root/children下的数据");
//        // 修改/root/children下的数据，第三个参数为版本，如果为-1，那会无视修改的数据版本，直接修改
//        zk.setData("/root/children", "children-modify".getBytes(), -1);
//
//        System.out.println("删除/root/children下的数据");
//        // 删除/root/children下的数据，第二个参数为版本，-1表示全部
//        zk.delete("/root/children", -1);
//
//        System.out.println("删除root");
        for (int i = 0; i < 10; i++) {
            try {
                zk.delete("/locks/lock_000000000" + i, -1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }

        zk.delete("/locks", -1);

        // 关闭session
        zk.close();
    }

    @Test
    public void test_create() throws Exception {

        final CountDownLatch semaphore = new CountDownLatch(1);

        ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    semaphore.countDown();
                }
                System.out.println(
                        "触发了" + watchedEvent.getType() + "事件!"
                                + " key: " + watchedEvent.getPath()
                                + " state: " + watchedEvent.getState());
            }

        });

        semaphore.await();

        // 临时节点
        String path1 = zk.create("/zk-test-ephemeral", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path1);

        // 临时顺序节点
        String path2 = zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("success create znode: " + path2);

        // 持久节点
        String path3 = zk.create("/zk-test-persistent", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path3);

        // 持久顺序节点
        String path4 = zk.create("/zk-test-persistent-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("success create znode: " + path4);
    }

    private static ZooKeeper zk;

    @Test
    public void test_exit() throws Exception {
        final CountDownLatch semaphore = new CountDownLatch(1);

        zk = new ZooKeeper("localhost:2181", 2000, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(
                        "触发了" + watchedEvent.getType() + "事件!"
                                + " key: " + watchedEvent.getPath()
                                + " state: " + watchedEvent.getState());
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                        semaphore.countDown();
                    } else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
                        System.out.println("node " + watchedEvent.getPath() + " created");
                        try {
                            // 通知一次就不在通知，客户端需要反复注册watcher
                            zk.exists(watchedEvent.getPath(), true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                        System.out.println("node " + watchedEvent.getPath() + " deleted");
                        try {
                            zk.exists(watchedEvent.getPath(), true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
                        System.out.println("node " + watchedEvent.getPath() + " NodeDataChanged");
                        try {
                            zk.exists(watchedEvent.getPath(), true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        semaphore.await();

        String path = "/zk-book";

        // exists接口中注册的watcher，能够对节点创建、删除、数据更新等事件进行监听
        // 无论指定节点是否存在，通过调用exists接口都可以注册watcher
        zk.exists(path, true);

        System.out.println("create znode: " + path);
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("setData znode: " + path);
        zk.setData(path, "123".getBytes(), -1);

        // exists对于指定节点的子节点的各种变换，都不会通知客户端
        System.out.println("create znode: " + path + "/c1");
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("delete znode: " + path + "/c1");
        zk.delete(path + "/c1", -1);

        System.out.println("delete znode: " + path);
        zk.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

}
