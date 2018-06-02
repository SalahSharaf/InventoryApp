package com.example.bios.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bios.inventoryapp.Data.ProductOpenHelper;
import com.example.bios.inventoryapp.Data.productContract;

public class MainActivity extends AppCompatActivity {

    ProductOpenHelper openHelper;
    FloatingActionButton btn;
    TextView textView;
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
        textView=findViewById(R.id.instances);
        ReadTask();
    }
    public void ReadTask(){
        SQLiteDatabase db=openHelper.getReadableDatabase();
        String[] projection={
                productContract.ProductEntry.COLUMN_ID,
                productContract.ProductEntry.COLUMN_PRODUCT_NAME,
                productContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE,
                productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME
        };
        Cursor cursor=db.query(productContract.ProductEntry.TABLE_NAME,projection,null,null,null,null,null);
        textView.setText("number of instances = " + cursor.getCount());
        textView.append("\n id-name-price-quantity-supplier name-supplier phone");

        while (cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_NAME));
            String name= cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_ID));

            int price= cursor.getInt(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_PRICE));
            int quantity= cursor.getInt(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY));

            String supplier_name= cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME));

            String supplier_phone= cursor.getString(cursor.getColumnIndex(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE));
            textView.append("\n " +id+" - "+name+" - "+price+" - "+quantity+" - "+supplier_name+" - "+supplier_phone);

        }
    }
}
