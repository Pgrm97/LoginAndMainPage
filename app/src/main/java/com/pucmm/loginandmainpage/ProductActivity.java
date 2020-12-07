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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucmm.loginandmainpage.database.CategoryData;
import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.squareup.picasso.Picasso;

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
    private ProductData productData = new ProductData();
    private int id;

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

        Intent intent = getIntent();
        id = intent.getIntExtra("productId",0);


        Button AddProductButton = (Button) findViewById(R.id.AddProduct);
        final EditText Productcode;
        final EditText description;
        final EditText price;
        final Spinner categories;
        final Button eliminarButton;
        Productcode = findViewById(R.id.Productcode);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        categories = findViewById(R.id.spinnerCategorias);
        eliminarButton = findViewById(R.id.eliminarButton);

        List<CategoryData> categoryData = database.categoryDao().getAll();
        categories.setAdapter(new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, categoryData));

        if(id != 0){
            productData = database.productDao().get(id);
            Productcode.setText(productData.getProductCode());
            description.setText(productData.getDescription());
            price.setText(productData.getPrice());
            Picasso.get().load(productData.getProductImage()).into(ProductImage);
            AddProductButton.setText("Editar");
            eliminarButton.setVisibility(View.VISIBLE);

            eliminarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.productDao().delete(productData);
                    onBackPressed();
                }
            });
        }

        AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductActivity.this, "Se esta guardando el producto, aguarde un momento", Toast.LENGTH_SHORT).show();
                final ProductData data = productData;
                data.setProductCode(Productcode.getText().toString().trim());
                data.setDescription(description.getText().toString().trim());
                data.setPrice(price.getText().toString().trim());
                data.setIDCategory(((CategoryData)categories.getSelectedItem() ).getID());

                byte [] Photo =  getStringImagen(bitmap);
                String PhotoName  = data.getProductCode() + ".jpg";
                final StorageReference mountainImagesRef = storageRef.child("products/" + PhotoName);
                UploadTask uploadTask = mountainImagesRef.putBytes(Photo);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override

                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                final String url = uri.toString();
                                data.setProductImage(url);
                                if(!data.getProductCode().equals("")){

                                    if (id != 0){
                                        database.productDao().update(data);
                                    }else{
                                        database.productDao().insert(data);
                                    }
                                    Productcode.setText("");
                                    description.setText("");
                                    price.setText("");
                                    ProductImage.setImageResource(R.drawable.ic_menu_camera);
                                    Toast.makeText(ProductActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                                }
                            }});}});

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