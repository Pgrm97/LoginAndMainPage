package com.pucmm.loginandmainpage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pucmm.loginandmainpage.database.CategoryData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.pucmm.loginandmainpage.ui.CategoryAdapter;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        database = RoomDB.getInstance(CategoryListActivity.this);

        cargaritems();


        Button btn = (Button) findViewById(R.id.CategoryAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    private void cargaritems(){
        List<CategoryData> items = database.categoryDao().getAll();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvCategories);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,items);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10 ){
            cargaritems();
        }
    }
}