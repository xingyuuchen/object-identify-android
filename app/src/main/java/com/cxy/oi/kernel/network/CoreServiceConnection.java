package com.cxy.oi.kernel.network;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.util.Log;

public class CoreServiceConnection implements ServiceConnection {
    private static final String TAG = "CoreServiceConnection";


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof RDispatcher) {
            OIKernel.network().getNetSceneQueue().setDispatcher((RDispatcher) service);
        } else {
            Log.e(TAG, "service NOT instanceof RDispatcher");
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        OIKernel.network().getNetSceneQueue().setDispatcher(null);

    }


}
