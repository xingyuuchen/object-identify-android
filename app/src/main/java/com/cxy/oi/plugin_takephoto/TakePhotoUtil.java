package com.cxy.oi.plugin_takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;

import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsStorage;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;

import java.io.File;

import static com.cxy.oi.kernel.contants.ConstantsUI.LauncherUI.REQUEST_PERMISSION_CAMERA_FORCE;


public class TakePhotoUtil {
    private static final String TAG = "TakePhotoUtil";
    private static final String cameraDir = ConstantsStorage.getCameraDirPathForSysCamera();
    private static String lastPhotoPath;



    public static void takePhoto(Activity activity) {
        if (activity == null) {
            return;
        }
        if (!Util.checkPermissionsAndRequest(activity, activity,
                new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_FORCE)) {
            Log.e(TAG, "permission CAMERA denied");
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        genPhotoFileName();

        File cameraDirFile = new File(cameraDir);
        if (!cameraDirFile.exists()) {
            if (!cameraDirFile.mkdirs()) {
                return;
            }
        }

        Uri uri = Uri.fromFile(new File(lastPhotoPath));
        Log.i(TAG, "uri: %s, filePath: %s", uri, lastPhotoPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   // 注释了之后：拍照返回了才onActivityResult，拍了返回-1
        activity.startActivityForResult(intent, ConstantsUI.LauncherUI.REQUEST_CODE_TAKE_PHOTO);

    }


    public static String genPhotoFileName() {
        String photoFileName = "oi" + System.currentTimeMillis() + ".jpg";
        lastPhotoPath = cameraDir + photoFileName;
        return photoFileName;
    }

    public static String getLastPhotoPath() {
        return lastPhotoPath;
    }


    private static void savePhotoPathToSharedPreference(String photoPath) {
        SharedPreferences sp = OIApplicationContext.getContext().getSharedPreferences(ConstantsStorage.SYSTEM_CONFIG_PREFS, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ConstantsUI.LauncherUI.KPHOTO_PATH, photoPath).apply();
    }

}
