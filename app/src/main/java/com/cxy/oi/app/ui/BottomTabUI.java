package com.cxy.oi.app.ui;

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
import com.cxy.oi.kernel.constants.ConstantsUI;

public class BottomTabUI extends LinearLayout {
    private static final String TAG = "BottomTabUI";

    private Context mContext;
    private int lastClickIdx = -1;
    private IOnTabClickListener onTabClickedListener;

    private TabItem findMoreTab;
    private TabItem mineTab;

    private int colorActive = getResources().getColor(R.color.green);
    private int colorBlack = getResources().getColor(R.color.black);
    private int tabbarHeight;

    private void init(Context context) {
        mContext = context;
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.tabbarGrey));
        tabbarHeight = getResources().getDimensionPixelSize(R.dimen.tabbar_height);

        insertFindMoreTab();
        insertMineTab();
        switchToTab(ConstantsUI.LauncherUI.INDEX_FINDMORE);
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
        findMoreTab = createTabItem(ConstantsUI.LauncherUI.INDEX_FINDMORE);
        findMoreTab.tv.setText(R.string.find_more);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                tabbarHeight, 1);
        addView(findMoreTab.thiz, params);
    }

    private void insertMineTab() {
        mineTab = createTabItem(ConstantsUI.LauncherUI.INDEX_MINE);
        mineTab.tv.setText(R.string.mine);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                tabbarHeight, 1);
        addView(mineTab.thiz, params);
    }


    private TabItem createTabItem(int idx) {
        TabItem item = new TabItem();
        item.thiz = LayoutInflater.from(mContext).inflate(R.layout.tabbar_item, null);
        item.iconIv = item.thiz.findViewById(R.id.tab_iv);
        item.tv = item.thiz.findViewById(R.id.tab_tv);
        item.thiz.setTag(idx);
        item.thiz.setOnClickListener(tabbarClickListener);
        return item;
    }

    private OnClickListener tabbarClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int idx = (int) v.getTag();
            if (idx == lastClickIdx) {
                return;
            }
            switchToTab(idx);
            if (onTabClickedListener != null) {
                onTabClickedListener.onTabClick(idx);
            }
        }
    };

    public void switchToTab(int idx) {
        if (lastClickIdx == idx) {
            return;
        }
        lastClickIdx = idx;
        switch (idx) {
            case ConstantsUI.LauncherUI.INDEX_FINDMORE: {
                findMoreTab.iconIv.setImageResource(R.drawable.icon_findmore_active);
                mineTab.iconIv.setImageResource(R.drawable.icon_mine);

                findMoreTab.tv.setTextColor(colorActive);
                mineTab.tv.setTextColor(colorBlack);
                break;
            }
            case ConstantsUI.LauncherUI.INDEX_MINE: {
                findMoreTab.iconIv.setImageResource(R.drawable.icon_findmore);
                mineTab.iconIv.setImageResource(R.drawable.icon_mine_active);

                mineTab.tv.setTextColor(colorActive);
                findMoreTab.tv.setTextColor(colorBlack);
                break;
            }
        }
    }

    static class TabItem {
        View thiz;
        ImageView iconIv;
        TextView tv;
    }


    public void setOnTabClickedListener(IOnTabClickListener listener) {
        onTabClickedListener = listener;
    }

}
