package com.example.bios.inventoryapp.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class productContract {
    private productContract() {

    }
    public static final String AUTHORITY="com.example.bios.inventoryapp";
    public static final Uri BASE_URI=Uri.parse("content://"+AUTHORITY);
    public static final String PATH="product";
    public static final class ProductEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;



        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_URI,PATH);
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY="quantity";
        public static final String COLUMN_PRODUCT_Supplier_NAME="supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE="supplier_phone";



    }
}
