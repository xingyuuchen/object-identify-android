package com.cxy.oi.kernel.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {
    private static final String TAG = "Util";

    public static boolean isNullOrNil(String s) {
        return s == null || s.length() <= 0;
    }

    public static boolean isNullOrNil(Collection<?> s) {
        return s == null || s.size() <= 0;
    }

    public static boolean isNullOrNil(int[] objects) {
        return objects == null || objects.length <= 0;
    }

    public static String nullAsNil(String s) {
        return s != null ? s : "";
    }

    public static byte[] nullAsNil(byte[] bytes) {
        return bytes != null ? bytes : new byte[0];
    }

    public static List<Object> nullAsNil(List<Object> objects) {
        return objects != null ? objects : new ArrayList<>();
    }

    public static String nullAs(String s, String as) {
        if (s == null) {
            return as;
        }
        return s;
    }
    public static byte[] nullAs(byte[] bytes, byte[] as) {
        if (bytes == null) {
            return as;
        }
        return bytes;
    }

    public static String getStack() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        if ((elements == null) || (elements.length < 4)) {
            return "...";
        }
        StringBuilder t = new StringBuilder();
        for (int i = 3; i < elements.length; i++) {
            if (!elements[i].getClassName().contains("com.cxy.oi")) {
                continue;
            }
            t.append("[");
            t.append(elements[i].getClassName().substring("com.cxy.oi.".length()));
            t.append(":");
            t.append(elements[i].getMethodName());
            t.append("(").append(elements[i].getLineNumber()).append(")]");
        }
        return t.toString();
    }


    public static boolean checkPermissionsAndRequest(Context context, Activity activity,
                                 String[] permissions, int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        requestCode);
                ret = false;
            }
        }
        return ret;
    }

    public static byte[] readFromFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            int len = fis.available();
            byte[] bytes = new byte[len];

            int ret = fis.read(bytes, 0, len);
            if (ret > 0) {
                return bytes;
            }
            Log.e(TAG, "[readFromFile] fis.read(bytes, 0, len) <= 0");
            return null;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "[readFromFile] FileNotFoundException %s", path);
        } catch (IOException e) {
            Log.e(TAG, "[readFromFile] IOException %s", path);
        }
        return null;
    }

}
