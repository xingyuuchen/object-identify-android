package com.cxy.oi.kernel.network;

public interface IDispatcher {


    int startTask(CommonReqResp reqResp, IOnNetEnd onNetEnd);

    int startTask(CommonReqResp reqResp, IOnNetEnd onNetEnd, IOnUploadListener onUploadUpdate);

}
