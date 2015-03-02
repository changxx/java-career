package com.changxx.practice.hash;

/**
 * @author changxiangxiang
 * @date 2013年11月13日 下午10:37:56
 * @description
 */
public class MHashMap<K, V> {

    private int           size;

    private static int    init_capacity = 16;

    private Entry<K, V>[] container;

    private static float  load_factor   = 0.75f;

    private int           threshold;

    class Entry<EK, EV> {

        Entry<EK, EV> next; // 下一个结点
        EK            key;  // key
        EV            value; // value
        int           hash; // 这个key对应的hash码

        // 构造方法
        Entry(EK k, EV v, int hash) {
            this.key = k;
            this.value = v;
            this.hash = hash;

        }

        // 相应的getter()方法

    }

    public MHashMap(int init_capaticy) {
        threshold = (int) (init_capaticy * load_factor);
        container = new Entry[init_capaticy];
    }

    public MHashMap() {
        this(init_capacity);
    }

    public boolean put(K k, V v) {
        int hash = k.hashCode();
        Entry<K, V> temp = new Entry<K, V>(k, v, hash);
        if (setEntry(temp, container)) {
            size++;
            return true;
        }
        return false;
    }

    public V get(K k) {
        int hash = k.hashCode();
        int index = indexFor(hash, container.length);
        // 对应元素
        Entry<K, V> entry = container[index];
        if (entry == null) {
            return null;
        }
        // 若不为空，遍历链表，比较k是否相等,如果k相等，则返回该value
        while (entry != null) {
            if (entry.hash == hash && (entry.key == k || entry.key.equals(k))) {
                return entry.value;
            }
            entry = entry.next;
        }
        // 如果遍历完了不相等，则返回空
        return null;
    }

    /**
     * 将指定的结点temp添加到指定的hash表table当中
     * @param temp
     * @return
     */
    private boolean setEntry(Entry<K, V> temp, Entry<K, V>[] table) {
        // 根据hash值找到下标
        int index = indexFor(temp.hash, table.length);
        // 对应元素
        Entry<K, V> entry = table[index];
        if (entry != null) {
            // 遍历下标存储的链表
            while (entry != null) {
                // 这个地方注意比较的顺序
                if (temp.hash == entry.hash && (temp.key == entry.key || temp.key.equals(entry.key))) {
                    // 相等就不需要存储了
                    return false;
                } else {
                    // 不相等比较下一个元素
                    if (entry.next != null) {
                        entry = entry.next;
                    } else {
                        // 下一个元素不存在说明到达队尾，可以把元素放在队尾
                        break;
                    }
                }
            }
            // 遍历到队尾，没有相同元素，就将元素挂在队尾
            addEntryLast(entry, temp);
        } else {
            // 元素不存在，直接存储
            setFirstEntry(temp, index, table);
        }

        return true;
    }

    private void setFirstEntry(Entry<K, V> temp, int index, Entry<K, V>[] table) {
        // 判断当前容量是否超标，如果超标，调用扩容方法
        if (size > threshold) {
            resize(table.length * 2);
        }
        // 设置元素
        table[index] = temp;
        // 因为每次设置后都是新的链表，需要将其后接的结点都去掉
        temp.next = null;
    }

    private void addEntryLast(Entry<K, V> entry, Entry<K, V> temp) {
        if (size > threshold) {
            resize(container.length * 2);
        }
        entry.next = temp;
    }

    private void resize(int newCapacity) {
        System.out.println("重新初始化大小开始，当前容器大小" + container.length + "  当前阀值" + threshold);
        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        container = newTable;
        threshold = (int) (newCapacity * load_factor);
        System.out.println("重新初始化大完成，当前容器大小" + container.length + "  当前阀值" + threshold);
    }

    /**
     * 扩容的方法,这个是里面比较耗性能的
     * @param newSize 新容器大小
     */
    private void transfer(Entry<K, V>[] newTable) {
        Entry<K, V>[] src = container;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            Entry<K, V> e = src[j];
            if (e != null) {
                src[j] = null;
                while (e != null) {
                    Entry<K, V> next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                }
            }
        }
    }

    /**
     * 根据hash码，容器数组的长度,计算该哈希码在容器数组中的下标值 ！！相当于对hashcode与数组长度的模运算，这样在元素的分布相对来说比较均匀
     * @param hashcode
     * @param containerLength
     * @return
     */
    private int indexFor(int hashcode, int containerLength) {
        return hashcode & (containerLength - 1);

    }

}
