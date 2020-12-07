package com.pucmm.loginandmainpage;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucmm.loginandmainpage.database.CategoryData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CategoryActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;
    private StorageReference storageRef;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView Categoryimagentext;
    private int id;
    private  CategoryData categoryData = new CategoryData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        id = intent.getIntExtra("idCategory",0);
        database = RoomDB.getInstance(CategoryActivity.this);
        final FirebaseApp customApp = FirebaseApp.initializeApp(this);
        FirebaseStorage storage = FirebaseStorage.getInstance(customApp);
        storageRef = storage.getReference();

        Button AddCategoryButton = (Button) findViewById(R.id.AddCategoryButton);
        final EditText Categorynametext;


        Categorynametext = findViewById(R.id.CategoryName);
        Categoryimagentext = findViewById(R.id.CategoryImg);
        Button btnBorrar = findViewById(R.id.eliminarButton);

        Categoryimagentext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);

            }
        });

        if(id != 0){
            categoryData = database.categoryDao().get(id);
            Categorynametext.setText(categoryData.getCategoryName());
            Picasso.get().load(categoryData.getCategoryimage()).into(Categoryimagentext);
            AddCategoryButton.setText("Editar");
            btnBorrar.setVisibility(View.VISIBLE);
        }
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.categoryDao().delete(categoryData);
            }
        });
        AddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CategoryActivity.this, "Espere un momento, guardando categoria", Toast.LENGTH_SHORT).show();
                final String CategoryName = Categorynametext.getText().toString().trim();
                byte [] Photo =  getStringImagen(bitmap);
                final CategoryData data = categoryData;
                String PhotoName  = CategoryName + ".jpg";
                final StorageReference mountainImagesRef = storageRef.child("images/" + PhotoName);
                UploadTask uploadTask = mountainImagesRef.putBytes(Photo);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override

                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                final String url = uri.toString();
                                data.setCategoryimage(url);
                                if(!CategoryName.equals("")){

                                    data.setCategoryName(CategoryName);
                                    if(id != 0){
                                        database.categoryDao().update(data);
                                    }else{
                                        database.categoryDao().insert(data);
                                    }
                                    Categorynametext.setText("");
                                    Categoryimagentext.setImageResource(R.drawable.ic_menu_camera);
                                    Toast.makeText(CategoryActivity.this, "Categoria agregada correctamente", Toast.LENGTH_SHORT).show();

                                }
                            }});}});


                if(CategoryName.equals("")){
                    Categorynametext.setError("Please enter a Category name!");
                }

            }
        });

    }

    public byte[] getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }

    public String getStringNameImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Categoryimagentext.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}