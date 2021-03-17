package com.cxy.oi.kernel.contants;

public final class ConstantsProtocol {
    private ConstantsProtocol() {
    }

    public static final int ERR_OK = 0;

    /**
     * 网络错误
     */
    public static final int ERR_INVALID_SOCKET =    -1;
    public static final int ERR_CONNECT_FAIL =      -2;
    public static final int ERR_SEND_FAIL =         -3;
    public static final int ERR_RECV_FAIL =         -4;
    public static final int ERR_OPERATION_TIMEOUT = -5;

    /**
     * 本地错误
     */
    private static final int ERR_LOCAL = -1000;
    public static final int ERR_REQ_DATA_ILLEGAL =  ERR_LOCAL - 1;
    public static final int ERR_NO_DISPATCHER =     ERR_LOCAL - 2;


    /**
     * All NetScene Type are declared here.
     */
    public static final int NETSCENE_TYPE_UNKNOWN_TYPE          = -1;
    public static final int NETSCENE_TYPE_HELLO_SVR             = 0;
    public static final int NETSCENE_TYPE_QUERY_IMG             = 1;
    public static final int NETSCENE_TYPE_GET_TRAIN_PROGRESS    = 2;
    public static final int NETSCENE_TYPE_REGISTER              = 3;
    public static final int NETSCENE_TYPE_UPLOAD_AVATAR         = 4;
    public static final int NETSCENE_TYPE_GET_HOT_SEARCH        = 5;
    public static final int NETSCENE_TYPE_GET_RECENT_QUERY      = 6;

}
