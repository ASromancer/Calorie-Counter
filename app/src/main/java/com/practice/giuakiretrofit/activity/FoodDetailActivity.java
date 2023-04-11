package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.adapter.FavoriteAdapter;
import com.practice.giuakiretrofit.adapter.FoodAdapter;
import com.practice.giuakiretrofit.api.FoodApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.login.LoginResponse;
import com.practice.giuakiretrofit.modal.AddFoodTrackingModal;
import com.practice.giuakiretrofit.modal.EditProfileModal;
import com.practice.giuakiretrofit.model.Favorite;
import com.practice.giuakiretrofit.model.Food;
import com.practice.giuakiretrofit.api.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailActivity extends AppCompatActivity {
    Food food = new Food();
    private TextView tvFoodName, tvFoodDescription, tvFoodProtein, tvFoodFat, tvFoodCarb, tvEnergyPerServing;
    private ImageView ivFoodImage;

    private List<Favorite> mListFavorites = new ArrayList<>();
    private FloatingActionButton btnFavoriteAdd, btnAddTracking;

    private CollapsingToolbarLayout collapsingToolbar;

    private int foodId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        setInitial();
        setControl();
    }

    private void setControl() {
        callFoodDetail(foodId, LoginActivity.token);
        addFoodToFavorite(foodId, LoginActivity.token, LoginActivity.userId);
        callFavoriteApi(foodId);
        addFoodTracking(foodId);
    }

    private void addFoodTracking(int foodId) {
        btnAddTracking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddFoodTrackingModal modal = new AddFoodTrackingModal();
                Bundle args = new Bundle();
                args.putInt("foodId", foodId);
                modal.setArguments(args);
                modal.show(getSupportFragmentManager(), "add_food_tracking_modal");
            }
        });
    }

    private void setInitial() {
        tvFoodDescription = findViewById(R.id.tv_food_description);
        tvFoodProtein = findViewById(R.id.tv_food_protein);
        tvFoodFat = findViewById(R.id.tv_food_fat);
        tvFoodCarb = findViewById(R.id.tv_food_carb);
        tvEnergyPerServing = findViewById(R.id.tv_energy_per_serving);
        ivFoodImage = findViewById(R.id.iv_food_image);
        btnFavoriteAdd = findViewById(R.id.btn_favorite_add);
        btnAddTracking = findViewById(R.id.btn_add_tracking);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        Intent intent = getIntent();
        foodId = intent.getExtras().getInt("foodId");
    }


    private void callFavoriteApi(int foodId) {
        FavoriteApi favoriteApi = RetrofitClient.getRetrofitInstance().create(FavoriteApi.class);
        Call<List<Favorite>> call = favoriteApi.getFavoriteFoodByUserId(LoginActivity.userId, "Bearer " + LoginActivity.token);
        call.enqueue(new Callback<List< Favorite >>() {
            @Override
            public void onResponse(Call<List< Favorite >> call, Response<List< Favorite >> response) {
                mListFavorites = response.body();
                int tmp = 0;
                for (Favorite favorite : mListFavorites){
                    if (favorite.getFood().getId() == foodId){
                        tmp ++;
                    }
                }
                if(tmp > 0){
                    btnFavoriteAdd.setSelected(true);
                }
                else {
                    btnFavoriteAdd.setSelected(false);
                }
            }
            @Override
            public void onFailure(Call<List< Favorite >> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callFoodDetail(int foodId, String token) {
        FoodDetailApi foodDetailApi = RetrofitClient.getRetrofitInstance().create(FoodDetailApi.class);
        Call<Food> call = foodDetailApi.getFoodDetailById(foodId, "Bearer " + token);
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {

                food = response.body();
                Log.d(TAG, "onResponse: " + food.toString());
                //layout.setTitle(food.getName());
                tvFoodDescription.setText(food.getDescription());
                tvFoodProtein.setText(String.valueOf(food.getProtein()));
                tvFoodFat.setText(String.valueOf(food.getFat()));
                tvFoodCarb.setText(String.valueOf(food.getCarb()));
                tvEnergyPerServing.setText(String.valueOf(food.getEnergyPerServing()));
                collapsingToolbar.setTitle(food.getName());
                if (food.getImage().isEmpty()){
                    ivFoodImage.setImageResource(R.drawable.ic_no_food);
                }
                else {
                    Picasso.get().load(food.getImage()).into(ivFoodImage);
                }
//                Glide.with(ivFoodImage).load(food.getImage()).into(ivFoodImage);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }



    private void addFoodToFavorite(int foodId, String token, int userId){
        btnFavoriteAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btnFavoriteAdd.isSelected()) {
                    btnFavoriteAdd.setSelected(false);
                } else {
                    btnFavoriteAdd.setSelected(true);
                }
                AddFoodToFavoriteApi addFoodToFavoriteApi = RetrofitClient.getRetrofitInstance().create(AddFoodToFavoriteApi.class);
                Call<Void> call = addFoodToFavoriteApi.addFoodToFavorite(userId,foodId, "Bearer " + token);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getApplicationContext(), "Favorited!", Toast.LENGTH_LONG).show();
                        } else {
                            DeleteFoodFromFavoriteApi deleteFoodFromFavoriteApi = RetrofitClient.getRetrofitInstance().create(DeleteFoodFromFavoriteApi.class);
                            Call<Void> callDel = deleteFoodFromFavoriteApi.deleteFoodFromFavorite(userId,foodId, "Bearer " + token);
                            callDel.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(getApplicationContext(), "Unfavorited!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fail!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}