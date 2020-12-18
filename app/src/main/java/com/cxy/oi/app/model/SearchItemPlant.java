package com.cxy.oi.app.model;

import com.cxy.oi.kernel.protocol.ConstantsProtocol;
import com.cxy.oi.kernel.util.Log;

public class SearchItemPlant extends SearchItem {
    private static final String TAG = "SearchItemPlant";


    @Override
    public int getType() {
        return ConstantsProtocol.ObjectItem.TYPE_PLANT;
    }


    @Override
    public void onItemClick() {
        Log.i(TAG, "植物被点击辣");
    }


}
