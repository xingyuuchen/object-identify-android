package com.cxy.oi.kernel.modelbase;

import android.widget.Toast;

import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.constants.ConstantsProtocol;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.util.Log;

public abstract class NetSceneBase {


    protected CommonReqResp reqResp;

    /**
     * unique scene type id, defined in ConstantsProtocol
     */
    public abstract int getType();

    public abstract int doScene(IDispatcher dispatcher);

    public abstract String getTag();

    public void onLocalErr(int errCode) {       // callback for local err
        checkLocalErrCodeAndShowToast(errCode);
    }


    protected boolean checkErrCodeAndShowToast(int errCode, String errmsg) {
        StringBuilder toast = new StringBuilder();
        if ((errCode & ConstantsProtocol.ERR_INVALID_SOCKET) > 0) {
            toast.append("unix socket打开失败 ");
        }
        if ((errCode & ConstantsProtocol.ERR_CONNECT_FAIL) > 0) {
            toast.append("连接服务器失败 ");
        }
        if ((errCode & ConstantsProtocol.ERR_SEND_FAIL) > 0) {
            toast.append("数据发送失败 ");
        }
        if ((errCode & ConstantsProtocol.ERR_RECV_FAIL) > 0) {
            toast.append("接受数据失败 ");
        }
        if ((errCode & ConstantsProtocol.ERR_OPERATION_TIMEOUT) > 0) {
            toast.append("网络超时 ");
        }
        if ((errCode & ConstantsProtocol.ERR_SVR_UNKNOWN) > 0) {
            toast.append("服务器未知错误 ");
        }
        if ((errCode & ConstantsProtocol.ERR_SVR_DATABASE) > 0) {
            toast.append("服务器数据库错误 ");
        }
        if ((errCode & ConstantsProtocol.ERR_ILLEGAL_RESP) > 0) {
            toast.append("返回数据包不合法 ");
        }
        if ((errCode & ConstantsProtocol.ERR_FILE_BROKEN) > 0) {
            toast.append("后台文件损坏 ");
        }
        if (toast.length() > 0) {
            toast.append(errmsg);
            Log.e(getTag(), "[checkErrCodeAndShowToast] errCode: %s", errCode);
            Toast.makeText(OIApplicationContext.getContext(), toast, Toast.LENGTH_LONG).show();
        }
        return toast.length() == 0;
    }

    protected boolean checkLocalErrCodeAndShowToast(int errCode) {
        String toast = null;
        switch (errCode) {
            case ConstantsProtocol.ERR_NO_DISPATCHER:
                toast = "ERR_NO_DISPATCHER";
                break;
            case ConstantsProtocol.ERR_REQ_DATA_ILLEGAL:
                toast = "本地请求数据不完整";
                break;
            case ConstantsProtocol.ERR_OK:
                break;
            default:
                toast = "未知本地错误";
                break;
        }
        if (toast != null) {
            Log.e(getTag(), "[checkLocalErrCodeAndShowToast] errCode: %s", errCode);
            Toast.makeText(OIApplicationContext.getContext(), toast, Toast.LENGTH_LONG).show();
        }
        return toast == null;
    }

}
