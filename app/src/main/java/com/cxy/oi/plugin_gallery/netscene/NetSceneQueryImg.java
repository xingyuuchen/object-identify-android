package com.cxy.oi.plugin_gallery.netscene;


import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.modelbase.IOnSceneEnd;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;

import java.util.Random;


public class NetSceneQueryImg extends NetSceneBase {
    private static final String TAG = "NetSceneQueryImg";

    private static final Random r = new Random();
    private final String imgPath;


    public NetSceneQueryImg(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        return dispatcher.startTask();
    }


    @Override
    public void onSceneEnd(int errCode) {
        if (errCode == ConstantsProtocol.ERR_OK) {

            RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
            builder.setItemName("陈老爷");
            if (r.nextBoolean()) {  // FIXME: hardcode
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_PLANT);
            } else {
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_ANIMAL);
            }
            builder.setCreateTime(System.currentTimeMillis());
            builder.setContent("陈老爷，牛的 🐂");
            builder.setImgPath(Util.nullAs(imgPath, ""));
            RecognitionInfo info = builder.build();

            OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
        } else {
            Log.e(TAG, "[onSceneEnd] errCode: %s", errCode);
        }
    }

}
