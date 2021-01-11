package com.cxy.oi.kernel.network;

import com.cxy.oi.kernel.util.Log;

import java.util.HashMap;
import java.util.Map;


public class NativeNetTaskAdapter {
    private static final String TAG = "NativeNetTaskAdapter";


    static {
        try {
            System.loadLibrary("oi_native");
            System.loadLibrary("c++_shared");
        } catch (UnsatisfiedLinkError e) {
            Log.i(TAG, "[loadLibrary] %s", e.getMessage());
        }
    }


    public static class Task {
        public int taskID;  //unique task identify

        public int retryCount = 3;
        public Map<String, String> headers;
        public String cgi;


        public Task() {
            this.headers = new HashMap<>();
        }

    }


    public static native int startTask(Task task);

}