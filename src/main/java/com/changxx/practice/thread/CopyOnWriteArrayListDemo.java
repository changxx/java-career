package com.changxx.practice.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author changxiangxiang
 * @date 2014年11月12日 下午2:18:16
 * @description CopyOnWriteArrayList是ArrayList
 *              的一个线程安全的变体，其中所有可变操作（add、set等等）都是通过对底层数组进行一次新的复制来实现的这一般需要很大的开销
 *              ，但是当遍历操作的数量大大超过可变操作的数量时，这种方法可能比其他替代方法更 有效。在不能或不想进行同步遍历，
 *              但又需要从并发线程中排除冲突时，它也很有用。 <br/>
 *              “快照”风格的迭代器方法在创建迭代器时使用了对数组状态的引用 。此数组在迭代器的生存期内不会更改
 *              ，因此不可能发生冲突，并且迭代器保证不会抛出ConcurrentModificationException
 *              。创建迭代器以后，迭代器就不会反映列表的添加
 *              、移除或者更改。在迭代器上进行的元素更改操作（remove、set和add）不受支持。
 *              这些方法将抛出UnsupportedOperationException。允许使用所有元素，包括null。
 *              内存一致性效果：当存在其他并发 collection 时，将对象放入CopyOnWriteArrayList之前的线程中的操作
 *              happen-before 随后通过另一线程从CopyOnWriteArrayList中访问或移除该元素的操作。
 *              这种情况一般在多线程操作时，一个线程对list进行修改。一个线程对list进行fore时会出现java.util.
 *              ConcurrentModificationException错误。<br/>
 *              <b>所以最后得出结论：CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里
 *              ，比如缓存。发生修改时候做copy，新老版本分离，保证读的高性能，适用于以读为主的情况。</b>
 */
public class CopyOnWriteArrayListDemo {

    /**
     * 读线程
     */
    private static class ReadTask implements Runnable {

        List<String> list;

        public ReadTask(List<String> list) {
            this.list = list;
        }

        public void run() {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }

    /**
     * 写线程
     */
    private static class WriteTask implements Runnable {

        List<String> list;
        int          index;

        public WriteTask(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }

        public void run() {
            list.remove(index);
            list.add(index, "write_" + index);
        }
    }

    public void run() {
        final int NUM = 10;
        // List<String> list = nefw ArrayList<String>();
        List<String> list = new CopyOnWriteArrayList<String>();
        for (int i = 0; i < NUM; i++) {
            list.add("main_" + i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            executorService.execute(new ReadTask(list));
            executorService.execute(new WriteTask(list, i));
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new CopyOnWriteArrayListDemo().run();
    }
}
