package com.cxy.oi.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cxy.oi.R;
import com.cxy.oi.app.netscene.NetSceneChangeNickname;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;


public class SetNicknameUI extends Activity {
    private static final String TAG = "SetNicknameUI";

    private static final int MAX_NICKNAME_LENGTH = 6;
    private EditText nicknameEt;
    private TextView confirmTv;


    private final TextWatcher nicknameChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            confirmTv.setEnabled(s.length() > 0 && s.length() < MAX_NICKNAME_LENGTH);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "[onCreate]");
        setContentView(R.layout.set_nickname);

        nicknameEt = findViewById(R.id.nickname_et);
        nicknameEt.addTextChangedListener(nicknameChangeListener);
        confirmTv = findViewById(R.id.nickname_btn_confirm);
        confirmTv.setEnabled(false);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nicknameEt.getText().toString();
                Log.i(TAG, "[onClick] input: %s", nickname);

                NetSceneChangeNickname netScene = new NetSceneChangeNickname(nickname,
                        new NetSceneChangeNickname.IOnNicknameChangedListener() {
                            @Override
                            public void onNickNameChanged(String nickname) {
                                OIKernel.account().saveNickname(nickname);
                                Intent intent = new Intent();
                                intent.putExtra(ConstantsUI.SetNicknameUI.KNICKNAME, nickname);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        });
                OIKernel.getNetSceneQueue().doScene(netScene);
            }
        });

    }
}
