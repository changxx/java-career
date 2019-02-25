package com.changxx.practice.localcache;

public interface Cache<K, V> {

    V getIfPersent(K key);

    V get(K key);

    void put(K key, V value);

    public V reload(K key);

    void invalidate(Object key);
}
