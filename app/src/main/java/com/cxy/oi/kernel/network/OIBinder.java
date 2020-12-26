package com.cxy.oi.kernel.network;

import android.os.Binder;


/**
 *  定义与服务进行通信的接口
 */
public class OIBinder extends Binder {



    public void startTask() {
        NativeNetTaskAdapter.Task task = new NativeNetTaskAdapter.Task();

        NativeNetTaskAdapter.startTask(task);
    }

}
