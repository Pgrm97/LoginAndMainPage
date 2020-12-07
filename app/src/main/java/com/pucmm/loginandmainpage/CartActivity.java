package com.pucmm.loginandmainpage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pucmm.loginandmainpage.database.ProductData;
import com.pucmm.loginandmainpage.ui.CartAdapter;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    int cantidadTotal;
    int precioTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.cart),Context.MODE_PRIVATE);
        String cartItems = sharedPref.getString(getString(R.string.cart_items), "");

        layoutManager = new LinearLayoutManager(this);
        final TextView subtotal;
        final RecyclerView recyclerView;
        final Button btn;

        subtotal = findViewById(R.id.txtSubtotal);
        recyclerView = findViewById(R.id.rvCart);
        btn = findViewById(R.id.btnProced);


        if(cartItems == ""){
            subtotal.setText("Todavia no hay productos en el carrito");
        }else{
            Type listType = new TypeToken<LinkedList<ProductData>>(){}.getType();
            LinkedList<ProductData> list = new Gson().fromJson(cartItems, listType);
            cantidadTotal = 0;
            precioTotal = 0;
            for(ProductData n : list){
                cantidadTotal += n.getCantidad();
               precioTotal += Integer.getInteger(n.getPrice(),0);
            }
            subtotal.setText("Sub total (" + cantidadTotal + " items): " + precioTotal);
            recyclerView.setAdapter(new CartAdapter(this, list));
            recyclerView.setLayoutManager(layoutManager);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Se esta procediendo a completar la compra", Toast.LENGTH_SHORT).show();
                sharedPref.edit().putString(getString(R.string.cart_items), "").commit();


            }
        });
    }
}