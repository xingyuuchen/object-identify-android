package com.cxy.oi.app.adapter;

import android.database.Cursor;

import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;

import java.util.ArrayList;

public class SearchHistoryDataLoader {
    private static final String TAG = "SearchHistoryDataLoader";


    public void load(int limitCnt, long lastCreateTime, IDataLoadCallBack callBack) {
        ArrayList<RecognitionInfo> infos = new ArrayList<>();
        Cursor cursor = OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().query(limitCnt, lastCreateTime);
        while (cursor.moveToNext()) {
            RecognitionInfo info = new RecognitionInfo();
            info.convertFrom(cursor);
            infos.add(info);
        }
        cursor.close();
        callBack.onDataLoaded(infos);
    }


    public interface IDataLoadCallBack {
        void onDataLoaded(ArrayList<RecognitionInfo> loadResult);
    }

}
