package com.cxy.oi.plugin_gallery.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MediaQueryService {
    private static final String TAG = "MediaQueryService";

    private Set<IQueryMediaCallback> callbackListeners;
    private ImageMediaQuery imageMediaQuery;
    private VideoMediaQuery videoMediaQuery;

    private IMediaQuery.QueryType queryType;


    public MediaQueryService() {
        imageMediaQuery = new ImageMediaQuery();
        videoMediaQuery = new VideoMediaQuery();

        callbackListeners = new HashSet<>();
    }


    public void queryMedia(IMediaQuery.QueryType queryType) {
        setQueryType(queryType);
        IMediaQuery query = getMediaQuery();
        if (query != null) {
            query.queryMediaInAlbum(new IQueryMediaCallback() {
                @Override
                public void onQueryMediaDone(ArrayList<MediaItem> mediaItems) {
                    notifyMediaQueryDone(mediaItems);
                }
            });
        }
    }




    public void addQueryMediaListener(IQueryMediaCallback callback) {
        callbackListeners.add(callback);
    }


    public void removeMediaListener(IQueryMediaCallback callback) {
        callbackListeners.remove(callback);
    }

    public void notifyMediaQueryDone(ArrayList<MediaItem> mediaItems) {
        for (IQueryMediaCallback listener : callbackListeners) {
            listener.onQueryMediaDone(mediaItems);
        }
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
