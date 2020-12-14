package com.cxy.oi.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxy.oi.R;
import com.cxy.oi.crash.OICrashReporter;

public class LauncherUI extends AppCompatActivity {

    private static final String TAG = "LauncherUI";
    private FrameLayout ui;


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

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();

    }
}
