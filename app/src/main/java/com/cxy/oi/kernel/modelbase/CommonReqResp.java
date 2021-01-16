package com.cxy.oi.kernel.modelbase;

import com.google.protobuf.GeneratedMessageV3;

public class CommonReqResp {
    private static final String TAG = "OI.CommonReqResp";

    public String uri;
    public int type;
    public GeneratedMessageV3 req;
    public byte[] resp;

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

        public Builder setResp(byte[] resp) {
            reqResp.resp = resp;
            return this;
        }

        public CommonReqResp build() {
            return this.reqResp;
        }

    }

}
