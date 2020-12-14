package com.cxy.oi.crash;


import android.os.Process;
import android.util.Log;

public enum OICrashReporter {
    INSTANCE;

    private static final String TAG = "OICrashReporter";

    public void init() {

    }


    OICrashReporter() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                StringBuilder reason = new StringBuilder(e.toString());
                StackTraceElement[] elements = e.getStackTrace();
                if (elements != null && elements.length > 0) {
                    for (StackTraceElement element : elements) {
                        reason.append("\t\n");
                        reason.append(element.toString());
                    }
                }
                Log.e(TAG, reason.toString());

                Process.killProcess(Process.myPid());
            }
        });
    }

}
