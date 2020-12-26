package com.cxy.oi.plugin_gallery.model;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import com.cxy.oi.kernel.app.OIHandler;
import com.cxy.oi.kernel.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MediaHandlerThread {
    private static final String TAG = "MediaHandlerThread";

    private OIHandler uiHandler;

    private HandlerThread queryHandlerThread;
    private OIHandler queryHandler;
    private ExecutorService decoder;

    public MediaHandlerThread() {
        uiHandler = new OIHandler(Looper.getMainLooper());
        // make it background priority so CPU-intensive work doesn't disrupt UI
        queryHandlerThread = new HandlerThread("MediaHandlerThread.queryHandler",
                Process.THREAD_PRIORITY_BACKGROUND);
        queryHandlerThread.start();
        queryHandler = new OIHandler(queryHandlerThread.getLooper());

        decoder = Executors.newFixedThreadPool(1);
    }

    public OIHandler getQueryHandler() {
        return queryHandler;
    }


    public void postToWorker(Runnable runnable) {
        if (runnable == null) {
            Log.e(TAG, "[postToWorker] runnable == null");
            return;
        }
        if (queryHandler != null) {
            queryHandler.post(runnable);
        }
    }

    public void startDecode(Runnable runnable) {
        if (runnable == null) {
            Log.e(TAG, "[startDecode] runnable == null");
            return;
        }
        if (decoder != null) {
            Log.i(TAG, "");
            decoder.execute(runnable);
        }
    }

    public void postToUIThread(Runnable runnable) {
        if (runnable == null) {
            Log.e(TAG, "[postToUIThread] runnable == null");
            return;
        }
        if (uiHandler != null) {
            uiHandler.post(runnable);
        }
    }

}
