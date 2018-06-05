package com.example.bios.inventoryapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class ProductProvider extends ContentProvider {


    ProductOpenHelper helper;
    public static final int CODE = 1;
    public static final int CODE_ID = 2;
    public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(productContract.AUTHORITY, productContract.PATH, CODE);
        matcher.addURI(productContract.AUTHORITY, productContract.PATH + "/#", CODE_ID);

    }

    @Override
    public boolean onCreate() {
        helper = new ProductOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = null;
        int matche = matcher.match(uri);
        switch (matche) {
            case CODE:
                cursor = database.query(productContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CODE_ID:
                selection = productContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(productContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("noo code matches");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = matcher.match(uri);
        switch (match) {
            case CODE:
                return productContract.ProductEntry.CONTENT_LIST_TYPE;
            case CODE_ID:
                return productContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = matcher.match(uri);
        switch (match) {
            case CODE:
                return insertProduct(uri, values);
            default:
                throw new IllegalArgumentException("couldn't insert");
        }
    }

    public Uri insertProduct(Uri uri, ContentValues values) {
        String name = values.getAsString(productContract.ProductEntry.COLUMN_PRODUCT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("product requires a name");
        }

        // Check that the gender is valid
        Integer price = values.getAsInteger(productContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        if (price == null) {
            throw new IllegalArgumentException("product requires a price");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        String supplier_name = values.getAsString(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME);
        if (supplier_name == null) {
            throw new IllegalArgumentException("you must add a supplier for the product ");
        }

        SQLiteDatabase database = helper.getWritableDatabase();
        long id = database.insert(productContract.ProductEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Toast.makeText(getContext(), "Couldn't Insert", Toast.LENGTH_SHORT).show();
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int match = matcher.match(uri);
        int id = 0;

        switch (match) {
            case CODE:
                id = database.delete(productContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_ID:
                selection = productContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = database.delete(productContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("couldn't delete ");
        }
        if (id != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = matcher.match(uri);
        switch (match) {
            case CODE:
                return updateProduct(uri, values, selection, selectionArgs);
            case CODE_ID:
                selection = productContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("couldn't update");
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(productContract.ProductEntry.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(productContract.ProductEntry.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("product requires a valid name");
            }
        }
        if (values.containsKey(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY)) {
            String quantity = values.getAsString(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
            if (quantity == null) {
                throw new IllegalArgumentException("product requires a valid quantity");
            }
        }
        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(productContract.ProductEntry.COLUMN_PRODUCT_PRICE)) {
            Integer price = values.getAsInteger(productContract.ProductEntry.COLUMN_PRODUCT_PRICE);
            if (price == null && price > 0) {
                throw new IllegalArgumentException("product requires valid price");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME)) {
            // Check that the weight is greater than or equal to 0 kg
            String suppliername = values.getAsString(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME);
            if (suppliername == null) {
                throw new IllegalArgumentException("product requires valid supplier name");
            }
        }

        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = helper.getWritableDatabase();
        int id = database.update(productContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
        if (id != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;

    }
}
