package com.pucmm.loginandmainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.database.RoomDB;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private int id;
    private RoomDB database;
    private int cantidad = 0;
    ProductData productData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        database = RoomDB.getInstance(this);

        final TextView descripcion;
        final ImageView productImagen;
        final TextView price;
        final Button minus;
        final Button plus;
        final TextView txtcantidad;
        final Button addToCart;

        descripcion = findViewById(R.id.txtDescp);
        productImagen = findViewById(R.id.imgProducto2);
        price = findViewById(R.id.productPrice);
        minus = findViewById(R.id.btnMinus);
        plus = findViewById(R.id.btnPlus);
        txtcantidad = findViewById(R.id.txtCantidad);
        addToCart = findViewById(R.id.btnAddToCart);
        final SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.cart),Context.MODE_PRIVATE);
        final Intent cartIntent = new Intent(this, CartActivity.class);

        Intent intent = getIntent();
        id = intent.getIntExtra("productId", 0);
        if(id != 0){
            productData = database.productDao().get(id);
            descripcion.setText(productData.getDescription());
            price.setText(productData.getPrice());
            Picasso.get().load(productData.getProductImage()).into(productImagen);
            txtcantidad.setText(String.valueOf(cantidad));
        }
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad--;
                txtcantidad.setText(String.valueOf(cantidad));
                productData.setCantidad(cantidad);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad++;
                txtcantidad.setText(String.valueOf(cantidad));
                productData.setCantidad(cantidad);
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cartItems = sharedPref.getString(getString(R.string.cart_items), "");
                SharedPreferences.Editor editor = sharedPref.edit();
                if (cartItems != ""){
                    Type listType = new TypeToken<LinkedList<ProductData>>(){}.getType();
                    LinkedList<ProductData> list = new Gson().fromJson(cartItems, listType);
                    list.add(productData);
                    String serializeItems = new Gson().toJson(list);
                    editor.putString(getString(R.string.cart_items), serializeItems);


                }else{
                    LinkedList<ProductData> list = new LinkedList<>();
                    list.add(productData);
                    String serializeItems = new Gson().toJson(list);
                    editor.putString(getString(R.string.cart_items), serializeItems);
                }
                editor.commit();
                startActivity(cartIntent);
            }
        });
    }
}