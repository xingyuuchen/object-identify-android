package com.cxy.oi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cxy.oi.R;
import com.cxy.oi.kernel.protocol.ConstantsProtocol;

public class BottomTabUI extends LinearLayout {

    private static final String TAG = "BottomTab";
    private Context mContext;

    private IOnTabClickedListener onTabClickedListener;

    private void init(Context context) {
        mContext = context;
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.tabbarGrey));

        insertFindMoreTab();
        insertMineTab();
    }


    public BottomTabUI(Context context) {
        super(context);
        init(context);
    }

    public BottomTabUI(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomTabUI(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void insertFindMoreTab() {
        TabItem findMoreTab = createTabItem(ConstantsProtocol.LauncherUI.INDEX_FINDMORE);
        findMoreTab.iconIv.setImageResource(R.drawable.icon_findmore_active);
        findMoreTab.tv.setText(R.string.find_more);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(findMoreTab.thiz, params);
    }

    private void insertMineTab() {
        TabItem mineTab = createTabItem(ConstantsProtocol.LauncherUI.INDEX_MINE);
        mineTab.iconIv.setImageResource(R.drawable.icon_mine);
        mineTab.tv.setText(R.string.mine);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(mineTab.thiz, params);

    }


    private TabItem createTabItem(int idx) {
        TabItem item = new TabItem();
        item.thiz = LayoutInflater.from(mContext).inflate(R.layout.tabbar_item, null);
        item.iconIv = item.thiz.findViewById(R.id.tab_iv);
        item.tv = item.thiz.findViewById(R.id.tab_tv);
        item.thiz.setTag(idx);
        item.thiz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = (int) v.getTag();
                if (onTabClickedListener != null) {
                    onTabClickedListener.onTabClick(idx);
                }
            }
        });
        return item;
    }


    static class TabItem {
        View thiz;
        ImageView iconIv;
        TextView tv;
    }


    public void setOnTabClickedListener(IOnTabClickedListener listener) {
        onTabClickedListener = listener;
    }

}
