package com.practice.giuakiretrofit.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.dto.FoodTrackingHistory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackingListAdapter extends RecyclerView.Adapter<TrackingListAdapter.TrackingListViewHolder> {
    private List<FoodTrackingHistory> foodList;

    public static int mSelectedPosition = -1;

    public static int trackingId = 0;

    public TrackingListAdapter(List<FoodTrackingHistory> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public TrackingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trackinglist, parent, false);
        return new TrackingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingListViewHolder holder, int position) {
        FoodTrackingHistory foodTrackingHistory = foodList.get(position);

        if (!foodTrackingHistory.getFood().getImage().isEmpty()) {
            Picasso.get().load(foodTrackingHistory.getFood().getImage()).into(holder.ivFoodImage);
        }

        holder.tvFoodName.setText(foodTrackingHistory.getFood().getName());
        holder.tvFoodConsumedGram.setText(foodTrackingHistory.getConsumedGram() + "g");
        holder.tvConsumedDatetime.setText(foodTrackingHistory.getConsumedDatetime());
        if (position == mSelectedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), androidx.cardview.R.color.cardview_dark_background));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousSelectedPosition = mSelectedPosition;
                mSelectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(mSelectedPosition);

                FoodTrackingHistory selected = foodList.get(mSelectedPosition);
                trackingId = selected.getId();
                System.out.println("Tracking Id : " + trackingId);

            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public static class TrackingListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFoodName;
        private TextView tvFoodConsumedGram;
        private ImageView ivFoodImage;

        private TextView tvConsumedDatetime;

        public TrackingListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.iv_food_image);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvFoodConsumedGram = itemView.findViewById(R.id.tv_food_consumedGram);
            tvConsumedDatetime = itemView.findViewById(R.id.tv_food_consumedDatetime);
        }


    }
}




