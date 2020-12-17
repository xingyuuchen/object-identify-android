package com.cxy.oi.kernel.util;

import java.util.Collection;

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

    public static String nullAs(String s, String as) {
        if (s == null) {
            return as;
        }
        return s;
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

}