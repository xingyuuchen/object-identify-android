package com.cxy.oi.kernel.modelbase;

import com.cxy.oi.kernel.app.AppForegroundDelegate;
import com.cxy.oi.kernel.app.IAppForegroundListener;
import com.cxy.oi.kernel.contants.ConstantsProtocol;

import java.util.ArrayList;

public final class NetSceneQueue {
    private static final String TAG = "NetSceneQueue";

    private static final NetSceneQueue instance = new NetSceneQueue();
    private final IAppForegroundListener appForegroundListener = new IAppForegroundListener() {
        @Override
        public void onAppForeground(String activity) {

        }

        @Override
        public void onAppBackground(String activity) {

        }
    };
    private ArrayList<NetSceneBase> runningQueue = new ArrayList<>();
    private ArrayList<NetSceneBase> waitingQueue = new ArrayList<>();



    private NetSceneQueue() {
        AppForegroundDelegate.INSTANCE.registerListener(appForegroundListener);
    }

    public static NetSceneQueue getInstance() {
        return instance;
    }


    public void doScene(NetSceneBase netScene) {
        // TODO: 网络请求

        netScene.onSceneEnd(ConstantsProtocol.ERR_OK);
    }

}
