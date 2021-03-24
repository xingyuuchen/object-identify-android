package com.cxy.oi.app.model;

import android.view.View;

import com.cxy.oi.R;
import com.cxy.oi.kernel.constants.ConstantsUI;


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
        return true;
    }


    @Override
    public void customView(View convertView) {
        if (activity != null && convertView != null) {
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.yellow_alpha));
        }
    }

}
