package com.cxy.oi.kernel.network;

import com.cxy.oi.kernel.modelbase.CommonReqResp;

public interface IDispatcher {


    int startTask(CommonReqResp reqResp, IOnNetEnd onNetEnd);

}
