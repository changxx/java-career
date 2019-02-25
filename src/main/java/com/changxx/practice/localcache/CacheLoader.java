package com.changxx.practice.localcache;

public interface CacheLoader<K, V> {

    public V load(K key);

}
