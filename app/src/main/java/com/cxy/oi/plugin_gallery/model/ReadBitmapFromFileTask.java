package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;

public class ReadBitmapFromFileTask implements Runnable {
    private static final String TAG = "ReadBitmapFromFileTask";

    private IOnBitmapGet callback;
    private long origId;
    private String path;
    private Bitmap bitmap;


    public ReadBitmapFromFileTask(long origId, String path, IOnBitmapGet callback) {
        this.origId = origId;
        this.path = path;
        this.callback = callback;
    }

    public void doQuery() {
        bitmap = ThumbDecodeUtil.getThumb(origId, path);
    }

    @Override
    public void run() {
        bitmap = ThumbDecodeUtil.getThumb(origId, path);
        GalleryCore.getMediaWorkerThread().postToUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onBitmapGet(origId, bitmap);
            }
        });
    }

    public interface IOnBitmapGet {
        void onBitmapGet(long cacheKey, Bitmap bitmap);
    }


    public void addImageBitmapGetListener(IOnBitmapGet listener) {

    }

}
