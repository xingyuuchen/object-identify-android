package com.cxy.oi.plugin_takephoto;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public class TakePhotoUtil {
    private static final String TAG = "TakePhotoUtil";


    public static void takePhoto(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
