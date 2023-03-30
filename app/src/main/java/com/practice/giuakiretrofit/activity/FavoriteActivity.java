package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.giuakiretrofit.adapter.CategoryAdapter;
import com.practice.giuakiretrofit.api.FavoriteApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.api.FoodDetailApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.model.Category;
import com.practice.giuakiretrofit.model.Favorite;
import com.practice.giuakiretrofit.model.Food;
import com.squareup.picasso.Picasso;
import com.practice.giuakiretrofit.adapter.FavoriteAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView rcvFavorite;
    private List<Favorite> mListFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setInitial();
        setControl();
    }

    private void setControl() {
        callFavoriteApi();
        //Bottom bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                Intent intentHome = new Intent(FavoriteActivity.this, HomeActivity.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentHome);
                                // Xử lý khi chọn menu_home
                                return true;
                            case R.id.food:
                                Intent intentCategory = new Intent(FavoriteActivity.this, CategoryActivity.class);
                                intentCategory.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentCategory);
                                // Xử lý khi chọn menu_search
                                return true;
                            case R.id.add:
                                // Xử lý khi chọn menu_notifications
                                return true;
                            case R.id.favorite:

                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                Intent intentProfile = new Intent(FavoriteActivity.this, ProfileActivity.class);
                                intentProfile.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentProfile);

                                // Xử lý khi chọn menu_profile
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void setInitial() {
        rcvFavorite = findViewById(R.id.rcv_favorite);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvFavorite.setLayoutManager(gridLayoutManager);
    }

    private void callFavoriteApi() {
        FavoriteApi favoriteApi = RetrofitClient.getRetrofitInstance().create(FavoriteApi.class);
        Call<List< Favorite >> call = favoriteApi.getFavoriteFoodByUserId(LoginActivity.userId, "Bearer " + LoginActivity.token);
        call.enqueue(new Callback<List< Favorite >>() {
            @Override
            public void onResponse(Call<List< Favorite >> call, Response<List< Favorite >> response) {
                mListFavorites = response.body();
                for (Favorite favorite : mListFavorites) {
                    Log.i(TAG, "onResponse: " + favorite.getFood().getName());
                }
                FavoriteAdapter adapter = new FavoriteAdapter(mListFavorites);
                rcvFavorite.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List< Favorite >> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}