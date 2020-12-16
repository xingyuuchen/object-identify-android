package com.cxy.oi.plugin_gallery.ui;

import android.graphics.Bitmap;
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
import com.cxy.oi.plugin_gallery.model.ReadBitmapFromFileTask;

public class ThumbDrawable extends Drawable {
    private static final String TAG = "ThumbDrawable";

    private static final Paint paint = new Paint();
    static {
        paint.setAntiAlias(true);
    }

    private Bitmap bitmap;
    private Rect srcRect = new Rect();
    private long origId;
    private String path;


    public static void attach(final ImageView iv, long origId, String path) {
        final Drawable obj = iv.getDrawable();
        final ThumbDrawable thumb;
        if (obj instanceof ThumbDrawable) {
            thumb = (ThumbDrawable) obj;
        } else {
            thumb = new ThumbDrawable();
        }

        thumb.origId = origId;
        thumb.path = path;
        thumb.bitmap = GalleryCore.getMediaCacheService().getBitMapFromCache(origId);
        if (thumb.bitmap == null) {
            GalleryCore.getMediaWorkerThread().postToWorker(new ReadBitmapFromFileTask(origId, path,
                    new ReadBitmapFromFileTask.IOnBitmapGet() {
                        @Override
                        public void onBitmapGet(long cacheKey, Bitmap bitmap) {
                            thumb.bitmap = bitmap;
                            iv.setImageDrawable(thumb);
                        }
            }));
        }
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
            if (bitmap == null) {
                Log.i(TAG, "bitmap == null, origId: %s", origId);
            } else {
                Log.i(TAG, "bitmap isRecycled() == true, origId: %s", origId);
            }
//            bitmap = ThumbDecodeUtil.getThumb(mOrigId, path);
            return;
        }
        resizeSrcRect();
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
