package com.cxy.oi.plugin_storage;

import android.database.sqlite.SQLiteDatabase;

public class ConfigStorage {
    private static final String TAG = "ConfigStorage";

    private SQLiteDatabase db;
    public static final String[] SQL_CREATE = {
//            "create table if not exists " + RecognitionInfo.RECOGNITION_INFO_TABLE + " ( " +
//                    RecognitionInfo.COL_ID + " integer primary key, " +
//                    RecognitionInfo.COL_SVR_ID + " integer, " +
//                    RecognitionInfo.COL_ITEM_TYPE + " int, " +
//                    RecognitionInfo.COL_STATUS + " int, " +
//                    RecognitionInfo.COL_CREATE_TIME + " integer, " +
//                    RecognitionInfo.COL_CONTENT + " text, " +
//                    RecognitionInfo.COL_IMG_PATH + " text, " +
//                    RecognitionInfo.COL_FLAG + " int )"
    };

    public ConfigStorage(SQLiteDatabase db) {
        this.db = db;
    }

}
