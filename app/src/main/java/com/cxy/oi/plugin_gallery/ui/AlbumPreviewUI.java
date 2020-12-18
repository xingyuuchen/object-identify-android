package com.cxy.oi.plugin_gallery.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.protocol.ConstantsProtocol;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;

import static com.cxy.oi.kernel.protocol.ConstantsProtocol.AlbumPreviewUI.REQUEST_PERMISSION;

public class AlbumPreviewUI extends Activity {
    private static final String TAG = "AlbumPreviewUI";

    private RecyclerView gallery;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "[onCreate]");
        setContentView(R.layout.gallery_preview);
        checkPermissions();

    }

    private void init() {
        Log.i(TAG, "[init]");
        gallery = findViewById(R.id.album_recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                ConstantsProtocol.AlbumPreviewUI.SPAN_COUNT);
        gallery.setLayoutManager(layoutManager);
        adapter = new AlbumAdapter(this);
        gallery.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "[onActivityResult], requestCode: %d", requestCode);
        if (requestCode == ConstantsProtocol.AlbumPreviewUI.ACTIVITY_REQUEST_CODE) {
            Intent intent = getIntent();

        }
    }


    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (Util.isNullOrNil(grantResults)
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "user refuse to grant!!");
                finish();
                return;
            }
            init();
        }
    }
}
