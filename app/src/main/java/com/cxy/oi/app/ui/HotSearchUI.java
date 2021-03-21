package com.cxy.oi.app.ui;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.app.adapter.HotSearchDataAdapter;
import com.cxy.oi.kernel.contants.ConstantsUI;


public class HotSearchUI {
    private static final String TAG = "HotSearchUI";

    private final Context mContext;
    private final RecyclerView hotSearchListView;
    private final LayoutInflater inflater;
    private final HotSearchDataAdapter adapter;


    public HotSearchUI(RecyclerView hotSearchListView, Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.hotSearchListView = hotSearchListView;

        GridLayoutManager layoutManager = new GridLayoutManager(mContext,
                ConstantsUI.HotSearchUI.SPAN_COUNT);
        hotSearchListView.setLayoutManager(layoutManager);

        adapter = new HotSearchDataAdapter(inflater, context);
        hotSearchListView.setAdapter(adapter);

    }

    public Context getContext() {
        return mContext;
    }

    public HotSearchDataAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getHistoryRecyclerView() {
        return hotSearchListView;
    }


}
