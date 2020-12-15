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

    public static String nullAs(String s, String as) {
        if (s == null) {
            return as;
        }
        return s;
    }

}
