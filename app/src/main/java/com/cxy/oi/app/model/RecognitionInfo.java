package com.cxy.oi.app.model;


import com.cxy.oi.kernel.protocol.ConstantsProtocol;

/**
 *  一次查询的识别数据
 *
 *  仅负责管理数据：存本地db、与后台交互
 *
 */
public final class RecognitionInfo {

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_SENDING = 1;
    public static final int STATE_SENT = 2;
    public static final int STATE_FAILED = 3;


    private int state;
    private String itemName;
    @ObjectType private int itemType;
    private long createTime;    // 此条搜索的创建时间
    private String content;
    private String imgPath;

    public int getState() {
        return state;
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

    public boolean isPlant() {
        return getItemType() == ConstantsProtocol.ObjectItem.TYPE_PLANT;
    }

    public boolean isAnimal() {
        return getItemType() == ConstantsProtocol.ObjectItem.TYPE_ANIMAL;
    }

    public boolean isLandMark() {
        return getItemType() == ConstantsProtocol.ObjectItem.TYPE_LANDMARK;
    }

    public static class Builder {

        private final RecognitionInfo info;

        public Builder() {
            info = new RecognitionInfo();
        }

        public Builder setState(int state) {
            info.state = state;
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
