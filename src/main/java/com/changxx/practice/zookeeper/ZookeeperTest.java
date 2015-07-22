package com.changxx.practice.zookeeper;


import org.apache.zookeeper.*;

import java.util.List;

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

        System.out.println("创建root");
        // 创建一个节点root，数据是mydata，不进行ACL权限控制，节点为永久性的
        zk.create("/root", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("创建/root/children");
        // 在root下面创建一个children znode，数据为children，不进行ACL权限控制，节点为永久性的
        zk.create("/root/children", "children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("获取/root子节点名称");
        // 获得/root节点下面的子节点名称，返回List<Strig>
        List<String> childrenNames = zk.getChildren("/root", true);
        for (int i = 0; i < childrenNames.size(); i++) {
            System.out.println("children:" + i + " name:" + childrenNames.get(i));
        }

        System.out.println("获取/root/children节点下的数据");
        // 获得/root/children节点下的数据，返回byte[]
        byte[] childrenByte = zk.getData("/root/children", true, null);
        System.out.println("children value : " + new String(childrenByte));

        System.out.println("修改/root/children下的数据");
        // 修改/root/children下的数据，第三个参数为版本，如果为-1，那会无视修改的数据版本，直接修改
        zk.setData("/root/children", "children-modify".getBytes(), -1);

        System.out.println("删除/root/children下的数据");
        // 删除/root/children下的数据，第二个参数为版本，-1表示全部
        zk.delete("/root/children", -1);

        System.out.println("删除root");
        zk.delete("/root", -1);

        // 关闭session
        zk.close();
    }

}
