package com.changxx.practice.localcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapLocalCache<K, V> extends AbstractCache<K, V> {

    private Map<K, CacheValue<V>> cacheMap = new ConcurrentHashMap<K, CacheValue<V>>();

    public ConcurrentHashMapLocalCache(CacheLoader<K, V> loader, CacheBuilder<? super K, ? super V> builder) {
        super(loader, builder);
    }

    @Override
    public CacheValue<V> getVal(K k) {
        return cacheMap.get(k);
    }

    @Override
    public void setVal(K k, CacheValue<V> v) {
        this.put(k, v.get());
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }
}
