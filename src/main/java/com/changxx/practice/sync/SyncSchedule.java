package com.changxx.practice.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changxiangxiang
 * @date 2014年11月3日 上午11:10:05
 * @description
 */
public class SyncSchedule<T> {

    private List<Sync<T>> syncList = new ArrayList<Sync<T>>();

    public List<Sync<T>> getSyncList() {
        return syncList;
    }

    public void setSyncList(List<Sync<T>> syncList) {
        this.syncList = syncList;
    }

}
