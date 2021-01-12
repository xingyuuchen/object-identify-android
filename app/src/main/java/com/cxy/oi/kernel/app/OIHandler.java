package com.cxy.oi.kernel.app;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class OIHandler {
    private static final String TAG = "OIHandler";

    private Handler theHandler;

    public OIHandler(Looper looper) {
        if (looper == null) {
            throw new NullPointerException("[OIHandler] looper should not be null");
        }
        theHandler = createHandler(looper);
    }

    public boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        return theHandler.sendEmptyMessageDelayed(what, delayMillis);
    }


    public boolean sendMessageDelayed(Message msg, long delayMillis) {
        return theHandler.sendMessageDelayed(msg, delayMillis);
    }

    public boolean postAtTime(Runnable r, long uptimeMillis) {
        return theHandler.postAtTime(r, uptimeMillis);
    }

    public void post(Runnable runnable) {
        if (theHandler != null) {
            theHandler.post(runnable);
        }
    }

    public Handler createHandler(Looper looper) {
        return new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                OIHandler.this.handleMassage(msg);
            }
        };
    }


    public void handleMassage(Message msg) {
        // override if needed
    }

}
