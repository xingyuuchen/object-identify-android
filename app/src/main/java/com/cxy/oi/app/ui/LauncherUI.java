package com.cxy.oi.app.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxy.oi.R;
import com.cxy.oi.app.TestEvent;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.AppForegroundDelegate;
import com.cxy.oi.kernel.app.IAppForegroundListener;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.crash.OICrashReporter;
import com.cxy.oi.kernel.event.EventCenter;
import com.cxy.oi.kernel.network.CoreService;
import com.cxy.oi.kernel.network.CoreServiceConnection;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_gallery.netscene.NetSceneQueryImg;
import com.cxy.oi.plugin_gallery.ui.AlbumPreviewUI;
import com.cxy.oi.plugin_takephoto.TakePhotoUtil;
import com.tencent.mars.app.AppLogic;
import com.tencent.mars.stn.StnLogic;

import java.io.File;

import static com.cxy.oi.kernel.contants.ConstantsUI.LauncherUI.REQUEST_PERMISSION_CAMERA_FORCE;
import static com.cxy.oi.kernel.contants.ConstantsUI.LauncherUI.REQUEST_PERMISSION_DEFAULT;


public class LauncherUI extends AppCompatActivity implements IAppForegroundListener {
    private static final String TAG = "LauncherUI";
    private RelativeLayout ui;
    private ImageView goToGalleryPreviewIv;
    private ImageView gotoTakePhotoIv;

    private BottomTabUI tabbar;
    private SearchHistoryUI searchHistoryUI;
    private final CoreServiceConnection coreServiceConnection = new CoreServiceConnection();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        AppForegroundDelegate.INSTANCE.registerListener(this);

        EventCenter.INSTANCE.publish(new TestEvent());
        Util.checkPermissionsAndRequest(this, this,
                new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_DEFAULT);

    }

    private void startService() {
        Intent intent = new Intent(this, CoreService.class);
        intent.putExtra("a", "haha");
        startService(intent);

    }


    private void initView() {
        ui = findViewById(R.id.main_ui);
        initTabbar();
        ImageView bgImage = findViewById(R.id.bg_image);
        ListView historyItemListView = findViewById(R.id.history_items);
        searchHistoryUI = new SearchHistoryUI(historyItemListView, OIApplicationContext.getContext());

        goToGalleryPreviewIv = findViewById(R.id.btn_gallery);
        goToGalleryPreviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AlbumPreviewUI.class);
                startActivityForResult(intent, ConstantsUI.AlbumPreviewUI.ACTIVITY_REQUEST_CODE);
            }
        });

        gotoTakePhotoIv = findViewById(R.id.btn_take_photo);
        gotoTakePhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhotoUtil.takePhoto(LauncherUI.this);
            }
        });
    }

    private void initTabbar() {
        tabbar = new BottomTabUI(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        ui.addView(tabbar, params);
        tabbar.setOnTabClickedListener(new IOnTabClickListener() {
            @Override
            public void onTabClick(int idxOfTab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public BottomTabUI getTabbar() {
        return tabbar;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "[onActivityResult] requestCode: %s, resultCode: %s", requestCode, resultCode);
        switch (requestCode) {
            case ConstantsUI.LauncherUI.REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String photoPath = TakePhotoUtil.getLastPhotoPath();
                    if (new File(photoPath).exists()) {
                        NetSceneQueryImg scene = new NetSceneQueryImg(photoPath);
                        OIKernel.getNetSceneQueue().doScene(scene);
                    }
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();
        OIApplicationContext.setContext(getApplicationContext());
        OIKernel.makeKernel();
        bindService(new Intent(this, CoreService.class), coreServiceConnection, Service.BIND_AUTO_CREATE);

//        AppLogic.setCallBack(stub);
//        StnLogic.setCallBack(stub);
    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(this, CoreService.class));
        OIKernel.storage().closeDB();
        AppForegroundDelegate.INSTANCE.unregisterListener(this);
        unbindService(coreServiceConnection);
        super.onDestroy();
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_FORCE) {
            if (Util.isNullOrNil(grantResults)
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "user refuse to grant camera permission");
                Toast.makeText(this, "无权限启动相机", Toast.LENGTH_LONG).show();
                return;
            }
            Log.i(TAG, "user grant camera permission, retry open camera");
            TakePhotoUtil.takePhoto(this);
        }
    }



    @Override
    public void onAppForeground(String activity) {
        Log.i(TAG, "receive foreground, activity: %s", activity);
    }
    @Override
    public void onAppBackground(String activity) {
        Log.i(TAG, "receive background, activity: %s", activity);
    }

}
