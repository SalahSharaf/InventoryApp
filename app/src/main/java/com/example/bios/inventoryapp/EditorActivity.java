package com.example.bios.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bios.inventoryapp.Data.ProductOpenHelper;
import com.example.bios.inventoryapp.Data.productContract;


public class EditorActivity extends AppCompatActivity {

    EditText editName, editPrice, editQuantity, editSupplierName, editSupplierPhone;
    ProductOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editName = findViewById(R.id.edit1);
        editPrice = findViewById(R.id.edit2);
        editQuantity = findViewById(R.id.edit3);
        editSupplierName = findViewById(R.id.edit4);
        editSupplierPhone = findViewById(R.id.edit5);
        openHelper=new ProductOpenHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addelement_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_elent) {
            insertTask();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertTask() {
        String name = editName.getText().toString();
        String price = editPrice.getText().toString();
        String quantity = editQuantity.getText().toString();
        String supplierName = editSupplierName.getText().toString();
        String supplierPhone = editSupplierPhone.getText().toString();
        try {
            SQLiteDatabase db = openHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_NAME, name);
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_PRICE, Integer.valueOf(price));
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, Integer.valueOf(quantity));
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME, supplierName);
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);
            long get = db.insert(productContract.ProductEntry.TABLE_NAME, null, values);
            if (get != -1) {
                Toast.makeText(this, "New Instance added ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
            }


        }catch (NumberFormatException e){
            e.getMessage();
        }

    }
}
