package com.cxy.oi.app.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cxy.oi.R;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.AppForegroundDelegate;
import com.cxy.oi.kernel.app.IAppForegroundListener;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.app.TestEvent;
import com.cxy.oi.crash.OICrashReporter;
import com.cxy.oi.kernel.event.EventCenter;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.ui.AlbumPreviewUI;



public class LauncherUI extends AppCompatActivity {

    private static final String TAG = "LauncherUI";
    private RelativeLayout ui;
    private ImageView bgImage;
    private ImageView goToGalleryPreviewIv;

    private BottomTabUI tabbar;
    private SearchHistoryUI searchHistoryUI;

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


        AppForegroundDelegate.INSTANCE.registerListener(appForegroundListener);
        EventCenter.INSTANCE.publish(new TestEvent());

    }


    private void initView() {
        ui = findViewById(R.id.main_ui);
        initTabbar();
        bgImage = findViewById(R.id.bg_image);
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

    }

    private void initTabbar() {
        tabbar = new BottomTabUI(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.BOTTOM;
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

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();
        OIApplicationContext.setContext(getApplicationContext());
        OIKernel.makeKernel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppForegroundDelegate.INSTANCE.unregisterListener(appForegroundListener);
    }
}
