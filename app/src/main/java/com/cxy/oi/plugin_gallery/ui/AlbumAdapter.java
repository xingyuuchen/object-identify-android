package com.cxy.oi.plugin_gallery.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.SquareImageView;
import com.cxy.oi.kernel.protocol.ConstantsProtocol;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.model.MediaItem;

import java.util.ArrayList;


public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "AlbumAdapter";

    private Context mContext;
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private static final int PREVIEW_COUNT = 24;



    public AlbumAdapter(Context context) {
        this.mContext = context;
        for (int i = 0; i < PREVIEW_COUNT; i++) {
            MediaItem mediaItem = new MediaItem();
            mediaItems.add(mediaItem);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_IMAGE: {
                View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_preview_item, parent);
                viewHolder = new ImageViewHolder(v);
                break;
            }
            case ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_VIDEO:
            default: {
                View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_preview_item, parent);
                viewHolder = new VideoViewHolder(v);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof MediaViewHolder)) {
            Log.e(TAG, "[onBindViewHolder] holder NOT instanceof MediaViewHolder");
            return;
        }
        MediaViewHolder viewHolder = (MediaViewHolder) holder;
        MediaItem mediaItem = getItem(position);
        if (mediaItem == null) {
            Log.e(TAG, "[onBindViewHolder] getItem(%d) == null, do nothing", position);
            return;
        }
        if (viewHolder.getType() == ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_IMAGE) {
            viewHolder.galleryIv.setImageResource(R.drawable.icon_mine);
        } else if (viewHolder.getType() == ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_VIDEO) {
            viewHolder.galleryIv.setImageResource(R.drawable.icon_mine_active);
        }

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    public MediaItem getItem(int idx) {
        if (idx <= 0 || idx >= mediaItems.size()) {
            Log.e(TAG, "[getItem] idx illegal");
            throw new IllegalArgumentException("[getItem] idx illegal");
        }
        return mediaItems.get(idx);
    }


    private abstract static class MediaViewHolder extends RecyclerView.ViewHolder {
        SquareImageView galleryIv;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryIv = itemView.findViewById(R.id.gallery_item_iv);
        }

        public abstract int getType();

    }

    private static class ImageViewHolder extends MediaViewHolder {
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public int getType() {
            return ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_IMAGE;
        }
    }

    private static class VideoViewHolder extends MediaViewHolder {
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        public int getType() {
            return ConstantsProtocol.AlbumPreviewUI.VIEW_TYPE_VIDEO;
        }
    }

    public ArrayList<MediaItem> getMediaItems() {
        return mediaItems;
    }

}

