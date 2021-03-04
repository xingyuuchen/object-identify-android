package com.cxy.oi.kernel.network;


/**
 * NetScenes which care about the uploading progress implements such interface.
 */
public interface IOnUploadListener {
    enum UploadState {
        NOT_START,
        UPLOADING,
        DONE,
    }

    void onUploadProgressUpdate(long curr, long total);

}
