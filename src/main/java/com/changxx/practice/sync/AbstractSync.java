package com.changxx.practice.sync;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.joda.time.DateTime;

import com.changxx.practice.sync.call.PeriodSyncCallable;
import com.changxx.practice.sync.call.SyncCallable;
import com.changxx.practice.sync.enums.SyncType;

/**
 * @author changxiangxiang
 * @date 2014年10月28日 下午2:31:25
 * @description
 */
public abstract class AbstractSync<T> implements Sync<T> {

    protected Integer threadPoolSize = 3;   // 线程池大小
    protected Integer singleSyncSize = 1000;                                       // 单次同步数据量
    protected boolean pool           = true;                                       // 是否多线程（true是，false否）

    public int sync(Date day) {
        long syncStart = System.currentTimeMillis();

        int num = 0;
        int count = this.count(day);
        int totalPage = count / singleSyncSize + (count % singleSyncSize == 0 ? 0 : 1);

        String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(day);

        System.out.print("数据同步，日期：" + dayStr + "， 总量：" + count + "， 总页数：" + totalPage);

        System.out.print("删除数据，日期：" + dayStr);
        this.remove(day);

        if (pool) {

            // 多线程
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
            List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

            for (int i = 1; i <= totalPage; i++) {
                SyncCallable<T> callable = new SyncCallable<T>(i, singleSyncSize, day, this);
                futures.add(executorService.submit(callable));
            }

            executorService.shutdown();

            for (Future<Integer> future : futures) {
                try {
                    num += future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } else {

            // 单线程
            for (int i = 1; i <= totalPage; i++) {
                SyncCallable<T> callable = new SyncCallable<T>(i, singleSyncSize, day, this);
                try {
                    num += callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        long consume = System.currentTimeMillis() - syncStart;
        System.out.print("数据同步数据" + num + "条，耗时：" + consume);

        return num;
    }

    public int sync(Date start, Date end) {
        int num = 0;

        Date _start = start;

        if (pool) {

            // 多线程
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
            List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

            while (_start.before(end) || _start.equals(end)) {
                PeriodSyncCallable<T> callable = new PeriodSyncCallable<T>(singleSyncSize, _start, this);
                futures.add(executorService.submit(callable));

                _start = new DateTime(_start).plusDays(1).toDate();
            }

            executorService.shutdown();

            for (Future<Integer> future : futures) {
                try {
                    num += future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } else {

            // 单线程
            while (_start.before(end)) {
                PeriodSyncCallable<T> callable = new PeriodSyncCallable<T>(singleSyncSize, _start, this);
                try {
                    num += callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return num;
    }

    /**
     * 统计一天的数据量
     */
    public abstract int count(Date day);

    /**
     * 根据日期分页查询
     */
    public abstract List<T> findList(Date day, Integer index, Integer pageSize);

    /**
     * 批量插入
     */
    public abstract int insert(List<T> list);

    /**
     * 删除某日期数据
     */
    public abstract void remove(Date date);

    /**
     * 获取同步类型
     */
    public abstract SyncType getSyncType();

    public Integer getThreadPoolSize() {
        return threadPoolSize;
    }

    /**
     * 设置线程池大小，默认3
     */
    public void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public Integer getSingleSyncSize() {
        return singleSyncSize;
    }

    /**
     * 设置单次同步数据量，默认1000
     */
    public void setSingleSyncSize(Integer singleSyncSize) {
        this.singleSyncSize = singleSyncSize;
    }

    public boolean isPool() {
        return pool;
    }

    /**
     * @param ：默认 true使用多线程，false单线程
     */
    public void setPool(boolean pool) {
        this.pool = pool;
    }

}
