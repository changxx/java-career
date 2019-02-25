package com.changxx.practice.localcache;

public class CacheBuilder<K, V> {

    // 最后一次访问之后多少时间后移除
    private long expireAfterAccess;

    // 被创建或值被替换后多少时间移除（这里只实现这一个），单位毫秒
    private long expireAfterWrite;

    // 为空的是否缓存
    private boolean cacheNull;

    /**
     * 被动清楚，缓存里放了多久没访问，清掉（默认24小时，这里就不改了）
     */
    private long invalidateTime = 1000L * 60 * 60 * 24;

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<Object, Object>();
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build(CacheLoader<K1, V1> cacheLoader, Class c) {
        if (c.getSimpleName().equals(ConcurrentHashMapLocalCache.class.getSimpleName())) {
            return new ConcurrentHashMapLocalCache<K1, V1>(cacheLoader, this);
        } else if (c.getSimpleName().equals(SegmentLocalCache.class.getSimpleName())) {
            return new SegmentLocalCache<K1, V1>(cacheLoader, this);
        }
        return null;
    }

    public long getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public CacheBuilder setExpireAfterAccess(long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
        return this;
    }

    public long getExpireAfterWrite() {
        return expireAfterWrite;
    }

    public CacheBuilder setExpireAfterWrite(long expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
        return this;
    }

    public boolean isCacheNull() {
        return cacheNull;
    }

    public void setCacheNull(boolean cacheNull) {
        this.cacheNull = cacheNull;
    }

    public long getInvalidateTime() {
        return invalidateTime;
    }

    public void setInvalidateTime(long invalidateTime) {
        this.invalidateTime = invalidateTime;
    }
}
