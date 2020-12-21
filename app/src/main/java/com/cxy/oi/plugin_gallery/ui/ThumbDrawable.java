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

    private ImageView iv;
    private Bitmap bitmap;
    private Rect srcRect = new Rect();
    private long origId;
    private String path;

    public ThumbDrawable(ImageView iv) {
        this.iv = iv;
    }

    public static void attach(ImageView iv, String path) {
        attach(iv, -1, path);
    }

    public static void attach(final ImageView iv, final long origId, String path) {
        final Drawable drawable = iv.getDrawable();
        final ThumbDrawable thumb;
        final boolean isNew;
        if (drawable instanceof ThumbDrawable) {
            thumb = (ThumbDrawable) drawable;
            thumb.bitmap = null;
            isNew = false;
        } else {
            thumb = new ThumbDrawable(iv);
            isNew = true;
        }

        thumb.origId = origId;
        thumb.path = path;
        Bitmap bm = GalleryCore.getMediaCacheService().getBitMapFromCache(origId);
        if (bm != null) {
            thumb.bitmap = bm;
            if (isNew) {
                iv.setImageDrawable(thumb);
            }
            return;
        }

        if (thumb.bitmap == null) {
            GalleryCore.getMediaWorkerThread().startDecode(new ReadBitmapFromFileTask(origId, path,
                    new ReadBitmapFromFileTask.IOnBitmapGet() {
                        @Override
                        public void onBitmapGet(Bitmap bitmap) {
                            thumb.bitmap = bitmap;
                            if (isNew) {
                                iv.setImageDrawable(thumb);
                            } else {
                                iv.invalidateDrawable(thumb);  // 异步，必须invalidate
                            }
                        }

                        @Override
                        public void doInBackground(Bitmap bitmap) {
                            GalleryCore.getMediaCacheService().saveBitmapToMemCache(origId, bitmap);
                        }
                    }));
        }
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if (bitmap == null || bitmap.isRecycled()) {
            Log.i(TAG, "bitmap == null || bitmap.isRecycled()!  origId: %s", origId);
            return;
        }
        resizeSrcRect();
        canvas.drawBitmap(bitmap, srcRect, getBounds(), paint);
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
