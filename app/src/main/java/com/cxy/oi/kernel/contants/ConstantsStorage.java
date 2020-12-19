package com.cxy.oi.kernel.contants;

import android.content.Context;

import com.cxy.oi.kernel.app.OIApplicationContext;

import java.io.File;

public class ConstantsStorage {
    private ConstantsStorage() {
    }

    public static String DATA_ROOT_PATH = DATA_ROOT_PATH();
    public static final String DB_NAME = "ObjectIdentify.db";

    private static String DATA_ROOT;



    public static synchronized String DATA_ROOT() {
        if (DATA_ROOT == null) {
            final Context context = OIApplicationContext.getContext();
            if (context == null) {
                throw new RuntimeException("ApplicationContext not initialized!!");
            }
            DATA_ROOT = context.getFilesDir().getParentFile().getAbsolutePath() + "/";
        }
        return DATA_ROOT;
    }

    public static synchronized String DATA_ROOT_PATH() {
        final String path = DATA_ROOT() + "ObjectIdentify/";
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

}
