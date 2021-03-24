package com.cxy.oi.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cxy.oi.R;
import com.cxy.oi.app.events.NetDispatcherReadyEvent;
import com.cxy.oi.app.netscene.NetSceneGetHotSearch;
import com.cxy.oi.app.ui.ItemDetailUI;
import com.cxy.oi.autogen.NetSceneGetHotSearchResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.app.OIApplicationContext;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.event.EventCenter;
import com.cxy.oi.kernel.event.IEvent;
import com.cxy.oi.kernel.event.IListener;
import com.cxy.oi.kernel.util.Log;
import java.util.List;


public class HotSearchDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "HotSearchDataAdapter";

    private Activity activity;
    private LayoutInflater inflater;
    private List<NetSceneGetHotSearchResp.HotSearchItem> hotSearchItems;


    private final IListener<NetDispatcherReadyEvent> listener = new IListener<NetDispatcherReadyEvent>() {
        @Override
        public void callback(IEvent event) {
            if (event instanceof NetDispatcherReadyEvent) {
                if (((NetDispatcherReadyEvent) event).data.isReady) {
                    updateHotSearchItems();
                }
            }
        }
    };

    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof Integer) {
                int position = (int) tag;
                NetSceneGetHotSearchResp.HotSearchItem item = getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(activity, ItemDetailUI.class);
                intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_NAME, item.getItemName());
                intent.putExtra(ConstantsUI.ItemDetailUI.KITEM_DESC, item.getItemDesc());
                intent.putExtra(ConstantsUI.ItemDetailUI.KFROM_HOTSEARCH, true);

                activity.startActivity(intent);
            }
        }
    };

    public HotSearchDataAdapter(LayoutInflater inflater, Activity activity) {
        this.activity = activity;
        this.inflater = inflater;

        EventCenter.INSTANCE.addListener(listener);

    }

    public interface IUpdateHotSearchItemListener {
        void onUpdateHotSearchItemDone();
    }


    public void updateHotSearchItems() {
        updateHotSearchItems(null);
    }

    public void updateHotSearchItems(final IUpdateHotSearchItemListener callback) {
        Log.i(TAG, "[updateHotSearchItems]");
        NetSceneGetHotSearch netScene = new NetSceneGetHotSearch(new NetSceneGetHotSearch.IOnHotSearchItemsLoadedListener() {
            @Override
            public void onHotSearchItemsLoaded(List<NetSceneGetHotSearchResp.HotSearchItem> hotSearchItems) {
                Log.i(TAG, "[onHotSearchItemsLoaded] loaded cnt: %d", hotSearchItems.size());
                HotSearchDataAdapter.this.hotSearchItems = hotSearchItems;
                notifyDataSetChanged();
                if (callback != null) {
                    callback.onUpdateHotSearchItemDone();
                }
            }
        });
        OIKernel.getNetSceneQueue().doScene(netScene);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = inflater.inflate(R.layout.hotsearch_item, null);
        v.setOnClickListener(onItemClickListener);

        viewHolder = new HotSearchItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof HotSearchItemViewHolder)) {
            Log.e(TAG, "[onBindViewHolder] wtf? holder not instanceof HotSearchItemViewHolder");
            return;
        }

        NetSceneGetHotSearchResp.HotSearchItem item = getItem(position);
        if (item == null) {
            Log.i(TAG, "[onBindViewHolder] item == null");
            return;
        }
        HotSearchItemViewHolder viewHolder = (HotSearchItemViewHolder) holder;

        viewHolder.itemType = item.getItemType();
        viewHolder.itemNameTv.setText(item.getItemName());
        int hotSearchHeatHeat = item.getHeat();
        viewHolder.itemHeatTv.setText(OIApplicationContext.getContext().
                getString(R.string.hot_search_heat, hotSearchHeatHeat));
        viewHolder.fillBackGround();

        viewHolder.root.setTag(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (hotSearchItems != null) {
            return hotSearchItems.size();
        }
        Log.i(TAG, "[getCount] hotSearchItems not ready");
        return 0;
    }

    public NetSceneGetHotSearchResp.HotSearchItem getItem(int position) {
        if (hotSearchItems == null) {
            return null;
        }
        if (position < 0 || position > hotSearchItems.size()) {
            return null;
        }
        return hotSearchItems.get(position);
    }


    private static class HotSearchItemViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public TextView itemNameTv;
        public TextView itemHeatTv;
        public NetSceneGetHotSearchResp.HotSearchItem.ItemType itemType;
        public HotSearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            itemNameTv = itemView.findViewById(R.id.item_name_tv);
            itemHeatTv = itemView.findViewById(R.id.item_heat_tv);
            itemType = NetSceneGetHotSearchResp.HotSearchItem.ItemType.PLANT;
        }

        public void fillBackGround() {
            Resources resources = OIApplicationContext.getContext().getResources();
            switch (itemType) {
                case PLANT: {
                    root.setBackgroundColor(resources.getColor(R.color.green_alpha1));
                    break;
                }
                case ANIMAL: {
                    root.setBackgroundColor(resources.getColor(R.color.red_alpha1));
                    break;
                }
                default:
                case LANDMARK: {
                    root.setBackgroundColor(resources.getColor(R.color.yellow_alpha1));
                    break;
                }
            }
        }
    }
}
