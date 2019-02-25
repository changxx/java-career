package com.changxx.practice.localcache;

import java.lang.ref.WeakReference;

public class CacheValue<T> extends WeakReference<T> {

    private long updateTime = 0L;

    private long expireTime = 0L;

    /**
     * 上一次访问或者更新的时间
     */
    private long lastAccessTime = 0L;

    public CacheValue(T referent) {
        super(referent);
        this.updateTime = System.currentTimeMillis();
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "CacheValue{" +
                "updateTime=" + updateTime +
                ", expireTime=" + expireTime +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}
