package com.changxx.practice.sync.call;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.changxx.practice.sync.AbstractSync;

/**
 * @author changxiangxiang
 * @date 2014年10月28日 下午3:19:08
 * @description
 */
public class PeriodSyncCallable<T> implements Callable<Integer> {

    private int             pageSize;                                               // 分页大小
    private Date            day;     // 同步日期
    private AbstractSync<T> daySync;

    public PeriodSyncCallable(int pageSize, Date day, AbstractSync<T> daySync) {
        super();
        this.pageSize = pageSize;
        this.day = day;
        this.daySync = daySync;
    }

    public Integer call() throws Exception {
        String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(day);
        int num = 0;
        System.out.print("数据同步   day：" + dayStr + "pageSize" + pageSize);

        daySync.remove(day);
        int count = daySync.count(day);
        int totalPage = count / pageSize;

        for (int i = 1; i <= totalPage; i++) {
            List<T> list = daySync.findList(day, i, pageSize);

            System.out.print("数据同步 day:" + dayStr + ",插入" + list.size() + "条考勤记录");
            num += daySync.insert(list);
        }

        return num;
    }

}
