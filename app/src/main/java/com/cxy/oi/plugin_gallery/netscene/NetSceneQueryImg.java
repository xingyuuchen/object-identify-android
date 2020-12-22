package com.cxy.oi.plugin_gallery.netscene;


import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;


public class NetSceneQueryImg extends NetSceneBase {


    public NetSceneQueryImg() {

    }

    @Override
    public void onSceneEnd(int errCode) {
        if (errCode == ConstantsProtocol.ERR_OK) {

            RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
            builder.setItemName("皮老爷");
            builder.setItemType(ConstantsUI.ObjectItem.TYPE_PLANT);
            builder.setCreateTime(System.currentTimeMillis());
            builder.setContent("皮老爷，一名国男，最近成为了一个做题家。");
            builder.setImgPath("");
            RecognitionInfo info = builder.build();

            OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
        }
    }

}
