package com.cxy.oi.app.netscene;

import com.cxy.oi.autogen.NetSceneChangeNicknameReq;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.constants.ConstantsProtocol;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;


public class NetSceneChangeNickname extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneChangeNickname";

    private IOnNicknameChangedListener callback;
    private String nickname;

    public NetSceneChangeNickname(String nickname) {
        this(nickname, null);
    }

    public NetSceneChangeNickname(String nickname, IOnNicknameChangedListener callback) {
        this.nickname = nickname;
        this.callback = callback;

        if (Util.isNullOrNil(nickname)) {
            Log.e(TAG, "nickname is null or nil");
            return;
        }
        if (!OIKernel.account().accountReady()) {
            Log.e(TAG, "account net Ready");
            return;
        }
        int usrId = OIKernel.account().getUsrId();
        NetSceneChangeNicknameReq req = NetSceneChangeNicknameReq.newBuilder()
                .setNickname(nickname)
                .setUsrId(usrId)
                .build();
        reqResp = new CommonReqResp.Builder()
                .setReq(req)
                .setType(getType())
                .setUri("/oi/changenickname")
                .build();
    }


    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_CHANGE_NICKNAME;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        if (reqResp == null) {
            Log.e(TAG, "[doScene] reqResp == null");
            return ConstantsProtocol.ERR_REQ_DATA_ILLEGAL;
        }
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onNetEnd(int errCode, String errmsg, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode, errmsg)) {
            Log.i(TAG, "[onNetEnd] errcode: %d, errmsg: %s", errCode, errmsg);
            return;
        }

        Log.i(TAG, "[onNetEnd] NetSceneChangeNickname succeed");
        if (callback != null) {
            callback.onNickNameChanged(nickname);
        }
    }

    public interface IOnNicknameChangedListener {
        void onNickNameChanged(String nickname);
    }

}
