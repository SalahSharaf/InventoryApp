package com.example.bios.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class EditorActivity extends AppCompatActivity {

    EditText editName,editPrice,editQuantity,editSupplierName,editSupplierPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editName=findViewById(R.id.edit1);
        editPrice=findViewById(R.id.edit2);
        editQuantity=findViewById(R.id.edit3);
        editSupplierName=findViewById(R.id.edit4);
        editSupplierPhone=findViewById(R.id.edit5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addelement_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_elent){
            insertTask();
        }
        return super.onOptionsItemSelected(item);
    }
    public void insertTask(){

    }
}
