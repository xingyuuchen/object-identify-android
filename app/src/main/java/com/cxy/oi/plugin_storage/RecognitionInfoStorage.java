package com.cxy.oi.plugin_storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxy.oi.kernel.util.Log;

import java.util.HashSet;
import java.util.Set;

public class RecognitionInfoStorage {
    private static final String TAG = "RecognitionInfoStorage";

    private final SQLiteDatabase db;
    public static final String[] SQL_CREATE = {
            "create table if not exists " + RecognitionInfo.RECOGNITION_INFO_TABLE + " ( " +
                    RecognitionInfo.COL_ID + " integer primary key, " +
                    RecognitionInfo.COL_SVR_ID + " integer, " +
                    RecognitionInfo.COL_STATUS + " int, " +
                    RecognitionInfo.COL_ITEM_NAME + " text, " +
                    RecognitionInfo.COL_ITEM_TYPE + " int, " +
                    RecognitionInfo.COL_CREATE_TIME + " integer, " +
                    RecognitionInfo.COL_CONTENT + " text, " +
                    RecognitionInfo.COL_IMG_PATH + " text, " +
                    RecognitionInfo.COL_FLAG + " int )"
    };


    public RecognitionInfoStorage(SQLiteDatabase db) {
        this.db = db;
        listeners = new HashSet<>();
    }



    public long insert(RecognitionInfo info) {
        ContentValues contentValues = info.convertTo();
        long res = db.insert(RecognitionInfo.RECOGNITION_INFO_TABLE, RecognitionInfo.COL_ID, contentValues);
        if (res < 0) {
            Log.i(TAG, "[insert] failed, res: %s", res);
            return res;
        }
        doNotify();
        return res;
    }


    public Cursor query(long lastCreateTime) {
        return query(Integer.MAX_VALUE, lastCreateTime);
    }

    public Cursor query(int limitCnt, long lastCreateTime) {
        final String sql = "select * from " + RecognitionInfo.RECOGNITION_INFO_TABLE + " where " +
                RecognitionInfo.COL_CREATE_TIME + ">" + lastCreateTime +
                " order by " + RecognitionInfo.COL_CREATE_TIME + " desc limit " + limitCnt;
        return db.rawQuery(sql, null);
    }

    public int getRecognitionInfoCount() {
        int count = 0;
        String query = "select count(*) from " + RecognitionInfo.RECOGNITION_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    private void doNotify() {
        Set<IOnxxxxxx> set = new HashSet<>(listeners);
        for (IOnxxxxxx listener : set) {
            listener.onNewRecognitionInfoInserted();
        }
    }

    public void registerListener(IOnxxxxxx listener) {
        listeners.add(listener);
    }

    public void unregisterListener(IOnxxxxxx listener) {
        listeners.remove(listener);
    }

    private final Set<IOnxxxxxx> listeners;     // TODO: 取名字
    public interface IOnxxxxxx {
        void onNewRecognitionInfoInserted();
    }
}
