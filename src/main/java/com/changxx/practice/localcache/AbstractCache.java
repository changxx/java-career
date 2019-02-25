package com.changxx.practice.localcache;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

    private CacheLoader<K, V> loader;

    private CacheBuilder<? super K, ? super V> builder;

    public abstract CacheValue<V> getVal(K k);

    public abstract void setVal(K k, CacheValue<V> v);

    public abstract void remove(K key);

    public AbstractCache(CacheLoader<K, V> loader, CacheBuilder<? super K, ? super V> builder) {
        this.loader = loader;
        this.builder = builder;
    }

    private V reload(K key, CacheValue<V> existCacheValue) {
        V v = this.reload(key);
        if (existCacheValue != null && existCacheValue.getLastAccessTime() != 0L && System.currentTimeMillis() - existCacheValue.getLastAccessTime() > builder.getInvalidateTime()) {
            // 太久没访问，就清掉了
            this.remove(key);
        }
        return v;
    }

    public V getIfPersent(K key) {
        CacheValue<V> cacheValue = this.getVal(key);
        if (cacheValue != null) {
            if (cacheValue.getExpireTime() > System.currentTimeMillis()) {
                long now = System.currentTimeMillis();
                cacheValue.setLastAccessTime(now);
                return cacheValue.get();
            }
            if (System.currentTimeMillis() - cacheValue.getLastAccessTime() > builder.getInvalidateTime()) {
                // 太久没访问，就清掉了
                this.remove(key);
            }
        }
        return null;
    }

    public V get(K key) {
        CacheValue<V> cacheValue = this.getVal(key);
        if (cacheValue == null) {
            // 没加载或者被删掉，去加载
            return reload(key);
        }
        long now = System.currentTimeMillis();
        if (cacheValue.getExpireTime() > now) {
            // 未过期，直接返回
            cacheValue.setLastAccessTime(now);
            return cacheValue.get();
        }
        // 过期，重新加载
        return reload(key, cacheValue);
    }

    public void put(K key, V value) {
        long now = System.currentTimeMillis();
        CacheValue<V> cacheValue = new CacheValue<V>(value);
        // 计算过期时间
        cacheValue.setExpireTime(now + builder.getExpireAfterWrite());
        cacheValue.setLastAccessTime(now);
        this.setVal(key, cacheValue);
    }

    public V reload(K key) {
        V v = this.loader.load(key);
        if (!builder.isCacheNull() && v == null) {
            // 结果为空的不缓存
            return null;
        }
        this.put(key, v);
        return v;
    }

    public void invalidate(Object key) {

    }
}
