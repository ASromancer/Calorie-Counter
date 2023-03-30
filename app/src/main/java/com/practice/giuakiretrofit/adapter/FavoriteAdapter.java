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
import com.practice.giuakiretrofit.model.Favorite;

import com.practice.giuakiretrofit.activity.*;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CategoryViewHolder> {

    private final List<Favorite> mListFavorites;

    public FavoriteAdapter(List<Favorite> mListFavorites) {
        this.mListFavorites = mListFavorites;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Favorite favorite = mListFavorites.get(position);
        holder.tvName.setText(favorite.getFood().getName());
        Picasso.get().load(favorite.getFood().getImage()).into(holder.ivFavoriteFood);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
            intent.putExtra("foodId", favorite.getFood().getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mListFavorites != null) {
            return mListFavorites.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private ImageView ivFavoriteFood;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_favorite_id_food_name);
            ivFavoriteFood = itemView.findViewById(R.id.iv_favorite_food);
        }
    }
}

