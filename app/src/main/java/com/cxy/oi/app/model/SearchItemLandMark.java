package com.cxy.oi.app.model;

import android.content.Context;
import android.view.View;

import com.cxy.oi.R;
import com.cxy.oi.kernel.contants.ConstantsUI;


public class SearchItemLandMark extends SearchItem {
    private static final String TAG = "SearchItemLandMark";


    @Override
    public int getType() {
        return ConstantsUI.ObjectItem.TYPE_LANDMARK;
    }


    @Override
    public void onItemClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    @Override
    public void customView(View convertView) {
        if (context != null && convertView != null) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.yellow_alpha));
        }
    }

}
