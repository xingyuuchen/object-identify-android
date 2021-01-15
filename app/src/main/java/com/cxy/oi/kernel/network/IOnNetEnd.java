package com.cxy.oi.kernel.network;


import com.cxy.oi.kernel.modelbase.CommonReqResp;

public interface IOnNetEnd {

    void onNetEnd(int errCode, CommonReqResp rr);

}
