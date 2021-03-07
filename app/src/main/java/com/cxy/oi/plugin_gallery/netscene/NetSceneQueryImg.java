package com.cxy.oi.plugin_gallery.netscene;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxy.oi.R;
import com.cxy.oi.autogen.NetSceneQueryImgReq;
import com.cxy.oi.autogen.NetSceneQueryImgResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsProtocol;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.network.IOnUploadListener;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;


public class NetSceneQueryImg extends NetSceneBase implements IOnNetEnd, IOnUploadListener {
    private static final String TAG = "NetSceneQueryImg";

    private final Context mContext;
    private final String imgPath;
    private final byte[] imgData;
    private NetSceneQueryImgReq req;
    private IOnUploadListener.UploadState uploadState = UploadState.NOT_START;

    private Dialog progressDialog;
    private TextView uploadTipTv;
    private ProgressBar uploadProgressBar;
    private ProgressBar queryProgressBar;


    public NetSceneQueryImg(Context context, String imgPath) {
        this.mContext = context;
        this.imgPath = imgPath;

        imgData = Util.readFromFile(imgPath);
        if (imgData != null) {
            req = NetSceneQueryImgReq.newBuilder().
                    setImgBytes(ByteString.copyFrom(imgData)).build();
            reqResp = new CommonReqResp.Builder()
                    .setReq(req)
                    .setUri("/oi/queryimg")
                    .setType(getType())
                    .build();
        }

    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_QUERY_IMG;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        if (reqResp == null) {
            Log.e(TAG, "[doScene] reqResp == null");
            return ConstantsProtocol.ERR_REQ_DATA_ILLEGAL;
        }
        Log.i(TAG, "[doScene] reqLen size = %d", reqResp.reqLen);
        return dispatcher.startTask(reqResp, this, this);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void onLocalErr(int errCode) {
        super.onLocalErr(errCode);
        if (imgData == null) {
            Log.e(TAG, "[onLocalErr] imgData == null");
        }
        if (imgPath == null) {
            Log.e(TAG, "[onLocalErr] imgPath == null");
        }
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

        NetSceneQueryImgResp resp;
        try {
            resp = NetSceneQueryImgResp.parseFrom(rr.resp);
        } catch (InvalidProtocolBufferException e) {
            Log.i(TAG, "rr.resp.len: %d", rr.resp.length);
            Log.e(TAG, "[onNetEnd] InvalidProtocolBufferException: %s", e.getMessage());
            return;
        }

        dismissProgressDialog();

        RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
        builder.setItemType(typeToDBInt(resp.getItemType()))
                .setItemName(resp.getItemName())
                .setCreateTime(System.currentTimeMillis())
                .setContent(resp.getItemDesc())
                .setImgPath(Util.nullAs(imgPath, ""));
        RecognitionInfo info = builder.build();

        OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
    }

    private int typeToDBInt(NetSceneQueryImgResp.ItemType type) {
        switch (type) {
            case PLANT:
                return 0;
            case ANIMAL:
                return 1;
            case LANDMARK:
                return 2;
        }
        return -1;
    }


    private void showProgressDialog() {
        progressDialog = new Dialog(mContext);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);

        View view = View.inflate(mContext, R.layout.upload_img_progress, null);
        uploadTipTv = view.findViewById(R.id.upload_tip_tv);
        uploadProgressBar = view.findViewById(R.id.upload_progressbar);
        queryProgressBar = view.findViewById(R.id.query_progressbar);

        uploadProgressBar.setProgress(0);
        uploadTipTv.setText(R.string.uploading);

        progressDialog.setContentView(view);
        progressDialog.show();
    }

    private void updateProgress(int percent) {
        if (percent <= 0 || percent > 100) {
            return;
        }
        if (uploadProgressBar != null) {
            Log.e(TAG, "[updateProgress] percent: %d", percent);
            uploadProgressBar.setProgress(percent);
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onUploadProgressUpdate(long curr, long total) {
        Log.i(TAG, "[onUploadProgressUpdate] curr=%d, total=%d", curr, total);
        if (curr <= 0) {
            Log.e(TAG, "[onUploadProgressUpdate] curr=%d", curr);
            return;
        }
        if (uploadState == UploadState.NOT_START) {
            uploadState = UploadState.UPLOADING;
            showProgressDialog();
            return;
        }

        if (curr >= total) {
            uploadState = UploadState.DONE;
            if (uploadProgressBar != null && queryProgressBar != null) {
                uploadProgressBar.setVisibility(View.GONE);
                queryProgressBar.setVisibility(View.VISIBLE);
                uploadTipTv.setText(R.string.querying);
            }
            return;
        }

        updateProgress((int) (curr * 100 / total));
    }

}
