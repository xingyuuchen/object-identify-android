package com.cxy.oi.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cxy.oi.R;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_gallery.IPluginGallery;


public class ItemDetailUI extends Activity {
    private static final String TAG = "ItemDetailUI";

    private TextView itemNameTv;
    private TextView itemDescTv;
    private ImageView itemIv;
    private ImageView bg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "[onCreate]");
        setContentView(R.layout.item_detail);
        initView();

        String itemName = getIntent().getStringExtra(ConstantsUI.ItemDetailUI.KITEM_NAME);
        String itemDesc = getIntent().getStringExtra(ConstantsUI.ItemDetailUI.KITEM_DESC);
        boolean fromHotSearch = getIntent().getBooleanExtra(ConstantsUI.ItemDetailUI.KFROM_HOTSEARCH, false);
        String imgPath = getIntent().getStringExtra(ConstantsUI.ItemDetailUI.KITEM_IMG_PATH);

        if (!fromHotSearch && !Util.isNullOrNil(imgPath)) {
            OIKernel.plugin(IPluginGallery.class).AttachThumbDrawable(itemIv, imgPath);
            OIKernel.plugin(IPluginGallery.class).AttachThumbDrawable(bg, imgPath, true);
        }
        itemNameTv.setText(itemName);
        itemDescTv.setText(itemDesc);


    }

    private void initView() {
        itemIv = findViewById(R.id.detail_item_iv);
        itemNameTv = findViewById(R.id.detail_item_name_tv);
        itemDescTv = findViewById(R.id.detail_item_desc_tv);
        bg = findViewById(R.id.detail_img_bg);
    }
}
