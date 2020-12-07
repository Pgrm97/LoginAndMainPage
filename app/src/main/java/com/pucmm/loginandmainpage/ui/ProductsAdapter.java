package com.pucmm.loginandmainpage.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.loginandmainpage.ProductActivity;
import com.pucmm.loginandmainpage.ProductDetailActivity;
import com.pucmm.loginandmainpage.R;
import com.pucmm.loginandmainpage.database.ProductData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private List<ProductData> productData;
    private Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productos_layout, parent, false);

        ProductsAdapter.MyViewHolder vh = new ProductsAdapter.MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final ProductData product = productData.get(position);

            holder.productName.setText(product.getProductCode());
            holder.productPrice.setText(product.getPrice());
            holder.productDesc.setText(product.getDescription());
            Picasso.get().load(product.getProductImage()).into(holder.productImage);
            holder.configImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("productId", product.getID());
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("productId", product.getID());
                    context.startActivity(intent);
                }
            });

    }
    public ProductsAdapter(Context _context, List<ProductData> lista){
        productData = lista;
        context = _context;
    }

    @Override
    public int getItemCount() {
        return productData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView productPrice;
        TextView productDesc;
        ImageView productImage;
        ImageView configImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDesc = itemView.findViewById(R.id.productDesc);
            productImage = itemView.findViewById(R.id.imgProducto);
            configImage = itemView.findViewById(R.id.btnConfig);
        }
    }
}
