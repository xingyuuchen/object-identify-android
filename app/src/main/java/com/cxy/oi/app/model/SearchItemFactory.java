package com.cxy.oi.app.model;

import com.cxy.oi.kernel.contants.ConstantsUI;

public class SearchItemFactory {

    public static SearchItem create(int type) {
        switch (type) {
            case ConstantsUI.ObjectItem.TYPE_PLANT:
            case ConstantsUI.ObjectItem.TYPE_ANIMAL:
            case ConstantsUI.ObjectItem.TYPE_LANDMARK:
                return new SearchItemPlant();
            default:
            return null;
        }
    }


}
