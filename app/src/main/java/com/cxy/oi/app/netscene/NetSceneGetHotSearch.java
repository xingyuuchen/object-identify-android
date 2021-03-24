package com.cxy.oi.app.netscene;

import com.cxy.oi.autogen.NetSceneGetHotSearchReq;
import com.cxy.oi.autogen.NetSceneGetHotSearchResp;
import com.cxy.oi.kernel.OIKernel;
import com.cxy.oi.kernel.constants.ConstantsProtocol;
import com.cxy.oi.kernel.modelbase.NetSceneBase;
import com.cxy.oi.kernel.network.CommonReqResp;
import com.cxy.oi.kernel.network.IDispatcher;
import com.cxy.oi.kernel.network.IOnNetEnd;
import com.cxy.oi.kernel.util.Log;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;


public class NetSceneGetHotSearch extends NetSceneBase implements IOnNetEnd {
    private static final String TAG = "NetSceneGetHotSearch";

    private IOnHotSearchItemsLoadedListener callback;

    public NetSceneGetHotSearch() {
        this(null);
    }

    public NetSceneGetHotSearch(IOnHotSearchItemsLoadedListener callback) {
        this.callback = callback;
        int usr_id = OIKernel.account().getUsrId();
        NetSceneGetHotSearchReq req = NetSceneGetHotSearchReq.newBuilder()
                .setUsrId(usr_id).build();
        reqResp = new CommonReqResp.Builder()
                .setReq(req)
                .setType(getType())
                .setUri("/oi/gethotsearch")
                .build();

    }


    @Override
    public void onNetEnd(int errCode, String errmsg, CommonReqResp rr) {
        if (!checkErrCodeAndShowToast(errCode, errmsg)) {
            return;
        }
        NetSceneGetHotSearchResp resp;

        try {
            resp = NetSceneGetHotSearchResp.parseFrom(rr.resp);
        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "[onNetEnd] InvalidProtocolBufferException: %s", e.getMessage());
            return;
        }

//        List<NetSceneGetHotSearchResp.HotSearchItem> hotSearchItems = hardCodeResp();
        List<NetSceneGetHotSearchResp.HotSearchItem> hotSearchItems = resp.getHotSearchItemsList();

        int hotSearchItemCnt = resp.getHotSearchItemsCount();

        Log.i(TAG, "[onNetEnd] hotSearchItemCnt: %d", hotSearchItemCnt);
        for (NetSceneGetHotSearchResp.HotSearchItem item : hotSearchItems) {
            Log.i(TAG, "[onNetEnd] name: %s, type: %d heat: %d, %s",
                    item.getItemName(), getType(item.getItemType()), item.getHeat(), item.getItemDesc());
        }

        if (callback != null) {
            callback.onHotSearchItemsLoaded(hotSearchItems);
        }

    }

    /**
     * debug only
     */
    private List<NetSceneGetHotSearchResp.HotSearchItem> hardCodeResp() {
        List<NetSceneGetHotSearchResp.HotSearchItem> ret = new ArrayList<>();
        NetSceneGetHotSearchResp.HotSearchItem item;
        item = NetSceneGetHotSearchResp.HotSearchItem.newBuilder()
                .setItemName("猪笼草")
                .setHeat(10)
                .setItemType(NetSceneGetHotSearchResp.HotSearchItem.ItemType.PLANT)
                .build();
        NetSceneGetHotSearchResp.HotSearchItem item1;
        item1 = NetSceneGetHotSearchResp.HotSearchItem.newBuilder()
                .setItemName("皮狗")
                .setHeat(100)
                .setItemType(NetSceneGetHotSearchResp.HotSearchItem.ItemType.ANIMAL)
                .build();
        NetSceneGetHotSearchResp.HotSearchItem item2;
        item2 = NetSceneGetHotSearchResp.HotSearchItem.newBuilder()
                .setItemName("巴黎铁塔")
                .setHeat(16)
                .setItemType(NetSceneGetHotSearchResp.HotSearchItem.ItemType.LANDMARK)
                .build();
        ret.add(item);
        ret.add(item1);
        ret.add(item2);
        ret.add(item1);
        return ret;
    }

    private static int getType(NetSceneGetHotSearchResp.HotSearchItem.ItemType type) {
        switch (type) {
        case PLANT:
            return 0;
        case ANIMAL:
            return 1;
        case LANDMARK:
            return 2;
        }
        return -1;
    }

    @Override
    public int doScene(IDispatcher dispatcher) {
        return dispatcher.startTask(reqResp, this);
    }

    @Override
    public int getType() {
        return ConstantsProtocol.NETSCENE_TYPE_GET_HOT_SEARCH;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public interface IOnHotSearchItemsLoadedListener {
        void onHotSearchItemsLoaded(List<NetSceneGetHotSearchResp.HotSearchItem> hotSearchItems);
    }
}
