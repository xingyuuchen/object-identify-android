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

    @Override
    public void run() {
        bitmap = ThumbDecodeUtil.getThumb(origId, path);
        callback.doInBackground(bitmap);
        GalleryCore.getMediaWorkerThread().postToUIThread(new Runnable() {
            @Override
            public void run() {
                callback.onBitmapGet(bitmap);
            }
        });
    }

    public interface IOnBitmapGet {
        void onBitmapGet(Bitmap bitmap);
        void doInBackground(Bitmap bitmap);
    }


    public void addImageBitmapGetListener(IOnBitmapGet listener) {

    }

}
