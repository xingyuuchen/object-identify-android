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
    private static final int callbackPoolSize = 10; // concurrency

    private static final Info[] infoPool = new Info[callbackPoolSize];
    private static class Info {
        CommonReqResp rr;
        IOnNetEnd callback;
        Info(CommonReqResp rr, IOnNetEnd callback) {
            this.rr = rr;
            this.callback = callback;
        }
    }

    static {
        NativeNetTaskAdapter.setCallBack(new NativeNetTaskAdapter.ICallBack() {
            @Override
            public int onTaskEnd(int netId, final int errCode) {
                final Info info = infoPool[netId];
                Log.i(TAG, "[onTaskEnd] netid:%d, onNetEndCallback:%s", netId, info);
                if (info == null) {
                    Log.e(TAG, "[onTaskEnd] netid:%d, onNetEndCallback is null", netId);
                    return -1;
                }
                freeCallbackInfoFromPool(netId);


                OIKernel.getNetSceneQueue().getUiHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        info.callback.onNetEnd(errCode, info.rr);
                    }
                });
                return 0;
            }

            @Override
            public byte[] reqToBuffer(int netId) {
                Log.i(TAG, "[reqToBuffer] netid = %d", netId);
                final Info info = infoPool[netId];
                if (info == null) {
                    Log.e(TAG, "[reqToBuffer] infoPool[%d] == null", netId);
                    return null;
                }
                byte[] ret = info.rr.req.toByteArray();
                info.rr.reqLen = ret.length;
                return ret;
            }

            @Override
            public int bufferToResp(int netId, byte[] resp) {
                Log.i(TAG, "[bufferToResp] netid = %d", netId);
                final Info info = infoPool[netId];
                if (info == null) {
                    Log.e(TAG, "[bufferToResp] infoPool[%d] == null", netId);
                    return -1;
                }
                info.rr.resp = resp;
                return 0;
            }


            @Override
            public long getReqBufferSize(int netId) {
                final Info info = infoPool[netId];
                if (info == null) {
                    Log.e(TAG, "[getReqBufferSize] infoPool[%d] == null", netId);
                    return -1;
                }
                long ret = info.rr.reqLen;
                Log.i(TAG, "[getReqBufferSize] netid:%d, size:%d", netId, ret);
                return ret;
            }

        });
    }


    @Override
    public int testBinder(int val) {
        return val + 1;
    }

    private static int allocCallbackInfoFromPool(CommonReqResp reqResp, IOnNetEnd onNetEnd) {
        for (int netId = 0; netId < callbackPoolSize; netId++) {
            if (infoPool[netId] == null) {
                infoPool[netId] = new Info(reqResp, onNetEnd);
                return netId;
            }
        }
        Log.e(TAG, "callbackPoll is FULL");
        return -1;
    }

    private static int freeCallbackInfoFromPool(int netId) {
        if (netId < 0 || netId >= callbackPoolSize) {
            Log.e(TAG, "[freeCallbackInfoFromPool] Illegal netId: %d", netId);
            return -1;
        }
        if (infoPool[netId] != null) {
            infoPool[netId] = null;
            return 0;
        }
        Log.e(TAG, "[freeCallbackInfoFromPool] pool[%d]=null", netId);
        return -1;
    }


    @Override
    public int startTask(CommonReqResp reqResp, IOnNetEnd onNetEnd) {
        int netId = allocCallbackInfoFromPool(reqResp, onNetEnd);
        NativeNetTaskAdapter.Task task = new NativeNetTaskAdapter.Task();
        task.netID = netId;
        task.cgi = reqResp.uri;
        task.retryCount = 3;

        return NativeNetTaskAdapter.startTask(task);
    }

}
