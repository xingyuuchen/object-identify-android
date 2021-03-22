package com.cxy.oi.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.netscene.NetSceneGetTrainProgress;
import com.cxy.oi.plugin_gallery.netscene.NetSceneQueryImg;
import com.cxy.oi.plugin_gallery.ui.AlbumPreviewUI;
import com.cxy.oi.plugin_takephoto.TakePhotoUtil;

import java.io.File;


public class IndexPagerUI extends Fragment {
    private static final String TAG = "IndexPagerUI";

    private RelativeLayout ui;
    private RecyclerView hotSearchRecyclerView;
    private HotSearchUI hotSearchUI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_index, null);
        initView(view);
        return view;
    }

    private void initView(View root) {
        ui = root.findViewById(R.id.tab1wrapper);
        ImageView bgImage = root.findViewById(R.id.bg_image);

        final ImageView refreshHotSearchIv = root.findViewById(R.id.refresh_hot_search_iv);
        refreshHotSearchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotSearchUI != null) {
                    hotSearchUI.refreshHotSearch(new HotSearchUI.IRefreshHotSearchDoneListener() {
                        @Override
                        public void onRefreshDone() {
                            refreshHotSearchIv.clearAnimation();
                        }
                    });
                    Animation rotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                    refreshHotSearchIv.startAnimation(rotateAnim);
                }
            }
        });
        hotSearchRecyclerView = root.findViewById(R.id.hot_search_items_recyclerView);
        hotSearchUI = new HotSearchUI(hotSearchRecyclerView, getActivity());

        ImageView goToGalleryPreviewIv = root.findViewById(R.id.btn_gallery);
        goToGalleryPreviewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AlbumPreviewUI.class);
                startActivityForResult(intent, ConstantsUI.AlbumPreviewUI.ACTIVITY_REQUEST_QUERY_IMG);
            }
        });
        goToGalleryPreviewIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NetSceneGetTrainProgress scene = new NetSceneGetTrainProgress(getActivity());
                OIKernel.getNetSceneQueue().doScene(scene);
                return true;
            }
        });

        ImageView gotoTakePhotoIv = root.findViewById(R.id.btn_take_photo);
        gotoTakePhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhotoUtil.takePhoto(IndexPagerUI.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "[onActivityResult] requestCode: %s, resultCode: %s", requestCode, resultCode);
        switch (requestCode) {
            case ConstantsUI.LauncherUI.REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String photoPath = TakePhotoUtil.getLastPhotoPath();
                    if (new File(photoPath).exists()) {
                        NetSceneQueryImg scene = new NetSceneQueryImg(getContext(), photoPath);
                        OIKernel.getNetSceneQueue().doScene(scene);
                    }
                }
                break;
            case ConstantsUI.AlbumPreviewUI.ACTIVITY_REQUEST_QUERY_IMG:
                if (data == null) {
                    Log.e(TAG, "[onActivityResult] ACTIVITY_REQUEST_QUERY_IMG, data == null");
                    return;
                }
                String imgPath = data.getStringExtra(ConstantsUI.AlbumPreviewUI.KSELECT_IMG_PATH);
                if (imgPath == null) {
                    Log.e(TAG, "[onActivityResult] ACTIVITY_REQUEST_QUERY_IMG, imgPath == null");
                    return;
                }
                NetSceneQueryImg netScene = new NetSceneQueryImg(getContext(), imgPath);
                OIKernel.getNetSceneQueue().doScene(netScene);
                break;
        }
    }
}
