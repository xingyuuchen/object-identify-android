package com.cxy.oi.kernel.modelbase;

import android.os.Looper;
import android.os.Message;

import com.cxy.oi.kernel.app.AppForegroundDelegate;
import com.cxy.oi.kernel.app.IAppForegroundListener;
import com.cxy.oi.kernel.app.OIHandler;
import com.cxy.oi.kernel.contants.ConstantsProtocol;

import java.util.ArrayList;

public final class NetSceneQueue implements IAppForegroundListener {
    private static final String TAG = "NetSceneQueue";

    private static final NetSceneQueue instance = new NetSceneQueue();
    private final ArrayList<NetSceneBase> runningQueue = new ArrayList<>();
    private final ArrayList<NetSceneBase> waitingQueue = new ArrayList<>();
    private OIHandler handler;


    private NetSceneQueue() {
        AppForegroundDelegate.INSTANCE.registerListener(this);

        handler = new OIHandler(Looper.getMainLooper()) {
            @Override
            public void handleMassage(Message msg) {
                doScene((NetSceneBase) msg.obj);
            }
        };

    }

    public static NetSceneQueue getInstance() {
        return instance;
    }


    public void doScene(NetSceneBase netScene) {
        doScene(netScene, 0);
    }


    public void doScene(NetSceneBase netScene, int delaySeconds) {
        if (delaySeconds < 0) {
            return;
        }
        if (delaySeconds > 0) {
            Message msg = Message.obtain();
            msg.obj = netScene;
            handler.sendMessageDelayed(msg, delaySeconds);
            return;
        }

        // TODO: 网络请求

        netScene.onSceneEnd(ConstantsProtocol.ERR_OK);
    }

    @Override
    public void onAppForeground(String activity) {

    }

    @Override
    public void onAppBackground(String activity) {

    }

}
