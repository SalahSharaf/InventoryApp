package com.example.bios.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bios.inventoryapp.Data.productContract;

public class MyCursorAdapter extends CursorAdapter  {
    TextView name, price;
    TextView quantity;
    Context context;
    Button sale;
    Cursor mCursor;
    public static String ITEM_INDEX="index";

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        this.mCursor=cursor;
        return LayoutInflater.from(context).inflate(R.layout.item, null);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ///////id's
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        quantity = view.findViewById(R.id.quantity);
        name.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME)));
        price.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE)));
        quantity.setText(cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY)));
        sale = view.findViewById(R.id.sale);
        //////////////
        ///////////////values
        final int[] quantitye = {cursor.getInt(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY))};
        //////////////////////
        sale.setTag(cursor);
        view.setTag(cursor);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor1= (Cursor) v.getTag();
                if (quantitye[0] == 0) {
                    Toast.makeText(context, "Item is out of Stock!", Toast.LENGTH_SHORT).show();
                } else {
                    quantitye[0] = quantitye[0] - 1;
                    ContentValues values = new ContentValues();
                    values.put(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, quantitye[0]);
                    quantity.setText(quantitye[0] + "");
                    Uri currentItemUri = Uri.withAppendedPath(productContract.ProductEntry.CONTENT_URI, getItemId(cursor1.getPosition()) +"");
                    context.getContentResolver().update(currentItemUri, values,null,null);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor mcursor= (Cursor) v.getTag();
                Intent intent = new Intent(context,ProductDetails.class);
                long itemId = getItemId(mcursor.getPosition());
                intent.putExtra(ITEM_INDEX,itemId + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
}