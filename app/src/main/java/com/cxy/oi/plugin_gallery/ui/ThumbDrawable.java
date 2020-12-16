package com.cxy.oi.plugin_gallery.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.model.GalleryCore;
import com.cxy.oi.plugin_gallery.model.ThumbDecodeUtil;

public class ThumbDrawable extends Drawable {
    private static final String TAG = "ThumbDrawable";


    public static void attach(ImageView iv, long origId, String path) {
        Bitmap bitmap = GalleryCore.getMediaCacheService().getBitMapFromCache(0);
        if (bitmap == null) {
            bitmap = ThumbDecodeUtil.getThumb(origId, path);
        }
        iv.setImageBitmap(bitmap);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
