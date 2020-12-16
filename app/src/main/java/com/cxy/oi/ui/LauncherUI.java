package com.cxy.oi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxy.oi.R;
import com.cxy.oi.app.AppForegroundDelegate;
import com.cxy.oi.app.IAppForegroundListener;
import com.cxy.oi.app.OIApplicationContext;
import com.cxy.oi.app.TestEvent;
import com.cxy.oi.crash.OICrashReporter;
import com.cxy.oi.kernel.EventCenter;
import com.cxy.oi.kernel.protocol.ConstantsProtocol;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.ui.AlbumPreviewUI;

public class LauncherUI extends AppCompatActivity {

    private static final String TAG = "LauncherUI";
    private FrameLayout ui;
    private ImageView goToGalleryPreviewIv;
    private IAppForegroundListener appForegroundListener = new IAppForegroundListener() {
        @Override
        public void onAppForeground(String activity) {
            Log.i(TAG, "receive foreground, activity: %s", activity);
        }
        @Override
        public void onAppBackground(String activity) {
            Log.i(TAG, "receive background, activity: %s", activity);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        BottomTabUI tabbar = new BottomTabUI(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        ui.addView(tabbar, params);
        tabbar.setOnTabClickedListener(new IOnTabClickListener() {
            @Override
            public void onTabClick(int idxOfTab) {

            }
        });

        AppForegroundDelegate.INSTANCE.registerListener(appForegroundListener);
        EventCenter.INSTANCE.publish(new TestEvent());

    }


    private void initView() {
        ui = findViewById(R.id.main_ui);
        goToGalleryPreviewIv = findViewById(R.id.btn_gallery);
        goToGalleryPreviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AlbumPreviewUI.class);
                startActivityForResult(intent, ConstantsProtocol.AlbumPreviewUI.ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();
        if (getApplicationContext() == null) {
            Log.i(TAG, "wtf!?????????????     QQ");
        }
        OIApplicationContext.setContext(getApplicationContext());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppForegroundDelegate.INSTANCE.unregisterListener(appForegroundListener);
    }
}
