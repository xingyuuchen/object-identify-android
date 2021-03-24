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
        doNotifyInserted(info);
        return res;
    }

    public int delete(RecognitionInfo info) {
        if (info.getId() < 0) {
            return -1;
        }
        String where = RecognitionInfo.COL_ID + "=?";
        int res = db.delete(RecognitionInfo.RECOGNITION_INFO_TABLE, where, new String[] {info.getId() + ""});
        doNotifyDelete();
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

    private void doNotifyInserted(RecognitionInfo info) {
        Set<IOnRecognitionInfoChangeListener> set = new HashSet<>(listeners);
        for (IOnRecognitionInfoChangeListener listener : set) {
            listener.onNewRecognitionInfoInserted(info);
        }
    }

    private void doNotifyDelete() {
        Set<IOnRecognitionInfoChangeListener> set = new HashSet<>(listeners);
        for (IOnRecognitionInfoChangeListener listener : set) {
            listener.onRecognitionInfoDeleted();
        }
    }

    public void registerListener(IOnRecognitionInfoChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(IOnRecognitionInfoChangeListener listener) {
        listeners.remove(listener);
    }

    private final Set<IOnRecognitionInfoChangeListener> listeners;
    public interface IOnRecognitionInfoChangeListener {
        void onNewRecognitionInfoInserted(RecognitionInfo info);
        void onRecognitionInfoDeleted();
    }
}
