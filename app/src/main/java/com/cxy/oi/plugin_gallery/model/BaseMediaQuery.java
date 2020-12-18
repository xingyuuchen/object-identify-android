package com.cxy.oi.plugin_gallery.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.cxy.oi.kernel.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;

public abstract class BaseMediaQuery implements IMediaQuery {
    private static final String TAG = "BaseMediaQuery";

    public static String DATE_MODIFIED_COL = MediaStore.MediaColumns.DATE_MODIFIED;
    public static String DATE_TAKEN_COL = MediaStore.Images.ImageColumns.DATE_TAKEN;

    protected ContentResolver contentResolver = OIApplicationContext.getContext().getContentResolver();

    public static MediaItem convertFrom(Cursor cursor) {
        if (cursor == null || cursor.isAfterLast() || cursor.isClosed()) {
            Log.e(TAG, "[convertFrom] cursor == null || cursor.isAfterLast() || cursor.isClosed()");
            return null;
        }
        MediaItem item = new MediaItem();
        int index = cursor.getColumnIndexOrThrow(BaseColumns._ID);
        if (index < 0) {
            Log.e(TAG, "[convertFrom] index(%d) < 0", index);
            return null;
        }
        item.mediaId = Long.parseLong((cursor.getString(cursor.getColumnIndexOrThrow(BaseColumns._ID))));
        item.originalPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
        item.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE));
        String addDate = cursor.getString(cursor.getColumnIndexOrThrow(DATE_MODIFIED_COL));
        String takenDate = cursor.getString(cursor.getColumnIndexOrThrow(DATE_TAKEN_COL));
        return item;
    }

}
