package com.cxy.oi.kernel.contants;

import android.content.Context;

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
        return getOIDataRootPath() + DATABASE_DIR_NAME;
    }

    public static String getCameraDirPath() {
        return getOIDataRootPath() + PICTURE_DIR_NAME;
    }

}
