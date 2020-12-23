package com.cxy.oi.app.adapter;

import android.database.Cursor;

import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;

import java.util.ArrayList;

public class SearchHistoryDataLoader {
    private static final String TAG = "SearchHistoryDataLoader";

    private int count;
    private long lastCreateTime;


    public SearchHistoryDataLoader(int count, long lastCreateTime) {
        this.count = count;
        this.lastCreateTime = lastCreateTime;
    }

    public SearchHistoryDataLoader() {
        this(0, 0);
    }

    public SearchHistoryDataLoader(int count) {
        this(count, 0);
    }

    public void load(IDataLoadCallBack callBack) {
        ArrayList<RecognitionInfo> infos = new ArrayList<>();
        Cursor cursor;
        if (count > 0) {
            cursor = OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().query(count, lastCreateTime);
        } else {
            cursor = OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().query(lastCreateTime);
        }
        while (cursor.moveToNext()) {
            RecognitionInfo info = new RecognitionInfo();
            info.convertFrom(cursor);
            infos.add(info);
        }
        cursor.close();
        callBack.onDataLoaded(infos);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getLastCreateTime() {
        return lastCreateTime;
    }

    public void setLastCreateTime(long lastCreateTime) {
        this.lastCreateTime = lastCreateTime;
    }

    public interface IDataLoadCallBack {
        void onDataLoaded(ArrayList<RecognitionInfo> loadResult);
    }

}
