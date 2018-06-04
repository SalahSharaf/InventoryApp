package com.example.bios.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bios.inventoryapp.Data.productContract;

public class ProductDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri uri;
    TextView nameE, priceE, quantityE, supplierNameE, supplierPhoneE;
    ImageButton increment, decrement, contact,delete2;
    int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        uri = getIntent().getData();
        nameE = findViewById(R.id.product_name);
        priceE = findViewById(R.id.product_price);
        quantityE = findViewById(R.id.product_quantity);
        supplierNameE = findViewById(R.id.supplier_name);
        supplierPhoneE = findViewById(R.id.supplier_phone);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        delete2=findViewById(R.id.deletebtn2);
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
        contact = findViewById(R.id.contact);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                productContract.ProductEntry._ID,
                productContract.ProductEntry.COLUMN_PRODUCT_NAME,
                productContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME,
                productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE};

        return new CursorLoader(this,
                uri, projection, null, null, null);
    }
    public  void deleteTask(){
        getContentResolver().delete(uri, null, null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }

        if (data.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME);
            int priceindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int suppliernameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME);
            int supplierphoneindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);


            // Extract out the value from the Cursor for the given column index
            String name = data.getString(nameindex);
            int price = data.getInt(priceindex);
            int quantity = data.getInt(quantityindex);
            String supplierName = data.getString(suppliernameindex);
            String supplierPhone = data.getString(supplierphoneindex);


            // Update the views on the screen with the values from the database
            nameE.setText(name);
            priceE.setText(String.valueOf(price));
            quantityE.setText(Integer.toString(quantity));
            supplierNameE.setText(supplierName);
            supplierPhoneE.setText(supplierPhone);
            increment.setTag(data);
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data = (Cursor) v.getTag();
                    int nameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME);
                    int priceindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE);
                    int suppliernameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME);
                    int supplierphoneindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);


                    // Extract out the value from the Cursor for the given column index
                    String name = data.getString(nameindex);
                    int price = data.getInt(priceindex);
                    String supplierName = data.getString(suppliernameindex);
                    String supplierPhone = data.getString(supplierphoneindex);
                    String editvalue = quantityE.getText().toString();
                    Integer newvalue = new Integer(editvalue);
                    newvalue += 1;
                    quantityE.setText(String.valueOf(newvalue));
                    ContentValues values = new ContentValues();
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_NAME, name);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_PRICE, Integer.valueOf(price));
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, newvalue);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME, supplierName);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);
                    getContentResolver().update(uri, values, null, null);
                }
            });
            decrement.setTag(data);
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data = (Cursor) v.getTag();
                    int nameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME);
                    int priceindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE);
                    int suppliernameindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME);
                    int supplierphoneindex = data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE);


                    // Extract out the value from the Cursor for the given column index
                    String name = data.getString(nameindex);
                    int price = data.getInt(priceindex);
                    String supplierName = data.getString(suppliernameindex);
                    String supplierPhone = data.getString(supplierphoneindex);
                    String editvalue = quantityE.getText().toString();
                    Integer newvalue = new Integer(editvalue);
                    newvalue -= 1;
                    quantityE.setText(String.valueOf(newvalue));
                    ContentValues values = new ContentValues();
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_NAME, name);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_PRICE, Integer.valueOf(price));
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, newvalue);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME, supplierName);
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);
                    getContentResolver().update(uri, values, null, null);
                }
            });
            contact.setTag(data);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor data = (Cursor) v.getTag();
                    String phone = data.getString(data.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE));
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
