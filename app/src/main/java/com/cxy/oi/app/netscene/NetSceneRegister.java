package com.cxy.oi.app.netscene;

import com.cxy.oi.autogen.NetSceneRegisterReq;
import com.cxy.oi.autogen.NetSceneRegisterResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.constants.ConstantsProtocol;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.google.protobuf.InvalidProtocolBufferException;


public class NetSceneRegister extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneRegister";
    private final Callback callback;

    public NetSceneRegister() {
        this(null);
    }

    public NetSceneRegister(Callback callback) {
        this.callback = callback;
        NetSceneRegisterReq req = NetSceneRegisterReq.newBuilder().setNop(true).build();
        reqResp = new CommonReqResp.Builder()
                .setType(getType())
                .setReq(req)
                .setUri("/oi/register")
                .build();
    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_REGISTER;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onNetEnd(int errCode, String errmsg, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode, errmsg)) {
            return;
        }
        NetSceneRegisterResp resp;
        try {
            resp = NetSceneRegisterResp.parseFrom(rr.resp);
        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "[onNetEnd] InvalidProtocolBufferException: %s", e.getMessage());
            return;
        }
        int id = resp.getUsrId();
        Log.i(TAG, "[onNetEnd] usrid is: %d", id);

        OIKernel.account().saveUsrId(id);

        if (callback != null) {
            callback.onUpdateUsrId(id);
        }
    }


    public interface Callback {
        void onUpdateUsrId(int id);
    }
}