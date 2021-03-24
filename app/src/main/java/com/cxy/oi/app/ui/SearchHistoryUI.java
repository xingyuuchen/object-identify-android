package com.cxy.oi.app.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.cxy.oi.app.adapter.SearchHistoryDataAdapter;


public class SearchHistoryUI {
    private static final String TAG = "SearchHistoryUI";

    private final ListView historyListView;
    private final LayoutInflater inflater;
    private final SearchHistoryDataAdapter adapter;

    public SearchHistoryUI(ListView historyListView, Activity activity) {
        inflater = LayoutInflater.from(activity);
        this.historyListView = historyListView;
        adapter = new SearchHistoryDataAdapter(inflater, activity);
        historyListView.setAdapter(adapter);

    }

    public SearchHistoryDataAdapter getAdapter() {
        return adapter;
    }

    public ListView getHistoryListView() {
        return historyListView;
    }

}
