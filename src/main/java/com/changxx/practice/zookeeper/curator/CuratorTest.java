package com.changxx.practice.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * CuratorTest
 *
 * @author changxiangxiang
 * @date 2017/5/10
 */
public class CuratorTest {
    /**
     * Zookeeper info
     */
    private static final String ZK_ADDRESS = "127.0.0.1:2181";
    private static final String ZK_PATH = "/zktest";
    private static final String ZK_LOCK_PATH = "/zklock/xx";

    @Test
    public void test1() throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Client API test
        // 2.1 Create node
        String data1 = "hello";
        print("create", ZK_PATH, data1);
        client.create().
                creatingParentsIfNeeded().
                forPath(ZK_PATH, data1.getBytes());

        // 2.2 Get node and data
        print("ls", "/");
        print(client.getChildren().forPath("/"));
        print("get", ZK_PATH);
        print(client.getData().forPath(ZK_PATH));

        // 2.3 Modify data
        String data2 = "world";
        print("set", ZK_PATH, data2);
        client.setData().forPath(ZK_PATH, data2.getBytes());
        print("get", ZK_PATH);
        print(client.getData().forPath(ZK_PATH));

        // 2.4 Remove node
        print("delete", ZK_PATH);
        client.delete().forPath(ZK_PATH);
        print("ls", "/");
        print(client.getChildren().forPath("/"));

        client.close();
    }

    /**
     * Curator提供了三种Watcher(CacheValue)来监听结点的变化：
     * Path CacheValue：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。 多级节点的获取不到
     * Node CacheValue：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。 NodeCache
     * Tree CacheValue：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。 TreeCacheListener
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        PathChildrenCache watcher = new PathChildrenCache(
                client,
                ZK_PATH,
                true    // if cache data
        );
        watcher.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data == null) {
                    System.out.println("No data in event[" + event + "]");
                } else {
                    System.out.println("Receive event: "
                            + "type=[" + event.getType() + "]"
                            + ", path=[" + data.getPath() + "]"
                            + ", data=[" + new String(data.getData()) + "]"
                            + ", stat=[" + data.getStat() + "]");
                }
            }
        });
        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void test4() throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        TreeCache watcher = new TreeCache(
                client,
                ZK_PATH
        );

        watcher.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data == null) {
                    System.out.println("No data in event[" + event + "]");
                } else {
                    System.out.println("Receive event: "
                            + "type=[" + event.getType() + "]"
                            + ", path=[" + data.getPath() + "]"
                            + ", data=[" + new String(data.getData()) + "]"
                            + ", stat=[" + data.getStat() + "]");
                }
            }
        });
        watcher.start();
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void test3() throws Exception {
        // 1.Connect to zk
        final CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                doWithLock(client);
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                doWithLock(client);
            }
        }, "t2");

        t1.start();
        t2.start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void doWithLock(CuratorFramework client) {
        InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);
        try {
            if (lock.acquire(100, TimeUnit.MILLISECONDS)) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static void print(String... cmds) {
        StringBuilder text = new StringBuilder("$ ");
        for (String cmd : cmds) {
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }

    private static void print(Object result) {
        System.out.println(
                result instanceof byte[]
                        ? new String((byte[]) result)
                        : result);
    }


}
