package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;

import com.cxy.oi.kernel.util.Log;

import java.util.HashMap;
import java.util.Map;


public class MediaCacheService {
    private static final String TAG = "MediaCacheService";

    private Map<Integer, Bitmap> galleryMemCache;

    public MediaCacheService() {
        galleryMemCache = new HashMap<>();

    }

    public void saveBitmapToMemCache(int key, Bitmap bitmap) {
        galleryMemCache.put(key, bitmap);
    }

    public Bitmap getMemCacheBitmap(int key) {
        return galleryMemCache.get(key);
    }

    public Bitmap getDiskCacheBitmap(int key) {
        return null;    // TODO: cache the thumbs to the fileSystem.
    }

    public void saveBitmapToDiskCache(int key, Bitmap bitmap) {

    }

    public Bitmap getBitMapFromCache(int key) {
        Bitmap bitmap = getMemCacheBitmap(key);
        if (bitmap != null) {
            Log.i(TAG, "[getBitMap] just hit the galleryMemCache, key: %s", key);
            return bitmap;
        }
        bitmap = getDiskCacheBitmap(key);
        if (bitmap == null) {
            Log.i(TAG, "[getBitMap] cannot get bitmap(key: %s) from cache!", key);
        }
        return bitmap;

    }


}
