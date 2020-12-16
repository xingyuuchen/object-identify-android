package com.cxy.oi.plugin_gallery.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.model.GalleryCore;
import com.cxy.oi.plugin_gallery.model.ThumbDecodeUtil;

public class ThumbDrawable extends Drawable {
    private static final String TAG = "ThumbDrawable";

    private static final Paint paint = new Paint();
    static {
        paint.setAntiAlias(true);
    }

    private Bitmap bitmap;
    private Rect srcRect = new Rect();
    private long mOrigId;
    private String path;


    public static void attach(ImageView iv, long origId, String path) {
        final Drawable obj = iv.getDrawable();
        ThumbDrawable thumb;
        if (obj instanceof ThumbDrawable) {
            thumb = (ThumbDrawable) obj;
        } else {
            thumb = new ThumbDrawable();
        }

        thumb.mOrigId = origId;
        thumb.path = path;
        thumb.bitmap = GalleryCore.getMediaCacheService().getBitMapFromCache(0);
        if (thumb.bitmap == null) {
            thumb.bitmap = ThumbDecodeUtil.getThumb(origId, path);
        }
        iv.setImageDrawable(thumb);
    }


    private void resizeSrcRect() {
        if (bitmap == null) {
            return;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w > h) {
            srcRect.top = 0;
            srcRect.bottom = h;
            srcRect.left = (w - h) / 2;
            srcRect.right = w - srcRect.left;
        } else {
            srcRect.left = 0;
            srcRect.right = w;
            srcRect.top = (h - w) / 2;
            srcRect.bottom = h - srcRect.top;
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (bitmap == null || bitmap.isRecycled()) {
            Log.i(TAG, " bitmap.isRecycled(): %s", bitmap == null);
//            bitmap = ThumbDecodeUtil.getThumb(mOrigId, path);
        }
        resizeSrcRect();
        Log.i(TAG, "%s %s %s %s %s %s %s", srcRect.top, srcRect.bottom, srcRect.right,
                getBounds().top, getBounds().bottom, getBounds().left, getBounds().right);
        canvas.drawBitmap(bitmap, srcRect, getBounds(), paint);
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
