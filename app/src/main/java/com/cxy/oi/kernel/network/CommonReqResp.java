package com.cxy.oi.kernel.network;

import com.cxy.oi.autogen.BaseNetSceneReq;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;

public class CommonReqResp {
    private static final String TAG = "OI.CommonReqResp";

    public String uri;
    public int type = ConstantsProtocol.NETSCENE_TYPE_UNKNOWN_TYPE;

    public BaseNetSceneReq baseReq;
    public GeneratedMessageV3 req;
    public byte[] baseResp;
    public ByteString resp;

    public long reqLen;


    public static class Builder {
        private final CommonReqResp reqResp;

        public Builder() {
            this.reqResp = new CommonReqResp();
        }

        public Builder setUri(String uri) {
            reqResp.uri = uri;
            return this;
        }

        public Builder setType(int type) {
            reqResp.type = type;
            return this;
        }

        public Builder setReq(GeneratedMessageV3 req) {
            reqResp.req = req;
            return this;
        }

        public CommonReqResp build() {
            if (reqResp.type == ConstantsProtocol.NETSCENE_TYPE_UNKNOWN_TYPE ||
                    reqResp.req == null) {
                throw new IllegalStateException("[CommonReqResp] arguments NOT Initialized.");
            }
            reqResp.baseReq = BaseNetSceneReq.newBuilder()
                    .setNetSceneType(reqResp.type)
                    .setNetSceneReqBuff(ByteString.copyFrom(reqResp.req.toByteArray()))
                    .build();
            reqResp.reqLen = reqResp.baseReq.toByteArray().length;
            return this.reqResp;
        }

    }

}
