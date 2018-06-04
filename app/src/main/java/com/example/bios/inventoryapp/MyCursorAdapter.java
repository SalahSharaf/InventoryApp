package com.example.bios.inventoryapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


import com.example.bios.inventoryapp.Data.productContract;

public class MyCursorAdapter extends CursorAdapter implements View.OnClickListener {
    TextView name, price;
    TextView quantity;
    Context context;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        quantity = view.findViewById(R.id.quantity);
        name.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME)));
        price.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE)));
        quantity.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY)));
        view.setOnClickListener(this);
        view.setTag(cursor.getPosition() + 1);
    }

    @Override
    public void onClick(View v) {
        int id = (Integer) v.getTag();
        Uri uri = ContentUris.withAppendedId(productContract.ProductEntry.CONTENT_URI, id);


        Intent productDetails = new Intent(context, ProductDetails.class);
        productDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        productDetails.setData(uri);
        context.startActivity(productDetails);

    }
}

