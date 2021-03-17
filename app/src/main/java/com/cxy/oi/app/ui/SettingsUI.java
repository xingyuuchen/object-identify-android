package com.cxy.oi.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cxy.oi.R;
import com.cxy.oi.app.netscene.NetSceneRegister;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;


public class SettingsUI extends Fragment {
    private static final String TAG = "SettingsUI";

    private RelativeLayout ui;
    private TextView usrIdTv;

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
        ImageView avatarIv = root.findViewById(R.id.avatar_iv);
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        usrIdTv = root.findViewById(R.id.usrid_tv);

        final TextView nicknameTv = root.findViewById(R.id.nickname_tv);
        nicknameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        }
    }
}
