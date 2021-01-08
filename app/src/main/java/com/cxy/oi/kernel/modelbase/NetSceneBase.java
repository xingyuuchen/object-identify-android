package com.cxy.oi.kernel.modelbase;

import com.cxy.oi.kernel.network.IDispatcher;

public abstract class NetSceneBase {
    private static final String TAG = "NetSceneBase";


    protected CommonReqResp reqResp;

    /**
     * unique scene type id, defined in ConstantsProtocol
     */
    public abstract int getType();

    public abstract int doScene(IDispatcher dispatcher);

    public abstract void onSceneEnd(int errCode);

}
