package com.cxy.oi.app.model;

import android.view.View;

import com.cxy.oi.R;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;

public class SearchItemPlant extends SearchItem {
    private static final String TAG = "SearchItemPlant";


    @Override
    public int getType() {
        return ConstantsUI.ObjectItem.TYPE_PLANT;
    }


    @Override
    public void onItemClick(View view) {
        Log.i(TAG, "植物被点击辣");

    }

    @Override
    public boolean onLongClick(View view) {
//        PopupMenu menu = new PopupMenu();
        Log.i(TAG, "植物被长按辣");
        return true;
    }


    @Override
    public void customView(View convertView) {
        if (activity != null && convertView != null) {
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.green_alpha));
        }

    }


}
