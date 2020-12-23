package com.cxy.oi.plugin_gallery.netscene;


import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
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
    public void onSceneEnd(int errCode) {
        if (errCode == ConstantsProtocol.ERR_OK) {

            RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
            builder.setItemName("皮老爷");
            if (r.nextBoolean()) {  // FIXME: hardcode
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_PLANT);
            } else {
                builder.setItemType(ConstantsUI.ObjectItem.TYPE_ANIMAL);
            }
            builder.setCreateTime(System.currentTimeMillis());
            builder.setContent("皮老爷，一名国男，最近成为了一个做题家。");
            builder.setImgPath(Util.nullAs(imgPath, ""));
            RecognitionInfo info = builder.build();

            OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
        }
    }

}
