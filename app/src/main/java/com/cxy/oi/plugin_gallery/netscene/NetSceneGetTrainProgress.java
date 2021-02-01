package com.cxy.oi.plugin_gallery.netscene;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.cxy.oi.R;
import com.cxy.oi.autogen.NetSceneGetTrainProgressReq;
import com.cxy.oi.autogen.NetSceneGetTrainProgressResp;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.modelbase.CommonReqResp;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.google.protobuf.InvalidProtocolBufferException;


public class NetSceneGetTrainProgress extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneGetTrainProgress";

    private final Context mContext;

    public NetSceneGetTrainProgress(Context context) {
        mContext = context;
        NetSceneGetTrainProgressReq req = NetSceneGetTrainProgressReq.newBuilder().setNop(true).build();
        reqResp = new CommonReqResp.Builder()
                .setType(getType())
                .setReq(req)
                .setUri("/oi/gettrainprogress")
                .build();
    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_GET_TRAIN_PROGRESS;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        Log.i(TAG, "[doScene] reqLen size = %d", reqResp.reqLen);
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }


    @Override
    public void onNetEnd(int errCode, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode)) {
            return;
        }
        if (rr.resp == null) {
            Log.e(TAG, "[onNetEnd] rr.resp == null");
            return;
        }

        NetSceneGetTrainProgressResp resp;
        try {
            resp = NetSceneGetTrainProgressResp.parseFrom(rr.resp);
        } catch (InvalidProtocolBufferException e) {
            Log.i(TAG, "rr.resp.len: %d", rr.resp.length);
            Log.e(TAG, "[onNetEnd] InvalidProtocolBufferException: %s", e.getMessage());
            return;
        }
        boolean isRunning = resp.getIsRunning();
        int currEpoch = resp.getCurrEpoch();
        int totalEpoch = resp.getTotalEpoch();
        Log.i(TAG, "isRunning:%b, currEpoch:%d, totalEpoch:%d", isRunning, currEpoch, totalEpoch);

        String msg = isRunning ? "当前轮次 " + currEpoch + ", 共 " + totalEpoch + "。" : "当前无训练任务";
        showDialog("后台训练进度", msg);
    }

    private void showDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.icon_findmore_active)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}
