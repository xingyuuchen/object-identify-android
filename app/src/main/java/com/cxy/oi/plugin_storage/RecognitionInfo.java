package com.cxy.oi.plugin_storage;


import android.content.ContentValues;
import android.database.Cursor;

import com.cxy.oi.app.model.ObjectType;
import com.cxy.oi.kernel.app.IDBItem;
import com.cxy.oi.kernel.contants.ConstantsUI;

/**
 *  一次查询的识别数据
 *
 *  仅负责管理数据：存本地db、后台交互
 *
 */
public final class RecognitionInfo implements IDBItem {

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_SENDING = 1;
    public static final int STATE_SENT = 2;
    public static final int STATE_FAILED = 3;


    private long id;
    private long svrId;
    private int status;
    private String itemName;
    @ObjectType
    private int itemType;
    private long createTime;
    private String content;
    private String imgPath;
    private int flag;


    public static final String RECOGNITION_INFO_TABLE = "recognition_info_table";
    public final static String COL_ID = "infoId";
    public final static String COL_SVR_ID = "infoSvrId";
    public final static String COL_TYPE = "type";
    public final static String COL_STATUS = "status";
    public final static String COL_CREATE_TIME = "createTime";
    public final static String COL_CONTENT = "content";
    public final static String COL_IMG_PATH = "imgPath";
    public final static String COL_FLAG = "flag";



    public int getStatus() {
        return status;
    }

    public int getItemType() {
        return itemType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public String getItemName() {
        return itemName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public long getId() {
        return id;
    }

    public long getSvrId() {
        return svrId;
    }

    public int getFlag() {
        return flag;
    }

    public boolean isPlant() {
        return getItemType() == ConstantsUI.ObjectItem.TYPE_PLANT;
    }

    public boolean isAnimal() {
        return getItemType() == ConstantsUI.ObjectItem.TYPE_ANIMAL;
    }

    public boolean isLandMark() {
        return getItemType() == ConstantsUI.ObjectItem.TYPE_LANDMARK;
    }

    @Override
    public void convertFrom(Cursor cu) {

    }

    @Override
    public ContentValues convertTo() {
        ContentValues values = new ContentValues();
        values.put(COL_ID, this.id);
        values.put(COL_SVR_ID, this.svrId);
        values.put(COL_TYPE, this.itemType);
        values.put(COL_STATUS, this.status);
        values.put(COL_CREATE_TIME, this.createTime);
        values.put(COL_CONTENT, this.content);
        values.put(COL_IMG_PATH, this.imgPath);
        values.put(COL_FLAG, this.flag);
        return values;
    }



    public static class Builder {

        private final RecognitionInfo info;

        public Builder() {
            info = new RecognitionInfo();
        }

        public Builder setStatus(int status) {
            info.status = status;
            return this;
        }

        public Builder setContent(String content) {
            info.content = content;
            return this;
        }

        public Builder setCreateTime(long createTime) {
            info.createTime = createTime;
            return this;
        }

        public Builder setItemName(String itemName) {
            info.itemName = itemName;
            return this;
        }

        public Builder setItemType(@ObjectType int itemType) {
            info.itemType = itemType;
            return this;
        }

        public Builder setImgPath(String imgPath) {
            info.imgPath = imgPath;
            return this;
        }

        public RecognitionInfo build() {
            return info;
        }
    }

}
