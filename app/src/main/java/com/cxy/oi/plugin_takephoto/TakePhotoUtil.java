package com.cxy.oi.plugin_takephoto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.cxy.oi.kernel.contants.ConstantsStorage;

import java.io.File;

import static android.provider.MediaStore.EXTRA_OUTPUT;

public class TakePhotoUtil {
    private static final String TAG = "TakePhotoUtil";


    public static void takePhoto(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String cameraDir = ConstantsStorage.getCameraDirPath();
        String fileName = "oi" + System.currentTimeMillis() + ".jpg";
        String filePath = cameraDir + fileName;

        File cameraDirFile = new File(cameraDir);
        if (!cameraDirFile.exists()) {
            if (!cameraDirFile.mkdirs()) {
                return;
            }
        }

        Uri uri = Uri.parse(filePath);
        intent.putExtra(EXTRA_OUTPUT, uri);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
