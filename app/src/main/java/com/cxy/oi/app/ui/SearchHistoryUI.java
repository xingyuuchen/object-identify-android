package com.cxy.oi.app.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.cxy.oi.app.adapter.SearchHistoryDataAdapter;


public class SearchHistoryUI {
    private static final String TAG = "SearchHistoryUI";

    private final Context mContext;
    private final ListView historyListView;
    private final LayoutInflater inflater;
    private final SearchHistoryDataAdapter adapter;

    public SearchHistoryUI(ListView historyListView, Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.historyListView = historyListView;
        adapter = new SearchHistoryDataAdapter(inflater, context);
        historyListView.setAdapter(adapter);

    }

    public Context getContext() {
        return mContext;
    }

    public SearchHistoryDataAdapter getAdapter() {
        return adapter;
    }

    public ListView getHistoryListView() {
        return historyListView;
    }

}
