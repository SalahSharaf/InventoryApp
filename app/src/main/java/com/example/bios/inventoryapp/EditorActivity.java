package com.example.bios.inventoryapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
        openHelper = new ProductOpenHelper(this);
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
        ContentValues values = new ContentValues();
        try {
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_NAME, name);
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_PRICE, Integer.valueOf(price));
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, Integer.valueOf(quantity));
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_Supplier_NAME, supplierName);
            values.put(productContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE, supplierPhone);
            getContentResolver().insert(productContract.ProductEntry.CONTENT_URI,values);
        } catch (NumberFormatException e) {
            e.getMessage();
        }catch (IllegalArgumentException ex){ }
        if(!name.equals("")&&!price.equals("")&&!quantity.equals("")&&!supplierName.equals("")&&!supplierPhone.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"make sure to fill all data fields",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (editName.getText() != null || editPrice.getText() != null || editQuantity.getText() != null || editSupplierName.getText() != null || editSupplierPhone.getText() != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("are you sure you want to close before finishing ?.");
            builder1.setCancelable(true);
            builder1.setIcon(R.drawable.ic_warning_black_24dp);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
