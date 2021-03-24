package com.cxy.oi.plugin_storage;


import android.content.ContentValues;
import android.database.Cursor;

import com.cxy.oi.app.model.ObjectType;
import com.cxy.oi.kernel.modelbase.IDBItem;
import com.cxy.oi.kernel.constants.ConstantsUI;
import com.cxy.oi.kernel.util.Util;

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
    private boolean hasSetId;

    private long svrId;
    private boolean hasSetSvrId;

    private int status;
    private boolean hasSetStatus;

    private String itemName;
    private boolean hasSetItemName;

    @ObjectType
    private int itemType;
    private boolean hasSetItemType;

    private long createTime;
    private boolean hasSetCreateTime;

    private String content;
    private boolean hasSetContent;

    private String imgPath;
    private boolean hasSetImgPath;

    private int flag;
    private boolean hasSetFlag;


    public static final String RECOGNITION_INFO_TABLE = "recognition_info_table";
    public final static String COL_ID = "infoId";
    public final static String COL_SVR_ID = "infoSvrId";
    public final static String COL_STATUS = "status";
    public final static String COL_ITEM_NAME = "itemName";
    public final static String COL_ITEM_TYPE = "type";
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
    public void convertFrom(Cursor cursor) {
        String[] colNames = cursor.getColumnNames();
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            switch (colName) {
                case COL_ID:
                    this.id = cursor.getLong(i);
                    this.hasSetId = true;
                    break;
                case COL_SVR_ID:
                    this.svrId = cursor.getLong(i);
                    this.hasSetSvrId = true;
                    break;
                case COL_STATUS:
                    this.status = cursor.getInt(i);
                    this.hasSetStatus = true;
                    break;
                case COL_ITEM_NAME:
                    this.itemName = cursor.getString(i);
                    this.hasSetItemName = true;
                    break;
                case COL_ITEM_TYPE:
                    this.itemType = cursor.getInt(i);
                    this.hasSetItemType = true;
                    break;
                case COL_CREATE_TIME:
                    this.createTime = cursor.getLong(i);
                    this.hasSetCreateTime = true;
                    break;
                case COL_CONTENT:
                    this.content = cursor.getString(i);
                    this.hasSetContent = true;
                    break;
                case COL_IMG_PATH:
                    this.imgPath = cursor.getString(i);
                    this.hasSetImgPath = true;
                    break;
                case COL_FLAG:
                    this.flag = cursor.getInt(i);
                    this.hasSetFlag = true;
                    break;
            }
        }
    }

    @Override
    public ContentValues convertTo() {
        ContentValues values = new ContentValues();
        if (hasSetId) {
            values.put(COL_ID, this.id);
        }
        if (hasSetSvrId) {
            values.put(COL_SVR_ID, this.svrId);
        }
        if (hasSetStatus) {
            values.put(COL_STATUS, this.status);
        }
        if (hasSetItemName) {
            values.put(COL_ITEM_NAME, Util.nullAs(this.itemName, ""));
        }
        if (hasSetItemType) {
            values.put(COL_ITEM_TYPE, this.itemType);
        }
        if (hasSetCreateTime) {
            values.put(COL_CREATE_TIME, this.createTime);
        }
        if (hasSetContent) {
            values.put(COL_CONTENT, Util.nullAs(this.content, ""));
        }
        if (hasSetImgPath) {
            values.put(COL_IMG_PATH, Util.nullAs(this.imgPath, ""));
        }
        if (hasSetFlag) {
            values.put(COL_FLAG, this.flag);
        }
        return values;
    }



    public static class Builder {

        private final RecognitionInfo info;

        public Builder() {
            info = new RecognitionInfo();
        }

        public Builder setSvrId(long svrId) {
            info.svrId = svrId;
            info.hasSetSvrId = true;
            return this;
        }

        public Builder setStatus(int status) {
            info.status = status;
            info.hasSetStatus = true;
            return this;
        }

        public Builder setItemName(String itemName) {
            info.itemName = itemName;
            info.hasSetItemName = true;
            return this;
        }

        public Builder setItemType(@ObjectType int itemType) {
            info.itemType = itemType;
            info.hasSetItemType = true;
            return this;
        }

        public Builder setCreateTime(long createTime) {
            info.createTime = createTime;
            info.hasSetCreateTime = true;
            return this;
        }

        public Builder setContent(String content) {
            info.content = content;
            info.hasSetContent = true;
            return this;
        }

        public Builder setImgPath(String imgPath) {
            info.imgPath = imgPath;
            info.hasSetImgPath = true;
            return this;
        }

        public Builder setFlag(int flag) {
            info.flag = flag;
            info.hasSetFlag = true;
            return this;
        }

        public RecognitionInfo build() {
            return info;
        }
    }

}
