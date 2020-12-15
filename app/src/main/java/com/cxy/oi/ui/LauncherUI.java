package com.cxy.oi.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxy.oi.R;
import com.cxy.oi.app.AppForegroundDelegate;
import com.cxy.oi.app.IAppForegroundListener;
import com.cxy.oi.app.OIApplicationContext;
import com.cxy.oi.crash.OICrashReporter;
import com.cxy.oi.kernel.util.Log;

public class LauncherUI extends AppCompatActivity {

    private static final String TAG = "LauncherUI";
    private FrameLayout ui;
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
        ui = findViewById(R.id.main_ui);
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

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();
        OIApplicationContext.setContext(getApplication());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppForegroundDelegate.INSTANCE.unregisterListener(appForegroundListener);
    }
}
