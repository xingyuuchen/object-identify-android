package com.cxy.oi.kernel.contants;

import android.content.Context;
import android.os.Environment;

import com.cxy.oi.kernel.app.OIApplicationContext;

import java.io.File;


/**
 * 任何路径后必须包含separator
 */
public class ConstantsStorage {
    private ConstantsStorage() {
    }

    private static String dataRoot;

    private static final String PICTURE_DIR_NAME = "pictures/";
    private static final String DATABASE_DIR_NAME = "databases/";

    public static final String DB_NAME = "ObjectIdentify.db";

    private static synchronized String getDataRoot() {
        if (dataRoot == null) {
            final Context context = OIApplicationContext.getContext();
            if (context == null) {
                throw new RuntimeException("[getDataRoot] ApplicationContext not initialized!!");
            }
            dataRoot = context.getFilesDir().getParentFile().getAbsolutePath() + "/";
        }
        return dataRoot;
    }

    public static synchronized String getOIDataRootPath() {
        final String path = getDataRoot() + "ObjectIdentify/";
        try {
            final File f = new File(path);
            if (!f.exists()) {
                boolean res = f.mkdirs();
            }
        } catch (Throwable ignored) {
            // ignored
        }
        return path;
    }


    public static String getDatabaseDirName() {
        File f = new File(getOIDataRootPath() + DATABASE_DIR_NAME);
        if (!f.exists()) {
            f.mkdirs();
        }
        return getOIDataRootPath() + DATABASE_DIR_NAME;
    }

    public String getPictureDirName() {
        File f = new File(getOIDataRootPath() + PICTURE_DIR_NAME);
        if (!f.exists()) {
            f.mkdirs();
        }
        return getOIDataRootPath() + PICTURE_DIR_NAME;
    }


    public static String getCameraDirPathForSysCamera() {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/oi/pictures/");
        if (!f.exists()) {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

    public static final String SYSTEM_CONFIG_PREFS = "system_config_prefs";

    public static final String ACCOUNT_CONFIG_PREFS = "account_config_prefs";
    public static final String ACCOUNT_KUSRID = "account_kusrid";
    public static final String ACCOUNT_KNICKNAME = "account_knickname";
    public static final String ACCOUNT_KAVATAR_PATH = "account_kavatar_path";



}
