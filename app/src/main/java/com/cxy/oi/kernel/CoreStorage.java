package com.cxy.oi.kernel;

import android.database.sqlite.SQLiteDatabase;

import com.cxy.oi.kernel.constants.ConstantsStorage;
import com.cxy.oi.kernel.util.Log;

import java.io.File;

/**
 *
 *  仅管理db文件, 不关心任何业务。
 *
 */
public final class CoreStorage {
    private static final String TAG = "CoreStorage";

    SQLiteDatabase db;

    CoreStorage() {
        try {
            String path = ConstantsStorage.getDatabaseDirName() + ConstantsStorage.DB_NAME;
            int flags = SQLiteDatabase.CREATE_IF_NECESSARY;
            initDB(path, flags);
        } catch (Exception e) {
            Log.e(TAG, "SQLiteException: %s", e);
        }
    }


    public SQLiteDatabase getDB() {
        if (db == null) {
            throw new NullPointerException("[getDB] db NOT init");
        }
        return db;
    }


    private void initDB(String dbPath, int flags) {
        try {
            final File f = new File(dbPath);
            if (!f.exists()) {
                boolean res = f.getParentFile().mkdirs();
            } else {
//                Log.i("AlbumAdapter", "delete res: %s", f.delete());
            }
            db = SQLiteDatabase.openDatabase(dbPath, null, flags);
        } catch (Throwable e) {
            Log.i(TAG, "[initDB] %s", e.getMessage());
        }
    }

    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }


}
