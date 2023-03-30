package com.practice.giuakiretrofit.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.practice.giuakiretrofit.activity.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.activity.FoodActivity;
import com.practice.giuakiretrofit.model.*;
import com.practice.giuakiretrofit.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> mListFood;

    public FoodAdapter(List<Food> mListFood) {
        this.mListFood = mListFood;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        Picasso.get().load(food.getImage()).into(holder.ivFoodImage);
        holder.tvName.setText(food.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
            intent.putExtra("foodId", food.getId());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (mListFood != null) {
            return mListFood.size();
        }
        return 0;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        private TextView  tvName;
        private ImageView ivFoodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.iv_food_image);
            tvName = itemView.findViewById(R.id.tv_food_name);
        }
    }
}
