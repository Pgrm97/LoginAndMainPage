package com.pucmm.loginandmainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pucmm.loginandmainpage.database.RoomDB;

public class ProductListActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        database = RoomDB.getInstance(ProductListActivity.this);

        String [] items = database.productDao().getAll();
        ListView listView = findViewById(R.id.ProductList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.ProductAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}