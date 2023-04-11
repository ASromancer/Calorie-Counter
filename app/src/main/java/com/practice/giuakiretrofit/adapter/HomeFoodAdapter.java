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
import com.practice.giuakiretrofit.activity.FoodActivity;
import com.practice.giuakiretrofit.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeFoodAdapter extends RecyclerView.Adapter<HomeFoodAdapter.HomeFoodViewHolder> {

    private final List<Category> mListCategory;

    public HomeFoodAdapter(List<Category> mListCategory) {
        this.mListCategory = mListCategory;
    }
    @NonNull
    @Override
    public HomeFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_food, parent, false);
        return new HomeFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFoodViewHolder holder, int position) {
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

    public static class HomeFoodViewHolder extends RecyclerView.ViewHolder{

        //        private TextView tvName;
        private ImageView ivCategoryImage;
        private TextView tvName;

        public HomeFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.iv_home_food_image);
            tvName = itemView.findViewById(R.id.tv_home_food_name);
        }
    }
}
