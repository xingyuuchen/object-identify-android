package com.cxy.oi.kernel.network;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.cxy.oi.app.events.NetDispatcherReadyEvent;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.event.EventCenter;
import com.cxy.oi.kernel.util.Log;


public class CoreServiceConnection implements ServiceConnection {
    private static final String TAG = "CoreServiceConnection";


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof RDispatcher) {
            OIKernel.network().getNetSceneQueue().setDispatcher((RDispatcher) service);

            NetDispatcherReadyEvent event = new NetDispatcherReadyEvent();
            event.data.isReady = true;
            EventCenter.INSTANCE.publish(event);
        } else {
            Log.e(TAG, "[onServiceConnected] service NOT instanceof RDispatcher");
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        OIKernel.network().getNetSceneQueue().setDispatcher(null);

        NetDispatcherReadyEvent event = new NetDispatcherReadyEvent();
        event.data.isReady = false;
        EventCenter.INSTANCE.publish(event);
    }


}
