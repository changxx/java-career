package com.changxx.practice.localcache;

import java.util.HashMap;
import java.util.Map;

public class SegmentLocalCache<K, V> extends AbstractCache<K, V> {

    // 分成各个段处理
    private static final int segNum = 65536;

    // 按hashcode分段
    private Segment<K, CacheValue<V>>[] segments = new Segment[segNum];

    public SegmentLocalCache(CacheLoader<K, V> loader, CacheBuilder<? super K, ? super V> builder) {
        super(loader, builder);
    }

    @Override
    public CacheValue<V> getVal(K key) {
        Segment<K, CacheValue<V>> segment = segmentFor(key.hashCode());
        return segment != null ? segment.getVal(key) : null;
    }

    @Override
    public void setVal(K key, CacheValue<V> cacheValue) {
        Segment<K, CacheValue<V>> segment = segmentFor(key.hashCode());
        if (segment == null) {
            segment = new Segment<K, CacheValue<V>>();
            segments[Math.abs(key.hashCode() % segNum)] = segment;
        }
        segment.setVal(key, cacheValue);
    }

    @Override
    public void remove(K key) {
        segmentFor(key.hashCode()).remove(key);
    }

    Segment<K, CacheValue<V>> segmentFor(int hash) {
        return segments[Math.abs(hash % segNum)];
    }

    class Segment<K, V> {
        private ReadWriteLock readWriteLock = new ReadWriteLock();
        private Map<K, V> segMap = new HashMap<K, V>();

        public V getVal(K k) {
            try {
                readWriteLock.readLock();
                return segMap.get(k);
            } catch (InterruptedException e) {
                // ignore
                return null;
            } finally {
                readWriteLock.readUnLock();
            }
        }

        public void setVal(K k, V v) {
            try {
                readWriteLock.writeLock();
                segMap.put(k, v);
            } catch (InterruptedException e) {
                // ignore
            } finally {
                readWriteLock.writeUnLock();
            }
        }

        public void remove(K k) {
            try {
                readWriteLock.writeLock();
                segMap.remove(k);
            } catch (InterruptedException e) {
                // ignore
            } finally {
                readWriteLock.writeUnLock();
            }
        }
    }

    class ReadWriteLock {
        private volatile int read;
        private volatile int write;

        public ReadWriteLock() {
            this.read = 0;
            this.write = 0;
        }

        public synchronized void readLock() throws InterruptedException {
            if (write > 0) {
                wait();
            }
            read++;
        }

        public synchronized void readUnLock() {
            read--;
            notifyAll();
        }

        public synchronized void writeLock() throws InterruptedException {
            if (read > 0 || write > 0) {
                wait();
            }
            write++;
        }

        public synchronized void writeUnLock() {
            write--;
            notifyAll();
        }
    }
}
