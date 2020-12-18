package com.cxy.oi.app.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxy.oi.R;


/**
 *  一次查询的抽象类
 *
 *  仅负责UI展示、与用户交互
 *
 */
public abstract class SearchItem {
    private static final String TAG = "SearchItem";

    public abstract int getType();

    public abstract void onItemClick();


    public static class BaseViewHolder {
        public SearchItem searchItem;
        public View thiz;
        public ImageView searchIv;
        public TextView itemName;
        public TextView itemDesc;
        public TextView searchTime;

        public BaseViewHolder(View view, final SearchItem searchItem) {
            this.searchItem = searchItem;
            thiz = view;
            searchIv = thiz.findViewById(R.id.search_iv);
            itemName = thiz.findViewById(R.id.item_name);
            itemDesc = thiz.findViewById(R.id.item_description);
            searchTime = thiz.findViewById(R.id.search_time);

            thiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseViewHolder.this.searchItem.onItemClick();
                }
            });
        }

    }


}
