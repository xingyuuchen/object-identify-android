package com.cxy.oi.plugin_gallery.model;


import android.os.Handler;

public final class GalleryCore {
    private static final String TAG = "GalleryCore";

    private static MediaQueryService mediaQueryService;
    private static MediaCacheService mediaCacheService;


    public static MediaQueryService getMediaQueryService() {
        if (mediaQueryService == null) {
            mediaQueryService = new MediaQueryService();
        }
        return mediaQueryService;
    }

    public static MediaCacheService getMediaCacheService() {
        if (mediaCacheService == null) {
            mediaCacheService = new MediaCacheService();
        }
        return mediaCacheService;
    }

//    public static Handler MediaWorkerThread() {
//
//    }

}
