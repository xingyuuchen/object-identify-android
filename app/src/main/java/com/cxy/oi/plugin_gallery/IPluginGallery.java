package com.cxy.oi.plugin_gallery;

import android.widget.ImageView;

import com.cxy.oi.kernel.modelbase.IPlugin;


public interface IPluginGallery extends IPlugin {

    void AttachThumbDrawable(ImageView iv, String path);

    void AttachThumbDrawable(ImageView iv, String path, boolean isBlur);

    void AttachThumbDrawable(final ImageView iv, final long origId, String path);

    void AttachThumbDrawable(final ImageView iv, final long origId, String path, boolean isBlur);


}
