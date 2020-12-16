package com.cxy.oi.plugin_gallery.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.cxy.oi.app.OIHandler;
import com.cxy.oi.kernel.util.Log;

public class MediaHandlerThread {
    private static final String TAG = "MediaHandlerThread";

    private OIHandler uiHandler;

    private HandlerThread queryHandlerThread;
    private OIHandler queryHandler;

    public MediaHandlerThread() {
        uiHandler = new OIHandler(Looper.getMainLooper());
        queryHandlerThread = new HandlerThread("MediaHandlerThread.queryHandler");
        queryHandlerThread.start();
        queryHandler = new OIHandler(queryHandlerThread.getLooper());

    }

    public OIHandler getQueryHandler() {
        return queryHandler;
    }


    public void postToWorker(Runnable runnable) {
        if (runnable == null) {
            Log.e(TAG, "[postToWorker] runnable == null");
        }
        if (queryHandler != null) {
            queryHandler.post(runnable);
        }
    }


    public void postToUIThread(Runnable runnable) {
        if (runnable == null) {
            Log.e(TAG, "[postToUIThread] runnable == null");
        }
        if (uiHandler != null) {
            uiHandler.post(runnable);
        }
    }

}
