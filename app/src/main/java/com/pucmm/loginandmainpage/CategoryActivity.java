package com.pucmm.loginandmainpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.pucmm.loginandmainpage.database.CategoryData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.pucmm.loginandmainpage.database.UserData;

import java.io.ByteArrayOutputStream;

public class CategoryActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        database = RoomDB.getInstance(CategoryActivity.this);

        Button AddCategoryButton = findViewById(R.id.AddCategoryButton);
        final EditText Categorynametext;
        final ImageView Categoryimagentext;
        final byte[] imageInByte;
        Categorynametext = findViewById(R.id.CategoryName);
        Categoryimagentext = findViewById(R.id.CategoryImg);

        Bitmap bitmap = ((BitmapDrawable) Categoryimagentext.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        imageInByte = baos.toByteArray();

        AddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String CategoryName = Categorynametext.getText().toString().trim();
                byte [] CategoryImg =  imageInByte;

                if(!CategoryName.equals("")){

                    CategoryData data = new CategoryData();

                    data.setCategoryName(CategoryName);

                    database.categoryDao().insert(data);

                    Categorynametext.setText("");

                    startActivity(new Intent(CategoryActivity.this, HomeActivity.class));
                }
                if(CategoryName.equals("")){
                    Categorynametext.setError("Please enter a Category name!");
                }

            }
        });
    }

}