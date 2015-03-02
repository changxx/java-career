package com.changxx.practice.sync.call;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.changxx.practice.sync.AbstractSync;

/**
 * @author changxiangxiang
 * @date 2014年10月28日 下午2:23:33
 * @description
 */
public class SyncCallable<T> implements Callable<Integer> {

    private int             index;   // 分页
    private int             pageSize;                                         // 分页大小
    private Date            day;     // 同步日期
    private AbstractSync<T> daySync;

    public SyncCallable(int index, int pageSize, Date day, AbstractSync<T> abstractDaySync) {
        super();
        this.index = index;
        this.pageSize = pageSize;
        this.day = day;
        this.daySync = abstractDaySync;
    }

    public Integer call() throws Exception {
        String dayStr = new SimpleDateFormat("yyyy-MM-dd").format(day);
        System.out.print("数据同步 index:" + index + " , pageSize:" + pageSize + ", day:" + dayStr);

        List<T> list = daySync.findList(day, index, pageSize);

        System.out.print("数据同步 index:" + index + ",插入" + list.size() + "条考勤记录");

        return daySync.insert(list);
    }

}
