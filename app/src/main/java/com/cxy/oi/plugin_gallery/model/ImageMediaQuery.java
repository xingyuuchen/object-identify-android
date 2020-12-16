package com.cxy.oi.plugin_gallery.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.cxy.oi.app.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;

import java.util.ArrayList;



public class ImageMediaQuery extends BaseMediaQuery implements IMediaQuery {
    private static final String TAG = "ImageMediaQuery";


    @Override
    public Uri getUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getProjection() {
        return new String[] {
                BaseColumns._ID,
                MediaStore.MediaColumns.DATA,
                BaseMediaQuery.DATE_TAKEN_COL,
                BaseMediaQuery.DATE_MODIFIED_COL,
                MediaStore.MediaColumns.MIME_TYPE,
        };
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String[] getSelectionArgs() {
        return null;
    }

    @Override
    public String getSortOrder() {
//        return BaseMediaQuery.DATE_TAKEN_COL + " desc limit 20";
        return BaseMediaQuery.DATE_MODIFIED_COL + " desc";
    }

    @Override
    public void queryMediaInAlbum(IQueryMediaCallback callback) {
        Cursor cursor = contentResolver.query(getUri(), getProjection(), getSelection(), getSelectionArgs(), getSortOrder());
        ArrayList<MediaItem> mediaItems = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = convertFrom(cursor);
                mediaItems.add(mediaItem);
            }
            cursor.close();
        }
        callback.onQueryMediaDone(mediaItems);
    }


}
