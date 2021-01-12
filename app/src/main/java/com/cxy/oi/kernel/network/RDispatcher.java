package com.cxy.oi.kernel.network;

import android.os.Binder;

import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.modelbase.CommonReqResp;
import com.cxy.oi.kernel.util.Log;


/**
 *  定义与服务进行通信的接口
 */
public class RDispatcher extends Binder implements IDispatcher {
    private static final String TAG = "RDispatcher";
    private static final int callbackPoolSize = 20;

    private static final IOnNetEnd[] callbackPool = new IOnNetEnd[callbackPoolSize];

    static {
        NativeNetTaskAdapter.setCallBack(new NativeNetTaskAdapter.ICallBack() {
            @Override
            public int onTaskEnd(int netId, final int errCode) {
                final IOnNetEnd callback = callbackPool[netId];
                Log.i(TAG, "[onTaskEnd] netid:%d, onNetEndCallback:%s", netId, callback);
                if (callback == null) {
                    Log.e(TAG, "[onTaskEnd] netid:%d, onNetEndCallback is null", netId);
                    return -1;
                }
                freeCallbackFromPool(netId);

                OIKernel.getNetSceneQueue().getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onNetEnd(errCode);
                    }
                });
                return 0;
            }
        });
    }


    @Override
    public int testBinder(int val) {
        return val + 1;
    }

    private static int allocCallbackFromPool(IOnNetEnd onNetEnd) {
        for (int netId = 0; netId < callbackPoolSize; netId++) {
            if (callbackPool[netId] == null) {
                callbackPool[netId] = onNetEnd;
                return netId;
            }
        }
        Log.e(TAG, "callbackPoll is FULL");
        return -1;
    }

    private static int freeCallbackFromPool(int netId) {
        if (netId < 0 || netId >= callbackPoolSize) {
            Log.e(TAG, "[freeCallbackFromPool] Illegal netId: %d", netId);
            return -1;
        }
        if (callbackPool[netId] != null) {
            callbackPool[netId] = null;
            return 0;
        }
        Log.e(TAG, "[freeCallbackFromPool] pool[%d]=null", netId);
        return -1;
    }


    @Override
    public int startTask(CommonReqResp reqResp, IOnNetEnd onNetEnd) {
        int netId = allocCallbackFromPool(onNetEnd);
        NativeNetTaskAdapter.Task task = new NativeNetTaskAdapter.Task();
        task.netID = netId;
        task.cgi = reqResp.uri;
        task.retryCount = 3;

        return NativeNetTaskAdapter.startTask(task);
    }

}
