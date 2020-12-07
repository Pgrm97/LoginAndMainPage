package com.pucmm.loginandmainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pucmm.loginandmainpage.database.RoomDB;

public class CategoryListActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        database = RoomDB.getInstance(CategoryListActivity.this);

        String [] items = database.categoryDao().getAll();
        ListView listView = findViewById(R.id.CategoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.CategoryAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}