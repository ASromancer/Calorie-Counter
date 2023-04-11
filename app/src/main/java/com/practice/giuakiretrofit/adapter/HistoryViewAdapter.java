package com.practice.giuakiretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.practice.giuakiretrofit.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.practice.giuakiretrofit.dto.FoodTrackingHistory;

import java.util.List;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.ViewHolder> {
    private Context context;
    private List<FoodTrackingHistory> historyList;

    public HistoryViewAdapter(Context context) {
        this.context = context;
    }

    public void setHistoryList(List<FoodTrackingHistory> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgURL = historyList.get(position).getFood().getImage();
        imgURL = (imgURL == null || imgURL.isEmpty()) ? "https://upload.wikimedia.org/wikipedia/commons/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg" : imgURL;
        Glide.with(context)
                .load(imgURL)
                .centerCrop()
                .into(holder.foodImage);
        holder.txtConsumedDateTime.setText(historyList.get(position).getConsumedDatetime());
        holder.txtFoodName.setText(historyList.get(position).getFood().getName());
        //holder.txtConsumedGram.setText(historyList.get(position).getConsumedGram().toString().concat(" gram"));
        //holder.txtConsumedCalories.setText(historyList.get(position).getConsumedCalories().toString().concat(" kcal"));
    }

    @Override
    public int getItemCount() {
        return this.historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFoodName, txtConsumedDateTime, txtConsumedGram, txtConsumedCalories;
        private ImageView foodImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setInitial();
            // setControl();
        }
        private void setInitial() {
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtConsumedDateTime = itemView.findViewById(R.id.txtConsumedDateTime);
            txtConsumedGram = itemView.findViewById(R.id.txtConsumedGram);
            txtConsumedCalories = itemView.findViewById(R.id.txtConsumedCalories);
            foodImage = itemView.findViewById(R.id.foodImageView);
        }
        private void setControl() {

        }
    }
}
