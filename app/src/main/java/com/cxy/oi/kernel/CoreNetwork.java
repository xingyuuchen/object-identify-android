package com.cxy.oi.kernel;

import com.cxy.oi.kernel.network.NetSceneQueue;

public final class CoreNetwork {
    private static final String TAG = "CoreNetwork";
    private final NetSceneQueue netSceneQueue;

    CoreNetwork() {
        netSceneQueue = NetSceneQueue.getInstance();
    }

    public NetSceneQueue getNetSceneQueue() {
        return netSceneQueue;
    }

}
