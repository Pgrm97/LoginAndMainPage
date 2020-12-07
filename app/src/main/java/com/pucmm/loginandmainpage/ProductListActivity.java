package com.pucmm.loginandmainpage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.pucmm.loginandmainpage.ui.ProductsAdapter;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private int idCategory;
    List<ProductData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        database = RoomDB.getInstance(ProductListActivity.this);
        recyclerView = findViewById(R.id.rvProducts);
        Intent intent = getIntent();
        idCategory = intent.getIntExtra("idcategory", 0);

        cargaritems();

        Button btn = (Button) findViewById(R.id.ProductAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }
    private void cargaritems(){
        if(idCategory != 0){
            data = database.productDao().getByCategory(idCategory);
        }else {
            data = database.productDao().getAll();
        }


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(new ProductsAdapter(this,data));
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10){
            cargaritems();
        }
    }
}