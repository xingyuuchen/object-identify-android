package com.cxy.oi.kernel.network;

import com.cxy.oi.kernel.modelbase.CommonReqResp;

public interface IDispatcher {


    int testBinder(int val);

    int startTask(CommonReqResp reqResp);

}
