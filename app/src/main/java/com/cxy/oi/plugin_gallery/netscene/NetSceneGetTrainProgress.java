package com.cxy.oi.plugin_gallery.netscene;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.cxy.oi.R;
import com.cxy.oi.autogen.NetSceneGetTrainProgressReq;
import com.cxy.oi.autogen.NetSceneGetTrainProgressResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_chart.IPluginChart;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;


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
        int hitRatesCount = resp.getHitRatesCount();
        List<Float> hitRates = resp.getHitRatesList();
        Log.i(TAG, "[onNetEnd] isRunning:%b, currEpoch:%d, totalEpoch:%d, hitRatesCount:%d",
                isRunning, currEpoch, totalEpoch, hitRatesCount);
        for (float f : hitRates) {
            Log.i(TAG, "[onNetEnd] hit rate: %s", f);
        }

        String title, subtitle;
        if (isRunning && resp.getHitRatesCount() > 0) {
            title = "后台训练进度";
            subtitle = "当前轮次" + currEpoch + ", 共 " + totalEpoch + "。";
        } else {
            title = "当前无训练任务";
            subtitle = "当前轮次 0 / 0";
        }
        showTrainDialog(title, subtitle, hitRates);
    }

    private void showTrainDialog(String title, String subtitle, List<Float> hitRates) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View progressChartView = OIKernel.plugin(IPluginChart.class)
                .createTrainProgressChartView(mContext, title, subtitle, hitRates);
        progressChartView.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(progressChartView);
        dialog.show();
    }

}
