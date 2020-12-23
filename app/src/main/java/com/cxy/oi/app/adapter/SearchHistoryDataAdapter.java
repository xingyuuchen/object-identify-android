package com.cxy.oi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cxy.oi.R;
import com.cxy.oi.app.model.SearchItem;
import com.cxy.oi.app.model.SearchItemFactory;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.TimeUtil;
import com.cxy.oi.kernel.util.Util;
import com.cxy.oi.plugin_gallery.ui.ThumbDrawable;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;
import com.cxy.oi.plugin_storage.RecognitionInfoStorage;

import java.util.ArrayList;



public class SearchHistoryDataAdapter extends BaseAdapter implements RecognitionInfoStorage.IOnRecognitionInfoChangeListener {
    private static final String TAG = "SearchHistoryDataAdapter";
    private final ArrayList<RecognitionInfo> historySearchItems;
    private final LayoutInflater inflater;
    private final Context context;
    private final SearchHistoryDataLoader dataLoader;

    public SearchHistoryDataAdapter(LayoutInflater inflater, Context context) {
        this.context = context;
        this.inflater = inflater;
        this.historySearchItems = new ArrayList<>();
        this.dataLoader = new SearchHistoryDataLoader();
        OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().registerListener(this);

        dataLoader.load(new SearchHistoryDataLoader.IDataLoadCallBack() {
            @Override
            public void onDataLoaded(ArrayList<RecognitionInfo> loadResult) {
                historySearchItems.addAll(loadResult);
            }
        });
        Log.i(TAG, "init query %s infos", historySearchItems.size());
    }

    @Override
    public RecognitionInfo getItem(int position) {
        if (historySearchItems != null) {
            return historySearchItems.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchItem.BaseViewHolder viewHolder;
        RecognitionInfo recognitionInfo = getItem(position);

        Log.i(TAG, "[getView] position: %s, convertView null?: %s, type: %s",
                position, convertView == null, recognitionInfo.getItemType());

        // FIXME: 此处不知如何复用, 暂时每次新建。
        SearchItem item = SearchItemFactory.create(recognitionInfo.getItemType(), context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_history_item, null);
            viewHolder = new SearchItem.BaseViewHolder(convertView, item);  // TODO：扩展为每个SearchItem定制
        } else {
            viewHolder = (SearchItem.BaseViewHolder) convertView.getTag();
            viewHolder.searchItem = item;
        }
        convertView.setTag(viewHolder);

        CharSequence time = TimeUtil.formatTime(recognitionInfo.getCreateTime());
        if (time.length() > 5) {
            viewHolder.searchTimeTv.setText(time);
        } else {
            viewHolder.searchTimeTv.setText(OIApplicationContext.getContext().getString(R.string.search_time, time));
        }
        viewHolder.itemNameTv.setText(recognitionInfo.getItemName());
        viewHolder.itemDescTv.setText(recognitionInfo.getContent());
        if (!Util.isNullOrNil(recognitionInfo.getImgPath())) {
            ThumbDrawable.attach(viewHolder.searchIv, recognitionInfo.getImgPath());
        }
        viewHolder.searchItem.customView(convertView);

        return convertView;
    }

    @Override
    public int getCount() {
        if (historySearchItems != null) {
            return historySearchItems.size();
        }
        return 0;
    }

    @Override
    public void onNewRecognitionInfoInserted() {
        historySearchItems.clear();
        dataLoader.load(new SearchHistoryDataLoader.IDataLoadCallBack() {
            @Override
            public void onDataLoaded(ArrayList<RecognitionInfo> loadResult) {
                historySearchItems.addAll(loadResult);
            }
        });
        Log.i(TAG, "query %s infos", historySearchItems.size());
        notifyDataSetChanged();
    }

}
