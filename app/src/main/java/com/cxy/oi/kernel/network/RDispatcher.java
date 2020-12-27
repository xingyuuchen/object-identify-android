package com.cxy.oi.kernel.network;

import android.os.Binder;


/**
 *  定义与服务进行通信的接口
 */
public class RDispatcher extends Binder implements IDispatcher {
    private static final String TAG = "RDispatcher";


    @Override
    public int testBinder(int val) {
        return val + 1;
    }


    @Override
    public int startTask() {
        NativeNetTaskAdapter.Task task = new NativeNetTaskAdapter.Task();

        int res = NativeNetTaskAdapter.startTask(task);
        return res;
    }

}
