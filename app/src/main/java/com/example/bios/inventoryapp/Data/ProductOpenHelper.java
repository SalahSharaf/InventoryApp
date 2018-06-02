package com.example.bios.inventoryapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bios.inventoryapp.Data.productContract.ProductEntry;
public class ProductOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "product.db";
    public static final int DATABASE_VERSION = 1;

    public ProductOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String Excution="CREATE TABLE "+ ProductEntry.TABLE_NAME +"("+
               ProductEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
               ProductEntry.COLUMN_PRODUCT_NAME+" TEXT NOT NULL ,"+
               ProductEntry.COLUMN_PRODUCT_PRICE +" INTEGER NOT NULL,"+
               ProductEntry.COLUMN_PRODUCT_QUANTITY+" INTEGER NOT NULL DEFAULT 0,"+
               ProductEntry.COLUMN_PRODUCT_Supplier_NAME+" TEXT NOT NULL ,"+
               ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE+" TEXT );";

        db.execSQL(Excution);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
