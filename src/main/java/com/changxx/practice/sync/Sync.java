package com.changxx.practice.sync;

import java.util.Date;

/**
 * @author changxiangxiang
 * @date 2014年10月28日 下午2:16:18
 * @description
 */
public interface Sync<T> {

    /**
     * 同步主方法：一天
     */
    public int sync(Date day);

    /**
     * 同步主方法：多个日期
     */
    public int sync(Date start, Date end);

}
