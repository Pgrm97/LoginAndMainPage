package com.pucmm.loginandmainpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pucmm.loginandmainpage.database.CategoryData;
import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.database.RoomDB;

public class ProductActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;
    private ImageView ProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        database = RoomDB.getInstance(ProductActivity.this);
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

               // byte [] Photo =  getStringImagen(bitmap);
               // String PhotoName  = CategoryName + ".jpg";
               // StorageReference mountainImagesRef = storageRef.child("images/" + PhotoName);
             //   UploadTask uploadTask = mountainImagesRef.putBytes(Photo);

                if(!Product_code.equals("") && Product_description.equals("") && Product_price.equals("")){

                    ProductData data = new ProductData();

                    data.setProductCode(Product_code);
                    data.setDescription(Product_description);
                    data.setPrice(Product_price);

                    database.productDao().insert(data);
                    Toast.makeText(ProductActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                    Productcode.setText("");
                    description.setText("");
                    price.setText("");

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

            }
        });

    }
}