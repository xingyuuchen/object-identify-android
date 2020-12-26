package com.cxy.oi.kernel.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.cxy.oi.kernel.util.Log;

public class CoreService extends Service {
    private static final String TAG = "CoreService";



    @Override
    public void onCreate() {

        super.onCreate();
        Log.i(TAG, "[onCreate] threadID:" + Thread.currentThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "[onStartCommand] threadID:" + Thread.currentThread());
        return super.onStartCommand(intent, flags, startId);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "[onBind] threadID:" + Thread.currentThread());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "[onRebind] threadID:" + Thread.currentThread());
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
