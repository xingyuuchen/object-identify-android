package com.cxy.oi.kernel.modelbase;

import com.cxy.oi.kernel.network.IDispatcher;

public abstract class NetSceneBase {
    private static final String TAG = "NetSceneBase";


    /**
     * unique scene type id, defined in ConstantsProtocol
     *
     * @return scene type
     */
    public abstract int getType();

    public abstract int doScene(IDispatcher dispatcher, IOnSceneEnd callback);

    public abstract void onSceneEnd(int errCode);

}
