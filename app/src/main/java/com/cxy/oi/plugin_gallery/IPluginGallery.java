package com.cxy.oi.plugin_gallery;

import android.widget.ImageView;

import com.cxy.oi.kernel.modelbase.IPlugin;


public interface IPluginGallery extends IPlugin {

    void attachThumbDrawable(ImageView iv, String path);

    void attachThumbDrawable(ImageView iv, String path, boolean isBlur);

    void attachThumbDrawable(final ImageView iv, final long origId, String path);

    void attachThumbDrawable(final ImageView iv, final long origId, String path, boolean isBlur);


}
