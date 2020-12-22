package com.cxy.oi.kernel;

import com.cxy.oi.kernel.modelbase.NetSceneQueue;

public final class CoreNetwork {
    private static final String TAG = "CoreNetwork";
    private NetSceneQueue netSceneQueue;

    CoreNetwork() {
        netSceneQueue = NetSceneQueue.getInstance();
    }

    public NetSceneQueue getNetSceneQueue() {
        return netSceneQueue;
    }

}
