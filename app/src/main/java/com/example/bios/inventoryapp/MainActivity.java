package com.example.bios.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bios.inventoryapp.Data.ProductOpenHelper;
import com.example.bios.inventoryapp.Data.productContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ProductOpenHelper openHelper;
    FloatingActionButton btn;
    ListView listView;
    int LOADER_ID=1;
    MyCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),EditorActivity.class);
                startActivity(intent);

            }
        });
        openHelper=new ProductOpenHelper(this);
        listView=findViewById(R.id.listView);
        TextView emptyCaseText=findViewById(R.id.textview);
        listView.setEmptyView(emptyCaseText);
        adapter = new MyCursorAdapter(this,null);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(LOADER_ID,null,this);
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

        return new CursorLoader(this, productContract.ProductEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //adapter.swapCursor(null);
    }

}
