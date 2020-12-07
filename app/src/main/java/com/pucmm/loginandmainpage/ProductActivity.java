package com.pucmm.loginandmainpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.database.RoomDB;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;
    private ImageView ProductImage;
    private StorageReference storageRef;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        database = RoomDB.getInstance(ProductActivity.this);
        FirebaseApp customApp = FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance(customApp);
        FirebaseStorage storage = FirebaseStorage.getInstance(customApp);
        storageRef = storage.getReference();

        ProductImage = findViewById(R.id.ProductImage);
        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);

            }
        });




        Button AddProductButton = (Button) findViewById(R.id.AddProduct);
        final EditText Productcode;
        final EditText description;
        final EditText price;
        Productcode = findViewById(R.id.Productcode);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

        AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Product_code = Productcode.getText().toString().trim();
                String Product_description = description.getText().toString().trim();
                String Product_price = price.getText().toString().trim();

                byte [] Photo =  getStringImagen(bitmap);
                String PhotoName  = Product_code + ".jpg";
                StorageReference mountainImagesRef = storageRef.child("products/" + PhotoName);


                if(!Product_code.equals("")){

                    ProductData data = new ProductData();

                    data.setProductCode(Product_code);
                    data.setDescription(Product_description);
                    data.setPrice(Product_price);

                    database.productDao().insert(data);
                    Productcode.setText("");
                    description.setText("");
                    price.setText("");
                    ProductImage.setImageResource(R.drawable.ic_menu_camera);
                    Toast.makeText(ProductActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                }
                if(Product_code.equals("")){
                    Productcode.setError("Please enter a product code!");
                }
                if(Product_description.equals("")){
                    description.setError("Please enter a product description!");
                }
                if(Product_price.equals("")){
                    price.setError("Please enter a product price!");
                }

                UploadTask uploadTask = mountainImagesRef.putBytes(Photo);
                startActivity(new Intent(ProductActivity.this, ProductListActivity.class));

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
                ProductImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}