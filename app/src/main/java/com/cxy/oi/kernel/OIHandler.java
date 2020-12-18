package com.cxy.oi.kernel;

import android.os.Handler;
import android.os.Looper;


public class OIHandler {
    private static final String TAG = "OIHandler";

    private Handler theHandler;

    public OIHandler(Looper looper) {
        if (looper == null) {
            throw new NullPointerException("[OIHandler] looper should not be null");
        }
        theHandler = new Handler(looper);
    }

    public boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        return theHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    public boolean postAtTime(Runnable r, long uptimeMillis) {
        return theHandler.postAtTime(r, uptimeMillis);
    }

    public void post(Runnable runnable) {
        if (theHandler != null) {
            theHandler.post(runnable);
        }
    }

}
