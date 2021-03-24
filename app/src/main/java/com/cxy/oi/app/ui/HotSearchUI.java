package com.cxy.oi.app.ui;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.app.adapter.HotSearchDataAdapter;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.util.Log;


public class HotSearchUI {
    private static final String TAG = "HotSearchUI";

    private final Activity activity;
    private final RecyclerView hotSearchListView;
    private final LayoutInflater inflater;
    private final HotSearchDataAdapter adapter;


    public HotSearchUI(RecyclerView hotSearchListView, Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.hotSearchListView = hotSearchListView;

        GridLayoutManager layoutManager = new GridLayoutManager(activity,
                ConstantsUI.HotSearchUI.SPAN_COUNT);
        hotSearchListView.setLayoutManager(layoutManager);

        adapter = new HotSearchDataAdapter(inflater, activity);
        hotSearchListView.setAdapter(adapter);

    }

    public void refreshHotSearch() {
        refreshHotSearch(null);
    }

    public void refreshHotSearch(final IRefreshHotSearchDoneListener callback) {
        if (adapter != null) {
            Log.i(TAG, "[refreshHotSearch]");
            adapter.updateHotSearchItems(new HotSearchDataAdapter.IUpdateHotSearchItemListener() {
                @Override
                public void onUpdateHotSearchItemDone() {
                    if (callback != null) {
                        callback.onRefreshDone();
                    }
                }
            });
        }
    }

    public interface IRefreshHotSearchDoneListener {
        void onRefreshDone();
    }

    public HotSearchDataAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getHistoryRecyclerView() {
        return hotSearchListView;
    }


}
