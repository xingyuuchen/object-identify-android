package com.cxy.oi.kernel.modelbase;

import com.cxy.oi.kernel.contants.ConstantsProtocol;

public final class NetSceneQueue {
    private static final String TAG = "NetSceneQueue";

    private static final NetSceneQueue instance = new NetSceneQueue();

    private NetSceneQueue() {

    }

    public static NetSceneQueue getInstance() {
        return instance;
    }

    public void doScene(NetSceneBase netScene) {
        // TODO: 网络请求

        netScene.onSceneEnd(ConstantsProtocol.ERR_OK);
    }

}
