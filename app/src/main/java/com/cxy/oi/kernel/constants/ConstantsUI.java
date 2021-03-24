package com.cxy.oi.kernel.constants;

public final class ConstantsUI {
    private ConstantsUI() {
    }


    public static final class LauncherUI {
        private LauncherUI() {
        }

        public static final int INDEX_FINDMORE = 0;
        public static final int INDEX_MINE = 1;

        public static final int REQUEST_PERMISSION_DEFAULT = 0;
        public static final int REQUEST_PERMISSION_CAMERA = 1;
        public static final int REQUEST_PERMISSION_CAMERA_FORCE = 2;

        public static final int REQUEST_CODE_TAKE_PHOTO = 10000;
        public static final String KPHOTO_PATH = "photo_path";

    }


    public static final class AlbumPreviewUI {
        private AlbumPreviewUI() {
        }
        public static final int ACTIVITY_REQUEST_QUERY_IMG = 10001;
        public static final int ACTIVITY_REQUEST_SELECT_AVATAR = 10002;
        public static final String KSELECT_IMG_PATH = "AlbumPreviewUI.KSELECT_IMG_PATH";
        public static final int REQUEST_PERMISSION = 0;
        public static final int SPAN_COUNT = 4;
        public static final int VIEW_TYPE_IMAGE = 0;
        public static final int VIEW_TYPE_VIDEO = 1;

    }

    public static final class HotSearchUI {
        private HotSearchUI() {
        }
        public static final int SPAN_COUNT = 3;
    }

    public static final class ObjectItem {
        private ObjectItem() {
        }
        public static final int TYPE_PLANT = 0;
        public static final int TYPE_ANIMAL = 1;
        public static final int TYPE_LANDMARK = 2;

    }

    public static final class SetNicknameUI {
        private SetNicknameUI() {
        }
        public static final int ACTIVITY_REQUEST_SET_NICKNAME = 10003;
        public static final String KNICKNAME = "nickname";
    }

    public static final class ItemDetailUI {
        private ItemDetailUI() {
        }
        public static final String KITEM_NAME = "item_name";
        public static final String KITEM_TYPE = "item_type";
        public static final String KITEM_DESC = "item_desc";
        public static final String KITEM_IMG_PATH = "item_img_path";
        public static final String KFROM_HOTSEARCH = "from_hotSearch";
    }
}

