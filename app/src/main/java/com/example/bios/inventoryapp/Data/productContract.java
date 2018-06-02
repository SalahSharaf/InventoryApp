package com.example.bios.inventoryapp.Data;

import android.provider.BaseColumns;

public class productContract {
    private productContract() {

    }

    public static final class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "product";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY="quantity";
        public static final String COLUMN_PRODUCT_Supplier_NAME="supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE="supplier_phone";


    }
}
