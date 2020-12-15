package com.cxy.oi.plugin_gallery.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ThumbDrawable extends Drawable {
    private static final String TAG = "ThumbDrawable";


    public static void attach(ImageView iv, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
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
        return 0;
    }
}
