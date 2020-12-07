package com.pucmm.loginandmainpage.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.loginandmainpage.R;
import com.pucmm.loginandmainpage.database.ProductData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<ProductData> productData;
    private Context context;
    int cantidad;

    public CartAdapter(Context _context, List<ProductData> lista){
        productData = lista;
        context = _context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout, parent, false);

        CartAdapter.MyViewHolder vh = new CartAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ProductData product = productData.get(position);

        cantidad = product.getCantidad();
        holder.descp.setText(product.getDescription());
        holder.price.setText(product.getPrice());
        Picasso.get().load(product.getProductImage()).into(holder.imageProduct);
        holder.cantidad.setText(String.valueOf(product.getCantidad()));

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setCantidad(product.getCantidad() - 1);
                holder.cantidad.setText(String.valueOf(product.getCantidad()));

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setCantidad(product.getCantidad() + 1);
                holder.cantidad.setText(String.valueOf(product.getCantidad()));

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toggleSelection(position);
               removeSelected();
            }
        });

    }
    public void toggleSelection(int position) {
        ProductData selectedProduct =  productData.get(position);
        if(selectedProduct.selected) { // no need to check " == true"
            selectedProduct.selected = false;
        }
        else {
            selectedProduct.selected = true;
        }
        notifyDataSetChanged();
    }

    public void removeSelected() {
        for(int i = productData.size()-1; i >= 0; i--) {
            if(productData.get(i).selected) {
                productData.remove(i);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return productData.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descp;
        TextView price;
        TextView cantidad;
        Button delete;
        ImageView imageProduct;
        Button minus;
        Button plus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            descp = itemView.findViewById(R.id.txtDescp2);
            price = itemView.findViewById(R.id.txtPrice2);
            cantidad = itemView.findViewById(R.id.txtquantity);
            delete = itemView.findViewById(R.id.btnDelete);
            imageProduct = itemView.findViewById(R.id.productImage2);
            minus = itemView.findViewById(R.id.btnMinus2);
            plus = itemView.findViewById(R.id.btnPlus2);
        }
    }
}
