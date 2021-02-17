package com.cxy.oi.kernel.contants;

public final class ConstantsProtocol {
    private ConstantsProtocol() {
    }

    public static final int ERR_OK = 0;

    /**
     * 网络错误
     */
    public static final int ERR_INVALID_SOCKET = -1;
    public static final int ERR_CONNECT_FAIL = -2;
    public static final int ERR_SEND_FAIL = -3;
    public static final int ERR_RECV_FAIL = -4;

    /**
     * 本地错误
     */
    private static final int ERR_LOCAL = -1000;
    public static final int ERR_REQ_DATA_ILLEGAL = ERR_LOCAL - 1;
    public static final int ERR_NO_DISPATCHER = ERR_LOCAL - 2;



    public static final int NETSCENE_TYPE_UNKNOWN_TYPE = -1;
    public static final int NETSCENE_TYPE_INDEX = 0;
    public static final int NETSCENE_TYPE_QUERY_IMG = 1;
    public static final int NETSCENE_TYPE_GET_TRAIN_PROGRESS = 2;

}
