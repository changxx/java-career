package com.changxx.practice.zookeeper.lock;

import com.changxx.practice.zookeeper.ZookeeperClient;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author changxx on 15-7-21.
 */
public class DistributedLock {

    private static final byte[] data = {0x12, 0x34};

    private ZooKeeper zookeeper = ZookeeperClient.getInstance();

    private final String root;

    private String id;

    private LockNode idName;

    private String ownerId;

    private String lastChildId;

    private Throwable other = null;

    private KeeperException exception = null;

    private InterruptedException interrupt = null;

    public DistributedLock(String root) {
        this.root = root;
        ensureExists(root);
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZooKeeper zk = ZookeeperClient.getInstance();
        List<String> childrenNames = zk.getChildren("/kl-tomcat/kaola-mykaola-web", false);
        for (int i = 0; i < childrenNames.size(); i++) {
            System.out.println("name:" + childrenNames.get(i));
            byte[] childrenByte = zk.getData("/kl-tomcat/kaola-mykaola-web/" + childrenNames.get(i), false, null);
            String str = new String(childrenByte);
            System.out.println(str);
        }
    }

    public void lock() throws InterruptedException, KeeperException {
        if (exception != null) {
            throw exception;
        }

        if (interrupt != null) {
            throw interrupt;
        }

        if (other != null) {
            throw new NestableRuntimeException(other);
        }

        if (isOwner()) {
            return;
        }

        BooleanMutex mutex = new BooleanMutex();

    }

    private void ensureExists(final String path) {
        try {
            Stat stat = zookeeper.exists(path, false);
            if (stat == null) {
                return;
            }

            zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            exception = e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            interrupt = e;
        }
    }

    private Boolean acquireLock(final BooleanMutex mutex) {
        try {
            do {
                if (id == null) {//构建当前lock的唯一标识
                    long sessionId = zookeeper.getSessionId();
                    String prefix = "x-" + sessionId + "-";
                    //如果第一次，则创建一个节点
                    String path = zookeeper.create(root + "/" + prefix, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    int index = path.lastIndexOf("/");
                    id = path.substring(index + 1);
                    idName = new LockNode(id);
                }

                if (id != null) {
                    List<String> names = zookeeper.getChildren(root, false);
                    if (names.isEmpty()) {
                        id = null;//异常情况，重新创建一个
                    } else {
                        //对节点进行排序
                        SortedSet<LockNode> sortedNames = new TreeSet<LockNode>();
                        for (String name : names) {
                            sortedNames.add(new LockNode(name));
                        }

                        if (sortedNames.contains(idName) == false) {
                            id = null;//清空为null，重新创建一个
                            continue;
                        }

                        //将第一个节点做为ownerId
                        ownerId = sortedNames.first().getName();
                        if (mutex != null && isOwner()) {
                            mutex.set(true);//直接更新状态，返回
                            return true;
                        } else if (mutex == null) {
                            return isOwner();
                        }

                        SortedSet<LockNode> lessThanMe = sortedNames.headSet(idName);
                        if (!lessThanMe.isEmpty()) {
                            //关注一下排队在自己之前的最近的一个节点
                            LockNode lastChildName = lessThanMe.last();
                            lastChildId = lastChildName.getName();
                            //异步watcher处理
                            zookeeper.exists(root + "/" + lastChildId, new Watcher() {

                                public void process(WatchedEvent event) {
                                    acquireLock(mutex);
                                }

                            });

                            if (lastChildId == null) {
                                acquireLock(mutex);// 如果节点不存在，需要自己重新触发一下，watcher不会被挂上去
                            }
                        } else {
                            if (isOwner()) {
                                mutex.set(true);
                            } else {
                                id = null;// 可能自己的节点已超时挂了，所以id和ownerId不相同
                            }
                        }
                    }
                }
            } while (id == null);
        } catch (KeeperException e) {
            exception = e;
            if (mutex != null) {
                mutex.set(true);
            }
        } catch (InterruptedException e) {
            interrupt = e;
            if (mutex != null) {
                mutex.set(true);
            }
        } catch (Throwable e) {
            other = e;
            if (mutex != null) {
                mutex.set(true);
            }
        }

        if (isOwner() && mutex != null) {
            mutex.set(true);
        }
        return Boolean.FALSE;
    }


    /**
     * 判断当前是不是锁的owner
     *
     * @return
     */
    public boolean isOwner() {
        return id != null && ownerId != null && id.equals(ownerId);
    }

}
