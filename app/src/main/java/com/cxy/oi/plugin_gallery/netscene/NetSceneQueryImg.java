package com.cxy.oi.plugin_gallery.netscene;


import android.widget.Toast;

import com.cxy.oi.autogen.NetSceneQueryImgReq;
import com.cxy.oi.autogen.NetSceneQueryImgResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.modelbase.CommonReqResp;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;



public class NetSceneQueryImg extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneQueryImg";

    private final String imgPath;
    private NetSceneQueryImgReq req;
    private NetSceneQueryImgResp resp;


    public NetSceneQueryImg(String imgPath) {
        this.imgPath = imgPath;

        req = NetSceneQueryImgReq.newBuilder().
                setImgBytes(ByteString.copyFrom(new byte[100])).build();
        reqResp = new CommonReqResp.Builder()
                .setReq(req)
                .setUri("/oi/queryimg")
                .setType(getType())
                .build();

    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_QUERY_IMG;
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
    public void onNetEnd(int errCode, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode)) {
            return;
        }

        if (rr.resp == null) {
            Log.e(TAG, "[onNetEnd] rr.resp == null");
            return;
        }

        try {
            this.resp = NetSceneQueryImgResp.parseFrom(rr.resp);
        } catch (InvalidProtocolBufferException e) {
            Log.i(TAG, "rr.resp.len: %d", rr.resp.length);
            Log.e(TAG, "[onNetEnd] InvalidProtocolBufferException: %s", e.getMessage());
            return;
        }
        RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
        builder.setItemType(typeToDBInt(resp.getItemType()));
        builder.setItemName(resp.getItemName())
                .setCreateTime(System.currentTimeMillis())
                .setContent(resp.getItemDesc())
                .setImgPath(Util.nullAs(imgPath, ""));
        RecognitionInfo info = builder.build();

        OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
    }

    private int typeToDBInt(NetSceneQueryImgResp.ItemType type) {
        switch (type) {
            case PLANT:
                return 0;
            case ANIMAL:
                return 1;
            case LANDMARK:
                return 2;
        }
        return -1;
    }

}
