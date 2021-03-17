package com.cxy.oi.kernel;

import android.content.SharedPreferences;

import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsStorage;
import com.cxy.oi.kernel.util.Log;


public final class CoreAccount {
    private static final String TAG = "CoreAccount";
    private int usrId = -1;
    private static final int INVALID_USR_ID = -1;

    CoreAccount() {
        SharedPreferences sp = OIApplicationContext.getContext().getSharedPreferences(ConstantsStorage.ACCOUNT_CONFIG_PREFS, 0);
        int id = sp.getInt(ConstantsStorage.ACCOUNT_KUSRID, INVALID_USR_ID);
        if (id != INVALID_USR_ID) {
            usrId = id;
        }
    }

    public void updateUsrId(int usrId) {
        this.usrId = usrId;
        SharedPreferences sp = OIApplicationContext.getContext().getSharedPreferences(ConstantsStorage.ACCOUNT_CONFIG_PREFS, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(ConstantsStorage.ACCOUNT_KUSRID, usrId).apply();
    }

    public int getUsrId() {
        if (usrId == INVALID_USR_ID) {
            Log.w(TAG, "[getUsrId] INVALID_USR_ID");
        }
        return usrId;
    }

    public boolean accountReady() {
        return usrId != INVALID_USR_ID;
    }

}
