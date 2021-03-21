package com.cxy.oi.app.model;

import android.content.Context;

import com.cxy.oi.kernel.util.Log;

import java.util.HashSet;
import java.util.Set;


public final class SearchItemFactory {
    private static final String TAG = "SearchItemFactory";

    private static final Set<SearchItem> allSearchItems;


    public static SearchItem create(int type, Context context) {
        for (SearchItem item : allSearchItems) {
            if (item.getType() == type) {
                try {
                    SearchItem newItem = item.getClass().newInstance();
                    newItem.setContext(context);
                    return newItem;
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
