package com.cxy.oi.kernel.util;

public class Log {

    public static void i(String TAG, final String format, Object... objects) {
        String log = objects == null ? format : String.format(format, objects);

        android.util.Log.i(TAG, log);
    }

    public static void v(String TAG, final String format, Object... objects) {
        String log = objects == null ? format : String.format(format, objects);

        android.util.Log.v(TAG, log);
    }

    public static void w(String TAG, final String format, Object... objects) {
        String log = objects == null ? format : String.format(format, objects);

        android.util.Log.w(TAG, log);
    }


    public static void d(String TAG, final String format, Object... objects) {
        String log = objects == null ? format : String.format(format, objects);

        android.util.Log.d(TAG, log);
    }

    public static void e(String TAG, final String format, Object... objects) {
        String log = objects == null ? format : String.format(format, objects);

        android.util.Log.e(TAG, log);
    }

    public static void printErrStackTrace(String TAG, Throwable tr, final String format, final Object... obj) {
        String log = obj == null ? format : String.format(format, obj);
        if (log == null) {
            log = "";
        }
        log += "  " + android.util.Log.getStackTraceString(tr);
        android.util.Log.e(TAG, log);
    }

}
