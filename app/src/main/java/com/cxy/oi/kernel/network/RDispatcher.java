package com.cxy.oi.kernel.network;

import android.os.Binder;

import com.cxy.oi.kernel.modelbase.CommonReqResp;


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
    public int startTask(CommonReqResp reqResp) {
        NativeNetTaskAdapter.Task task = new NativeNetTaskAdapter.Task();
        task.cgi = reqResp.uri;
        task.retryCount = 3;
        task.taskID = reqResp.type;

        return NativeNetTaskAdapter.startTask(task);
    }

}
