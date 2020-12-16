package com.cxy.oi.plugin_gallery.model;


import com.cxy.oi.kernel.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MediaQueryService {
    private static final String TAG = "MediaQueryService";

    private final Set<IQueryMediaCallback> callbackListeners;
    private ImageMediaQuery imageMediaQuery;
    private VideoMediaQuery videoMediaQuery;

    private IMediaQuery.QueryType queryType;


    public MediaQueryService() {
        imageMediaQuery = new ImageMediaQuery();
        videoMediaQuery = new VideoMediaQuery();

        callbackListeners = new HashSet<>();
    }


    public void queryMediaItemsInAlbum(IMediaQuery.QueryType queryType) {

        setQueryType(queryType);
        final IMediaQuery query = getMediaQuery();
        if (query != null) {
            GalleryCore.getMediaWorkerThread().postToWorker(new Runnable() {
                @Override
                public void run() {
                    query.queryMediaInAlbum(new IQueryMediaCallback() {
                        @Override
                        public void onQueryMediaDone(ArrayList<MediaItem> mediaItems) {
                            notifyMediaQueryDone(mediaItems);
                        }
                    });
                }
            });
        } else {
            Log.i(TAG, "[queryMedia] getMediaQuery() returns null, do nothing");
        }
    }




    public void addQueryMediaListener(IQueryMediaCallback callback) {
        callbackListeners.add(callback);
    }


    public void removeMediaListener(IQueryMediaCallback callback) {
        callbackListeners.remove(callback);
    }

    public void notifyMediaQueryDone(final ArrayList<MediaItem> mediaItems) {
        GalleryCore.getMediaWorkerThread().postToUIThread(new Runnable() {
            @Override
            public void run() {
                synchronized (callbackListeners) {
                    for (IQueryMediaCallback listener : callbackListeners) {
                        listener.onQueryMediaDone(mediaItems);
                    }
                }
            }
        });
    }

    public void setQueryType(IMediaQuery.QueryType queryType) {
        this.queryType = queryType;
    }


    private IMediaQuery getMediaQuery() {
        switch (queryType) {
            case Image:
                return imageMediaQuery;
            case Video:
                return videoMediaQuery;
            case All:
            default:
                return null;
        }
    }

}
