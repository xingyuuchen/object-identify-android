package com.cxy.oi.app.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxy.oi.R;
import com.cxy.oi.app.ui.ItemDetailUI;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.plugin_storage.RecognitionInfo;


/**
 *  一次查询的抽象类
 *
 *  仅负责UI展示、与用户交互
 *
 */
public abstract class SearchItem {
    private static final String TAG = "SearchItem";

    protected Activity activity;
    protected String itemName;
    protected String itemDesc;
    protected String imgPath;

    public abstract int getType();

    public abstract void onItemClick(View view);

    public abstract boolean onLongClick(View view);

    public abstract void customView(View convertView);



    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClick(v);

            Intent intent = new Intent();
            intent.setClass(activity, ItemDetailUI.class);
            intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_NAME, itemName);
            intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_TYPE, getType());
            intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_DESC, itemDesc);
            intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_IMG_PATH, imgPath);
            activity.startActivity(intent);
        }
    };

    private final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return SearchItem.this.onLongClick(v);
        }
    };

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void fillingData(RecognitionInfo info) {
        itemName = info.getItemName();
        itemDesc = info.getContent();
        imgPath = info.getImgPath();
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
