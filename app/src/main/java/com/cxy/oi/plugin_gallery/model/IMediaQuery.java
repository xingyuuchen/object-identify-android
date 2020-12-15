package com.cxy.oi.plugin_gallery.model;

import android.net.Uri;

public interface IMediaQuery {

    enum QueryType {
        Image,
        Video,
        All,
    }

    Uri getUri();

    String[] getProjection();

    String getSelection();

    String[] getSelectionArgs();

    String getSortOrder();

    void queryMediaInAlbum(IQueryMediaCallback callback);

}
