package com.cxy.oi.plugin_gallery.netscene;

import com.cxy.oi.autogen.NetSceneQueryImgReq;
import com.cxy.oi.autogen.NetSceneQueryImgResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
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
    private final byte[] imgData;
    private NetSceneQueryImgReq req;


    public NetSceneQueryImg(String imgPath) {
        this.imgPath = imgPath;

        imgData = Util.readFromFile(imgPath);
        if (imgData != null) {
            req = NetSceneQueryImgReq.newBuilder().
                    setImgBytes(ByteString.copyFrom(imgData)).build();
            reqResp = new CommonReqResp.Builder()
                    .setReq(req)
                    .setUri("/oi/queryimg")
                    .setType(getType())
                    .build();
        }

    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_QUERY_IMG;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        Log.i(TAG, "[doScene] reqLen size = %d", reqResp.reqLen);
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onLocalErr(int errCode) {
        super.onLocalErr(errCode);
        if (imgData == null) {
            Log.e(TAG, "[onLocalErr] imgData == null");
        }
        if (imgPath == null) {
            Log.e(TAG, "[onLocalErr] imgPath == null");
        }
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

        NetSceneQueryImgResp resp;
        try {
            resp = NetSceneQueryImgResp.parseFrom(rr.resp);
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
