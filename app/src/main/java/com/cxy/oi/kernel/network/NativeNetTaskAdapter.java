package com.cxy.oi.kernel.network;

import com.cxy.oi.kernel.util.Log;

import java.util.HashMap;
import java.util.Map;


public class NativeNetTaskAdapter {
    private static final String TAG = "NativeNetTaskAdapter";

    private static ICallBack callBack;

    static {
        try {
            System.loadLibrary("oi_native");
            System.loadLibrary("c++_shared");
        } catch (UnsatisfiedLinkError e) {
            Log.i(TAG, "[loadLibrary] %s", e.getMessage());
        }
    }


    public static void setCallBack(ICallBack _callBack) {
        callBack = _callBack;
    }


    public static class Task {
        public int netID;

        public int retryCount = 3;
        public Map<String, String> headers;
        public String cgi;


        public Task() {
            this.headers = new HashMap<>();
        }

    }


    public static native int startTask(Task task);

    /**
     * native callback
     */
    public static int onTaskEnd(int netId, int errCode) {
        if (callBack != null) {
            return callBack.onTaskEnd(netId, errCode);
        }
        return -1;
    }


    public interface ICallBack {

        int onTaskEnd(int netId, int errCode);

    }
}
