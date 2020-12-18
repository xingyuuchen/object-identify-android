package com.cxy.oi.app.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.cxy.oi.R;
import com.cxy.oi.app.model.RecognitionInfo;
import com.cxy.oi.app.model.SearchItem;
import com.cxy.oi.app.model.SearchItemPlant;

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


    private class HistoryAdapter extends BaseAdapter {
        private final ArrayList<RecognitionInfo> historySearchItems;

        public HistoryAdapter() {
            historySearchItems = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                historySearchItems.add(new RecognitionInfo());
            }
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
            if (convertView == null) {
                SearchItem item = new SearchItemPlant();
                convertView = inflater.inflate(R.layout.search_history_item, null);
                viewHolder = new SearchItem.BaseViewHolder(convertView, item);
            } else {
                viewHolder = (SearchItem.BaseViewHolder) convertView.getTag();
            }

            RecognitionInfo recognitionInfo = getItem(position);
            viewHolder.searchIv.setImageResource(R.drawable.icon_camera);


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
    }


}
