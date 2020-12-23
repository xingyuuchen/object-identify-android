package com.cxy.oi.plugin_gallery.ui;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.plugin_gallery.model.GalleryCore;
import com.cxy.oi.plugin_gallery.model.IMediaQuery;
import com.cxy.oi.plugin_gallery.model.IQueryMediaCallback;
import com.cxy.oi.plugin_gallery.model.MediaItem;

import java.util.ArrayList;


public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IQueryMediaCallback {
    private static final String TAG = "AlbumAdapter";

    private final Context mContext;
    private ArrayList<MediaItem> mediaItems = new ArrayList<>();
    private static final int PREVIEW_COUNT = 24;
    private IOnItemClickListener mOnItemClickListener;
    private int selectImgIdx;


    public AlbumAdapter(Context context) {
        this.mContext = context;
        this.selectImgIdx = -1;
        GalleryCore.getMediaQueryService().addQueryMediaListener(this);
        GalleryCore.getMediaQueryService().queryMediaItemsInAlbum(IMediaQuery.QueryType.Image);

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case ConstantsUI.AlbumPreviewUI.VIEW_TYPE_IMAGE: {
                View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_preview_item, null);
                v.setOnClickListener(mOnClickListener);
                viewHolder = new ImageViewHolder(v);
                break;
            }
            case ConstantsUI.AlbumPreviewUI.VIEW_TYPE_VIDEO:
            default: {
                View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_preview_item, null);
                v.setOnClickListener(mOnClickListener);
                viewHolder = new VideoViewHolder(v);
                break;
            }
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "[onBindViewHolder] position: %d", position);
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

        if (selectImgIdx == position) {
            viewHolder.galleryIvMask.setBackgroundResource(R.color.alpha_0_5);
            viewHolder.galleryCheckBox.setChecked(true);
        } else {
            viewHolder.galleryIvMask.setBackgroundResource(R.color.alpha_0_0_5);
            viewHolder.galleryCheckBox.setChecked(false);
        }
        String imageFilePath = mediaItem.originalPath;
        long origId = mediaItem.mediaId;

        ThumbDrawable.attach(viewHolder.galleryIv, origId, imageFilePath);

        viewHolder.itemView.setTag(position);
    }


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object o = v.getTag();
            if (o instanceof Integer) {
                int position = (int) o;
                Log.i(TAG, "on item clicked, idx: %s", position);

                if (selectImgIdx == position) {
                    selectImgIdx = -1;
                } else {
                    notifyItemChanged(selectImgIdx);
                    selectImgIdx = position;
                }
                notifyItemChanged(position);

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, getItem(position));
                }
            }
        }
    };

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    public MediaItem getItem(int idx) {
        if (idx < 0 || idx >= mediaItems.size()) {
            Log.e(TAG, "[getItem] idx illegal");
            return null;
        }
        return mediaItems.get(idx);
    }

    @Override
    public void onQueryMediaDone(ArrayList<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
        notifyDataSetChanged();
    }


    public abstract static class MediaViewHolder extends RecyclerView.ViewHolder {
        public ImageView galleryIv;
        public ImageView galleryIvMask;
        public CheckBox galleryCheckBox;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryIv = itemView.findViewById(R.id.gallery_item_iv);
            galleryIvMask = itemView.findViewById(R.id.gallery_item_iv_mask);
            galleryCheckBox = itemView.findViewById(R.id.gallery_item_checkBox);
            galleryCheckBox.setChecked(false);
        }

        public abstract int getType();
    }


    private static class ImageViewHolder extends MediaViewHolder {
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public int getType() {
            return ConstantsUI.AlbumPreviewUI.VIEW_TYPE_IMAGE;
        }
    }
    private static class VideoViewHolder extends MediaViewHolder {
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        public int getType() {
            return ConstantsUI.AlbumPreviewUI.VIEW_TYPE_VIDEO;
        }
    }



    public interface IOnItemClickListener {
        void onItemClick(int position, MediaItem mediaItem);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ArrayList<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public int getSelectImgIdx() {
        return selectImgIdx;
    }

    public MediaItem getSelectedItem() {
        if (selectImgIdx == -1) {
            Log.i(TAG, "[getSelectedItem] NO item is selected");
            return null;
        }
        return getItem(selectImgIdx);
    }
}

