package com.cxy.oi.kernel.util;

import java.util.LinkedHashMap;
import java.util.Map;


public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final String TAG = "LRUCache";

    private final int maxSize;


    public LRUCache(int maxSize) {
        super(16, 0.75f, true);
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean delete = size() > maxSize;
        if (delete) {
            Log.i(TAG, "see you ~~~~~~  %s ", eldest.toString());
        }
        return delete;
    }

}
