package com.cxy.oi.app.netscene;

import android.content.Context;
import android.widget.Toast;

import com.cxy.oi.autogen.NetSceneUploadAvatarReq;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.google.protobuf.ByteString;


public class NetSceneUploadAvatar extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneUploadAvatar";
    private String avatarPath;
    private final byte[] avatarImgData;
    private final Context mContext;
    private NetSceneUploadAvatarReq req;

    private final IAvatarUploadListener callback;

    public NetSceneUploadAvatar(String avatarPath, Context context) {
        this(avatarPath, context, null);
    }

    public NetSceneUploadAvatar(String avatarPath, Context context,
                                IAvatarUploadListener callback) {
        this.callback = callback;
        this.avatarPath = avatarPath;
        this.mContext = context;

        avatarImgData = Util.readFromFile(avatarPath);
        if (avatarImgData != null) {
            req = NetSceneUploadAvatarReq.newBuilder()
                    .setUsrId(OIKernel.account().getUsrId())
                    .setAvatarImgBytes(ByteString.copyFrom(avatarImgData)).build();

            reqResp = new CommonReqResp.Builder()
                    .setReq(req)
                    .setUri("/oi/uploadavatar")
                    .setType(getType())
                    .build();
        }

    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_UPLOAD_AVATAR;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        if (avatarImgData == null) {
            Log.e(TAG, "[doScene] avatar file bytes not ready");
            return ConstantsProtocol.ERR_REQ_DATA_ILLEGAL;
        }
        Log.i(TAG, "[doScene] reqLen size = %d", reqResp.reqLen);
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onNetEnd(int errCode, String errmsg, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode, errmsg)) {
            if (callback != null) {
                callback.onAvatarUploaded(avatarPath, false);
            }
            return;
        }
        Log.i(TAG, "[onNetEnd] avatar upload succeed");
        OIKernel.account().saveAvatar(avatarPath);
        if (callback != null) {
            callback.onAvatarUploaded(avatarPath, true);
        }
    }

    public interface IAvatarUploadListener {
        void onAvatarUploaded(String avatarPath, boolean isSuccess);
    }
}