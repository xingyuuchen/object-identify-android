package com.cxy.oi.plugin_gallery.netscene;


import com.cxy.oi.kernel.OIKernel;
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

import java.util.Random;


public class NetSceneQueryImg extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneQueryImg";

    private static final Random r = new Random();
    private final String imgPath;


    public NetSceneQueryImg(String imgPath) {
        this.imgPath = imgPath;
        reqResp = new CommonReqResp.Builder()
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
    public void onNetEnd(int errCode) {
        if (errCode == ConstantsProtocol.ERR_OK) {

            RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
            if (r.nextBoolean()) {  // FIXME: hardcode
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_PLANT);
            } else {
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_ANIMAL);
            }
            builder.setItemName("薰衣草")
                    .setCreateTime(System.currentTimeMillis())
                    .setContent("薰衣草，香的很啊")
                    .setImgPath(Util.nullAs(imgPath, ""));
            RecognitionInfo info = builder.build();

            OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
        } else {
            Log.e(TAG, "[onNetEnd] errCode: %s", errCode);
        }
    }

}
