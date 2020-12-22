package com.cxy.oi.app.model;

import android.content.Context;
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

    protected Context context;

    public abstract int getType();

    public abstract void onItemClick(View view);

    public abstract boolean onLongClick(View view);

    public abstract void customView(View convertView);



    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClick(v);
        }
    };

    private final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return SearchItem.this.onLongClick(v);
        }
    };

    public void setContext(Context context) {
        this.context = context;
    }

    public static class BaseViewHolder {
        public SearchItem searchItem;
        public View convertView;
        public ImageView searchIv;
        public TextView itemNameTv;
        public TextView itemDescTv;
        public TextView searchTimeTv;

        public BaseViewHolder(View view, final SearchItem searchItem) {
            this.searchItem = searchItem;
            convertView = view;
            searchIv = convertView.findViewById(R.id.search_iv);
            itemNameTv = convertView.findViewById(R.id.item_name);
            itemDescTv = convertView.findViewById(R.id.item_description);
            searchTimeTv = convertView.findViewById(R.id.search_time);

            convertView.setOnClickListener(searchItem.onClickListener);
            convertView.setOnLongClickListener(searchItem.onLongClickListener);
        }

    }


}
