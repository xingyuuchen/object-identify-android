package com.cxy.oi.app.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.cxy.oi.R;
import com.cxy.oi.app.model.SearchItemFactory;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.contants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;
import com.cxy.oi.kernel.util.TimeUtil;
import com.cxy.oi.plugin_gallery.ui.ThumbDrawable;
import com.cxy.oi.plugin_storage.IPluginStorage;
import com.cxy.oi.plugin_storage.RecognitionInfo;
import com.cxy.oi.app.model.SearchItem;
import com.cxy.oi.app.model.SearchItemPlant;
import com.cxy.oi.plugin_storage.RecognitionInfoStorage;

import java.util.ArrayList;


public class SearchHistoryUI {
    private static final String TAG = "SearchHistoryUI";

    private Context mContext;
    private ListView historyListView;
    private LayoutInflater inflater;
    private HistoryAdapter adapter;

    public SearchHistoryUI(ListView historyListView, Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.historyListView = historyListView;
        adapter = new HistoryAdapter();
        initView();

    }

    private void initView() {
        historyListView.setAdapter(adapter);
    }


    private class HistoryAdapter extends BaseAdapter implements RecognitionInfoStorage.IOnxxxxxx {
        private final ArrayList<RecognitionInfo> historySearchItems;

        public HistoryAdapter() {
            historySearchItems = new ArrayList<>();
            OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().registerListener(this);

            Cursor cursor = OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().query(3, 0);
            while (cursor.moveToNext()) {
                RecognitionInfo info = new RecognitionInfo();
                info.convertFrom(cursor);
                historySearchItems.add(info);
            }
            cursor.close();
            Log.i(TAG, "init query %s infos", historySearchItems.size());
            notifyDataSetChanged();
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

            if (convertView == null) {
                SearchItem item = SearchItemFactory.create(recognitionInfo.getItemType());
                convertView = inflater.inflate(R.layout.search_history_item, null);
                viewHolder = new SearchItem.BaseViewHolder(convertView, item);
            } else {
                viewHolder = (SearchItem.BaseViewHolder) convertView.getTag();
            }

            CharSequence cs = TimeUtil.formatTimeInList(recognitionInfo.getCreateTime());

            viewHolder.searchTime.setText(OIApplicationContext.getContext().getString(R.string.search_time, cs));
            viewHolder.itemName.setText(recognitionInfo.getItemName());
            viewHolder.itemDesc.setText(recognitionInfo.getContent());
            if (recognitionInfo.getImgPath() != null) {
                ThumbDrawable.attach(viewHolder.searchIv, 0, recognitionInfo.getImgPath());
            } else {
                viewHolder.searchIv.setImageResource(R.drawable.icon_camera);
            }

            convertView.setTag(viewHolder);
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
            Cursor cursor = OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().query(3, 0);
            historySearchItems.clear();
            while (cursor.moveToNext()) {
                RecognitionInfo info = new RecognitionInfo();
                info.convertFrom(cursor);
                historySearchItems.add(info);
            }
            cursor.close();
            Log.i(TAG, "query %s infos", historySearchItems.size());
            notifyDataSetChanged();
            notifyDataSetChanged();
        }

    }


}
