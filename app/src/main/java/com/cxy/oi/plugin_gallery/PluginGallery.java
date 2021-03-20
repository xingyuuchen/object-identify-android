package com.cxy.oi.plugin_gallery;

import android.widget.ImageView;

import com.cxy.oi.plugin_gallery.ui.ThumbDrawable;

public class PluginGallery implements IPluginGallery {

    @Override
    public void AttachThumbDrawable(ImageView iv, String path) {
        ThumbDrawable.attach(iv, path);
    }

    @Override
    public void AttachThumbDrawable(ImageView iv, long origId, String path) {
        ThumbDrawable.attach(iv, origId, path);
    }

    @Override
    public void doWhenBoot() {

    }
}
