package com.cxy.oi.app;

import android.content.Context;

public final class OIApplicationContext {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }


    public static Context getContext() {
        return mContext;
    }


}
