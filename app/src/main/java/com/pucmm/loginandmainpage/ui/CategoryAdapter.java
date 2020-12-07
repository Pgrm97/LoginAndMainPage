package com.pucmm.loginandmainpage.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.loginandmainpage.CategoryActivity;
import com.pucmm.loginandmainpage.ProductListActivity;
import com.pucmm.loginandmainpage.R;
import com.pucmm.loginandmainpage.database.CategoryData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoryData> categoryData;
    private Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    public CategoryAdapter(Context _context, List<CategoryData> items){
        categoryData = items;
        context = _context;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final CategoryData categoy = categoryData.get(position);

            holder.textView.setText(categoy.getCategoryName());
            Picasso.get().load(categoy.getCategoryimage()).into(holder.imageView);

            holder.imageView2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra("idCategory", categoy.getID());
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("idcategory", categoy.getID());
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ImageView imageView2;
        public MyViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.categoryname);
            imageView = (ImageView) view.findViewById(R.id.categoryImg);
            imageView2 = (ImageView) view.findViewById(R.id.configButton);
        }
    }
}
