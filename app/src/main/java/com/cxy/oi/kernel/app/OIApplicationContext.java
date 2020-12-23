package com.cxy.oi.kernel.app;

import android.content.Context;

public final class OIApplicationContext {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }


    public static Context getContext() {
        return mContext;
    }

    public static String getPackageName() {
        return "com.cxy.oi";
    }

}
