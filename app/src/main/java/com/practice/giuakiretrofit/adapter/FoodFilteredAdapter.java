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

public class FoodFilteredAdapter extends RecyclerView.Adapter<FoodFilteredAdapter.FoodFilteredViewHolder> {

    private List<Food> mFoodFilteredList;

    public FoodFilteredAdapter(List<Food> mFoodFilteredList) {
        this.mFoodFilteredList = mFoodFilteredList;
    }
    @NonNull
    @Override
    public FoodFilteredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foods_filtered, parent, false);
        return new FoodFilteredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodFilteredViewHolder holder, int position) {
        Food food = mFoodFilteredList.get(position);
        holder.tvName.setText(food.getName());
        Picasso.get().load(food.getImage()).into(holder.ivImage);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
            intent.putExtra("foodId", food.getId());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (mFoodFilteredList != null) {
            return mFoodFilteredList.size();
        }
        return 0;
    }

    public static class FoodFilteredViewHolder extends RecyclerView.ViewHolder{

        private TextView  tvName;
        private ImageView ivImage;

        public FoodFilteredViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_food_filtered_name);
            ivImage = itemView.findViewById(R.id.tv_food_filtered_image);
        }
    }
}
