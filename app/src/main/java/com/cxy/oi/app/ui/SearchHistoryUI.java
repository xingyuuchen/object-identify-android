package com.cxy.oi.app.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.cxy.oi.R;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.contants.ConstantsUI;
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

            if (OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().getRecognitionInfoCount() <= 0) {
                for (int i = 0; i < 4; i++) {
                    RecognitionInfo info;
                    RecognitionInfo.Builder builder = new RecognitionInfo.Builder();
                    builder.setContent("这是我查询的第" + i + "个植物，它被触碰到后会关闭")
                            .setCreateTime(System.currentTimeMillis())
                            .setItemType(ConstantsUI.ObjectItem.TYPE_PLANT)
                            .setItemName("含羞草");
                    info = builder.build();
                    OIKernel.plugin(IPluginStorage.class).getRecognitionInfoStorage().insert(info);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    historySearchItems.add(new RecognitionInfo());
                }
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

        @Override
        public void onNewRecognitionInfoInserted() {

        }

    }


}
