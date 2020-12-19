package com.cxy.oi.kernel.app;

import android.content.ContentValues;
import android.database.Cursor;


public interface IDBItem {

    void convertFrom(final Cursor cu);

    ContentValues convertTo();

}
