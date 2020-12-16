package com.cxy.oi.plugin_gallery.model;

import java.util.ArrayList;

/**
 * db查询回调
 */
public interface IQueryMediaCallback {


    void onQueryMediaDone(ArrayList<MediaItem> mediaItems);

}
