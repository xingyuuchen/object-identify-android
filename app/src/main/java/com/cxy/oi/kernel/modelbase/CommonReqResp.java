package com.cxy.oi.kernel.modelbase;

public class CommonReqResp {

    public String uri;
    public int type;


    public static class Builder {
        private CommonReqResp reqResp;

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

        public CommonReqResp build() {
            return this.reqResp;
        }

    }

}
