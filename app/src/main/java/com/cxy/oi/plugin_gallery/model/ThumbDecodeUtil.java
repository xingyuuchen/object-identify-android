package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.exifinterface.media.ExifInterface;
import android.provider.MediaStore;

import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;

import java.io.IOException;


public class ThumbDecodeUtil {
    private static final String TAG = "ThumbDecodeUtil";
    private static final int THUMB_SIZE = 100;


    public static Bitmap getThumb(long origId, String path) {
        Bitmap bitmap;
        try {
            if (!Util.isNullOrNil(path)) {
                ExifInterface exifInterface = new ExifInterface(path);
                byte[] bytes = exifInterface.getThumbnail();
                if (bytes != null) {
                    bitmap = decodeFileIfTooLarge(bytes, THUMB_SIZE);
                    if (bitmap != null) {
                        Log.i(TAG, "[getThumb] from ExifInterface OK");
                        return bitmap;
                    }
                }
            }
        } catch (IOException e) {
            Log.printErrStackTrace(TAG, e, "[getThumb]");
        }
        if (origId > 0) {
            bitmap = getThumbFromSystemAPI(origId);
            if (bitmap != null) {
                Log.i(TAG, "[getThumb] getThumbFromSystemAPI ok, origId: %d", origId);
                return bitmap;
            }
        }
        if (!Util.isNullOrNil(path)) {
            bitmap = decodeFileIfTooLarge(path, THUMB_SIZE);
            return bitmap;
        }
        return null;
    }


    public static Bitmap getThumbFromSystemAPI(long origId) {
        if (origId > 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                    OIApplicationContext.getContext().getContentResolver(),
                    origId, MediaStore.Images.Thumbnails.MINI_KIND, options);
            return bitmap;
        }
        return null;
    }


    public static Bitmap decodeFileIfTooLarge(String path, int targetSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int scale = 1;
            while (options.outWidth / scale / 2 >= targetSize && options.outHeight / scale / 2 >= targetSize) {
                scale *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            return null;
        }
    }


    public static Bitmap decodeFileIfTooLarge(byte[] data, int targetSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int scale = 1;
            while (options.outWidth / scale / 2 >= targetSize && options.outHeight / scale / 2 >= targetSize) {
                scale *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            return null;
        }
    }

}
