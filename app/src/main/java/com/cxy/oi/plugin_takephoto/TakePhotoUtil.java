package com.cxy.oi.plugin_takephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.cxy.oi.kernel.contants.ConstantsStorage;
import com.cxy.oi.kernel.util.Log;

import java.io.File;

import static android.provider.MediaStore.EXTRA_OUTPUT;

public class TakePhotoUtil {
    private static final String TAG = "TakePhotoUtil";


    public static void takePhoto(Activity activity) {
        // TODO check permission挪过来
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String cameraDir = ConstantsStorage.getCameraDirPathForSysCamera();
        String fileName = "oi" + System.currentTimeMillis() + ".jpg";
        String filePath = cameraDir + fileName;

        File cameraDirFile = new File(cameraDir);
        if (!cameraDirFile.exists()) {
            if (!cameraDirFile.mkdirs()) {
                return;
            }
        }

        Uri uri = Uri.fromFile(new File(filePath));
        Log.i(TAG, "uri: %s, filePath: %s", uri, filePath);
        intent.putExtra(EXTRA_OUTPUT, uri);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, 0);

    }

    public static String genPhotoFileName() {
        return "oi" + System.currentTimeMillis() + ".jpg";
    }

}
