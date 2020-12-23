package com.cxy.oi.kernel.util;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private static final String TAG = "LRUCache";

    private final int maxSize;
    private Map<K, V> map;


    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.map = new HashMap<>();
    }

    public void put(K key, V value) {
        if (map.containsKey(key) || map.size() < maxSize) {
            map.put(key, value);
        }
    }

    public V get(K key) {
        V value = map.get(key);
        reWeight();
        return value;
    }

    private void reWeight() {

    }

}
