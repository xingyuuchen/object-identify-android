package com.cxy.oi.app.model;

import com.cxy.oi.kernel.util.Log;

import java.util.HashSet;
import java.util.Set;


public final class SearchItemFactory {
    private static final String TAG = "SearchItemFactory";

    private static final Set<SearchItem> allSearchItems;


    public static SearchItem create(int type) {
        for (SearchItem item : allSearchItems) {
            if (item.getType() == type) {
                try {
                    return item.getClass().newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    Log.printErrStackTrace(TAG, e, "[create]");
                }
            }
        }
        Log.e(TAG, "[create] no type match %s", type);
        return new SearchItemPlant();
    }


    static {
        allSearchItems = new HashSet<>();
        allSearchItems.add(new SearchItemPlant());
        allSearchItems.add(new SearchItemAnimal());
        allSearchItems.add(new SearchItemLandMark());
    }

}
