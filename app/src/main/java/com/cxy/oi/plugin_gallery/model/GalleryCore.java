package com.cxy.oi.plugin_gallery.model;

public final class GalleryCore {
    private static final String TAG = "GalleryCore";

    private static MediaQueryService mediaQueryService;



    public static MediaQueryService getMediaQueryService() {
        if (mediaQueryService == null) {
            mediaQueryService = new MediaQueryService();
        }
        return mediaQueryService;
    }

}
