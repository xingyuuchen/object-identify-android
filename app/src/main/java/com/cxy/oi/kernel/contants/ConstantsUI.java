package com.cxy.oi.kernel.contants;

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

        public static final int REQUEST_CODE_TAKE_PHOTO = 0;
        public static final String KPHOTO_PATH = "photo_path";

    }


    public static final class AlbumPreviewUI {
        private AlbumPreviewUI() {
        }
        public static final int ACTIVITY_REQUEST_CODE = 123;
        public static final int REQUEST_PERMISSION = 0;
        public static final int SPAN_COUNT = 4;
        public static final int VIEW_TYPE_IMAGE = 0;
        public static final int VIEW_TYPE_VIDEO = 1;

    }

    public static final class ObjectItem {
        private ObjectItem() {
        }
        public static final int TYPE_PLANT = 0;
        public static final int TYPE_ANIMAL = 1;
        public static final int TYPE_LANDMARK = 2;

    }

}

