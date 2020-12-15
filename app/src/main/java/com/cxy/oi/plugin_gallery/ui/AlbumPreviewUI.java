package com.cxy.oi.plugin_gallery.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.protocol.ConstantsProtocol;
import com.cxy.oi.kernel.util.Log;

public class AlbumPreviewUI extends Activity {
    private static final String TAG = "AlbumPreviewUI";

    private RecyclerView gallery;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_preview);

    }

    private void init() {
        adapter = new AlbumAdapter(this);
        gallery = findViewById(R.id.album_recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,
                ConstantsProtocol.AlbumPreviewUI.SPAN_COUNT);
        gallery.setLayoutManager(layoutManager);
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
}
