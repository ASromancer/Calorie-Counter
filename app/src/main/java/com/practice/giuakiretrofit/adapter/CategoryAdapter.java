package com.practice.giuakiretrofit.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.model.Category;

import com.practice.giuakiretrofit.activity.*;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<Category> mListCategory;

    public CategoryAdapter(List<Category> mListCategory) {
        this.mListCategory = mListCategory;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        holder.tvName.setText(category.getName());
        Picasso.get().load(category.getImage()).into(holder.ivCategoryImage);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FoodActivity.class);
            intent.putExtra("cateId", category.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null) {
            return mListCategory.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private ImageView ivCategoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.iv_category_image);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}
