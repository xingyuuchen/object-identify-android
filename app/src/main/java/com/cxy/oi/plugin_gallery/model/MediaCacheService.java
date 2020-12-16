package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;

import com.cxy.oi.kernel.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 *  二级缓存模型：内存Map缓存 + 硬盘缓存
 */
public class MediaCacheService {
    private static final String TAG = "MediaCacheService";

    private Map<Long, Bitmap> galleryMemCache;

    public MediaCacheService() {
        galleryMemCache = new HashMap<>();

    }

    public void saveBitmapToMemCache(long cacheKey, Bitmap bitmap) {
        galleryMemCache.put(cacheKey, bitmap);
    }

    public Bitmap getBitmapFromMemCache(long cacheKey) {
        return galleryMemCache.get(cacheKey);
    }

    public Bitmap getBitmapFromDiskCache(long cacheKey) {
        return null;    // TODO: cache the thumbs to the file system.
    }

    public void saveBitmapToDiskCache(long cacheKey, Bitmap bitmap) {

    }


    public Bitmap getBitMapFromCache(long cacheKey) {
        Bitmap bitmap = getBitmapFromMemCache(cacheKey);
        if (bitmap != null) {
            Log.i(TAG, "[getBitMap] HIT the galleryMemCache, key: %s", cacheKey);
            return bitmap;
        }
        bitmap = getBitmapFromDiskCache(cacheKey);
        if (bitmap == null) {
            Log.i(TAG, "[getBitMap] cannot get bitmap(key: %s) from cache", cacheKey);
        } else {
            saveBitmapToMemCache(cacheKey, bitmap);
        }

        return bitmap;
    }




}
