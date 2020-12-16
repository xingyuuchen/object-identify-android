package com.cxy.oi.plugin_gallery.model;



public final class GalleryCore {
    private static final String TAG = "GalleryCore";

    private static MediaQueryService mediaQueryService;
    private static MediaCacheService mediaCacheService;
    private static MediaHandlerThread mediaHandlerThread;

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

    public static MediaHandlerThread getMediaWorkerThread() {
        if (mediaHandlerThread == null) {
            mediaHandlerThread = new MediaHandlerThread();
        }
        return mediaHandlerThread;
    }

}
