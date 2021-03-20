package com.cxy.oi.app.ui;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cxy.oi.R;
import com.cxy.oi.app.TestEvent;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.AppForegroundDelegate;
import com.cxy.oi.kernel.app.IAppForegroundListener;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.crash.OICrashReporter;
import com.cxy.oi.kernel.event.EventCenter;
import com.cxy.oi.kernel.network.CoreService;
import com.cxy.oi.kernel.network.CoreServiceConnection;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.cxy.oi.kernel.contants.ConstantsUI.LauncherUI.REQUEST_PERMISSION_CAMERA_FORCE;
import static com.cxy.oi.kernel.contants.ConstantsUI.LauncherUI.REQUEST_PERMISSION_DEFAULT;


public class LauncherUI extends AppCompatActivity implements IAppForegroundListener {
    private static final String TAG = "OI.LauncherUI";
    private RelativeLayout ui;

    private BottomTabUI tabbar;
    private final CoreServiceConnection coreServiceConnection = new CoreServiceConnection();

    private ViewPager viewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        viewPager = findViewById(R.id.viewpager);
        initView();
        AppForegroundDelegate.INSTANCE.registerListener(this);

        EventCenter.INSTANCE.publish(new TestEvent());
        Util.checkPermissionsAndRequest(this, this,
                new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_DEFAULT);

    }

    private void initView() {
        ui = findViewById(R.id.main_ui);
        initViewPager();
        initTabbar();
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new IndexPagerUI());
        fragments.add(new SettingsPagerUI());

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i(TAG, "[onPageScrolled] %d, %f, %d", position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "[onPageSelected] %d", position);
                if (tabbar != null) {
                    tabbar.switchToTab(position);
                    return;
                }
                Log.e(TAG, "[onPageSelected] tabbar is null!");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i(TAG, "[onPageScrollStateChanged] %d", state);
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
                if (idxOfTab < 0 || idxOfTab >= fragments.size()) {
                    Log.e(TAG, "[onTabClick] idxOfTab: %d", idxOfTab);
                    return;
                }
                viewPager.setCurrentItem(idxOfTab);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        OICrashReporter.INSTANCE.init();
        OIApplicationContext.setContext(getApplicationContext());
        OIKernel.makeKernel();
        bindService(new Intent(this, CoreService.class), coreServiceConnection, Service.BIND_AUTO_CREATE);

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
//            TakePhotoUtil.takePhoto(this);
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
