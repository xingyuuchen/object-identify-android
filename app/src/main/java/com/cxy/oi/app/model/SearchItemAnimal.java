package com.cxy.oi.app.model;

import android.view.View;

import com.cxy.oi.R;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;


public class SearchItemAnimal extends SearchItem {
    private static final String TAG = "SearchItemAnimal";


    @Override
    public int getType() {
        return ConstantsUI.ObjectItem.TYPE_ANIMAL;
    }

    @Override
    public void onItemClick(View view) {
        Log.i(TAG, "动物被点击辣");
    }

    @Override
    public boolean onLongClick(View view) {
        Log.i(TAG, "动物被长按辣");
        return true;
    }

    @Override
    public void customView(View convertView) {
        if (activity != null && convertView != null) {
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.red_alpha));
        }
    }

}
