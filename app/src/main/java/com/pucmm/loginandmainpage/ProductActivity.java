package com.pucmm.loginandmainpage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pucmm.loginandmainpage.database.RoomDB;

public class ProductActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        database = RoomDB.getInstance(ProductActivity.this);
    }
}