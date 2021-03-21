package com.cxy.oi.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cxy.oi.R;
import com.cxy.oi.app.netscene.NetSceneRegister;
import com.cxy.oi.app.netscene.NetSceneUploadAvatar;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_gallery.IPluginGallery;
import com.cxy.oi.plugin_gallery.ui.AlbumPreviewUI;


public class MinePagerUI extends Fragment {
    private static final String TAG = "SettingsUI";

    private RelativeLayout ui;
    private TextView usrIdTv;
    private TextView nicknameTv;
    private ImageView avatarIv;
    private TextView gotoChangeNicknameBtn;

    private final View.OnClickListener gotoChangeNicknameUIListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), SetNicknameUI.class);
            startActivityForResult(intent, ConstantsUI.SetNicknameUI.ACTIVITY_REQUEST_SET_NICKNAME);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_mine, null);
        initView(view);
        return view;
    }

    private void initView(View root) {
        Log.i(TAG, "[initView]");
        ui = root.findViewById(R.id.tab2wrapper);

        ListView searchHistoryListView = root.findViewById(R.id.history_items);
        SearchHistoryUI searchHistoryUI = new SearchHistoryUI(searchHistoryListView, OIApplicationContext.getContext());

        avatarIv = root.findViewById(R.id.avatar_iv);
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AlbumPreviewUI.class);
                startActivityForResult(intent, ConstantsUI.AlbumPreviewUI.ACTIVITY_REQUEST_SELECT_AVATAR);
            }
        });
        if (!Util.isNullOrNil(OIKernel.account().getAvatarPath())) {
            OIKernel.plugin(IPluginGallery.class).AttachThumbDrawable(avatarIv, OIKernel.account().getAvatarPath());
        }

        usrIdTv = root.findViewById(R.id.usrid_tv);
        gotoChangeNicknameBtn = root.findViewById(R.id.goto_change_nickname_btn);
        gotoChangeNicknameBtn.setOnClickListener(gotoChangeNicknameUIListener);
        nicknameTv = root.findViewById(R.id.nickname_tv);
        nicknameTv.setOnClickListener(gotoChangeNicknameUIListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "[onResume]");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (OIKernel.account().accountReady()) {
                int id = OIKernel.account().getUsrId();
                usrIdTv.setText(OIApplicationContext.getContext().getString(R.string.usrid,
                        id + ""));

            } else {
                Log.i(TAG, "[setUserVisibleHint] account not ready");
                NetSceneRegister netScene = new NetSceneRegister(new NetSceneRegister.Callback() {
                    @Override
                    public void onUpdateUsrId(int id) {
                        usrIdTv.setText(OIApplicationContext.getContext().getString(R.string.usrid,
                                id + ""));
                    }
                });
                OIKernel.getNetSceneQueue().doScene(netScene);
            }
            String nickname = OIKernel.account().getNickName();
            if (!Util.isNullOrNil(nickname)) {
                nicknameTv.setText(nickname);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode: %d, resultCode: %d", requestCode, resultCode);
        switch (requestCode) {
            case ConstantsUI.SetNicknameUI.ACTIVITY_REQUEST_SET_NICKNAME: {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String nickname = data.getStringExtra(ConstantsUI.SetNicknameUI.KNICKNAME);
                    if (!Util.isNullOrNil(nickname)) {
                        nicknameTv.setText(nickname);
                        return;
                    }
                    Log.w(TAG, "[onActivityResult] getStringExtra() == null");
                    nickname = OIKernel.account().getNickName();
                    if (!Util.isNullOrNil(nickname)) {
                        nicknameTv.setText(nickname);
                        return;
                    }
                    Log.w(TAG, "[onActivityResult] OIKernel.account().getNickName() == null");
                }
                break;
            }
            case ConstantsUI.AlbumPreviewUI.ACTIVITY_REQUEST_SELECT_AVATAR: {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String avatarPath = data.getStringExtra(ConstantsUI.AlbumPreviewUI.KSELECT_IMG_PATH);
                    if (avatarPath == null) {
                        Log.e(TAG, "[onActivityResult avatarPath == null");
                        return;
                    }
                    if (!OIKernel.account().accountReady()) {
                        Toast.makeText(OIApplicationContext.getContext(), "账户尚未注册",
                                Toast.LENGTH_LONG).show();
                        Log.e(TAG, "[onActivityResult] accountReady() = false");
                        return;
                    }
                    NetSceneUploadAvatar netScene = new NetSceneUploadAvatar(avatarPath, getContext(),
                            new NetSceneUploadAvatar.IAvatarUploadListener() {
                                @Override
                                public void onAvatarUploaded(String avatarPath, boolean isSuccess) {
                                    String toast = isSuccess ? "头像上传成功" : "头像上传失败";
                                    Toast.makeText(OIApplicationContext.getContext(), toast,
                                            Toast.LENGTH_SHORT).show();
                                    if (isSuccess) {
                                        OIKernel.plugin(IPluginGallery.class).
                                                AttachThumbDrawable(avatarIv, avatarPath);
                                    }
                                }
                    });
                    OIKernel.getNetSceneQueue().doScene(netScene);
                }
                break;
            }
        }
    }
}
