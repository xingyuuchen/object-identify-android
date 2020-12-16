package com.cxy.oi.plugin_gallery.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.exifinterface.media.ExifInterface;
import android.provider.MediaStore;

import com.cxy.oi.app.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;

import java.io.IOException;

public class ThumbDecodeUtil {
    private static final String TAG = "ThumbDecodeUtil";

    public static Bitmap getThumb(long origId, String path) {
        Bitmap bitmap;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            byte[] bytes = exifInterface.getThumbnail();
            if (bytes != null) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmap != null) {
                    Log.i(TAG, "[getThumb] OK, from ExifInterface");
                    return bitmap;
                }
            }
        } catch (IOException e) {
            Log.printErrStackTrace(TAG, e, "[getThumb]");
        }
        bitmap = getThumbFromSystemAPI(origId);
        if (bitmap != null) {
            Log.i(TAG, "[getThumb] getThumbFromSystemAPI ok, origId: %d", origId);
            return bitmap;
        }
        Log.i(TAG, "");
        bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }


    public static Bitmap getThumbFromSystemAPI(long origId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                OIApplicationContext.getContext().getContentResolver(),
                origId, MediaStore.Images.Thumbnails.MINI_KIND, options);
        return bitmap;
    }




}
