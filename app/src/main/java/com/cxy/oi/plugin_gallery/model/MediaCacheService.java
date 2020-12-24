package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;

import com.cxy.oi.kernel.util.LRUCache;
import com.cxy.oi.kernel.util.Log;


/**
 *  二级缓存模型：内存Map缓存 + 硬盘缓存
 */
public class MediaCacheService {
    private static final String TAG = "MediaCacheService";

    private static final int MAX_MEM_CACHE_SIZE = 300;
    private final LRUCache<String, Bitmap> galleryMemCache;

    public MediaCacheService() {
        galleryMemCache = new LRUCache<>(MAX_MEM_CACHE_SIZE);

    }

    public void saveBitmapToMemCache(String cacheKey, Bitmap bitmap) {
        galleryMemCache.put(cacheKey, bitmap);
    }

    public Bitmap getBitmapFromMemCache(String cacheKey) {
        Log.i(TAG, "[getBitmapFromMemCache] curr size: %s", galleryMemCache.size());
        return galleryMemCache.get(cacheKey);
    }

    public Bitmap getBitmapFromDiskCache(String cacheKey) {
        return null;    // TODO: cache the thumbs to the file system.
    }

    public void saveBitmapToDiskCache(String cacheKey, Bitmap bitmap) {
        // TODO: cache the thumbs to the file system.
    }


    public Bitmap getBitMapFromCache(String cacheKey) {
        Bitmap bitmap = getBitmapFromMemCache(cacheKey);
        if (bitmap != null) {
            Log.i(TAG, "[getBitMap] HIT the galleryMemCache, key: %s", cacheKey);
            return bitmap;
        }
        bitmap = getBitmapFromDiskCache(cacheKey);
        if (bitmap != null) {
            Log.i(TAG, "[getBitMap] HIT bitmap(key: %s) from disk cache", cacheKey);
            saveBitmapToMemCache(cacheKey, bitmap);
        }

        return bitmap;
    }




}
