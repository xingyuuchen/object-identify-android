package com.cxy.oi.plugin_gallery.model;

import android.net.Uri;

public class VideoMediaQuery extends BaseMediaQuery implements IMediaQuery {

    @Override
    public Uri getUri() {
        return null;
    }

    @Override
    public String[] getProjection() {
        return new String[0];
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }

    @Override
    public String getSortOrder() {
        return null;
    }

    @Override
    public void queryMediaInAlbum(IQueryMediaCallback callback) {

    }
}
