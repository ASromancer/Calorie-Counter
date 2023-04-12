package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.giuakiretrofit.adapter.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.api.*;
import com.practice.giuakiretrofit.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.practice.giuakiretrofit.client.RetrofitClient;
public class CategoryActivity extends AppCompatActivity {
    private RecyclerView rcvCategories;
    private List<Category> mListCategory = new ArrayList<>();
    private List<Food> mFoodList = new ArrayList<>();

    private List<Food> mFoodFilteredList = new ArrayList<>();

    private SearchView svSearchLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setInitial();
        setControl();
    }

    private void setControl() {
        //Bottom bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.food);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                Intent intentHome = new Intent(CategoryActivity.this, HomeActivity.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentHome);
                                // Xử lý khi chọn menu_home
                                return true;
                            case R.id.food:
                                recreate();
                                // Xử lý khi chọn menu_search
                                return true;
                            case R.id.add:
                                Intent intentAdd = new Intent(CategoryActivity.this, TrackingActivity.class);
                                intentAdd.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentAdd);
                                // Xử lý khi chọn menu_notifications
                                return true;
                            case R.id.favorite:
                                Intent intentFavorite = new Intent(CategoryActivity.this, FavoriteActivity.class);
                                intentFavorite.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFavorite);
                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                Intent intentProfile = new Intent(CategoryActivity.this, ProfileActivity.class);
                                intentProfile.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentProfile);

                                // Xử lý khi chọn menu_profile
                                return true;
                        }
                        return false;
                    }
                });

        //call API
        callApiGetCategories(LoginActivity.token);
        callApiGetAllFood(LoginActivity.token);

        // Search bar
        svSearchLayout.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhập xong và nhấn submit
                mFoodFilteredList.clear();

                for (Food food : mFoodList) {
                    if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                        mFoodFilteredList.add(food);
                    }
                }
                Log.i(TAG, mFoodFilteredList.size() + "");
                Intent searchIntent = new Intent(CategoryActivity.this, FoodFilteredActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                searchIntent.putExtra("mFoodFilteredList", (Serializable)mFoodFilteredList);
                startActivity(searchIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng nhập từ khóa tìm kiếm mới
                return false;
            }
        });
    }

    private void setInitial() {
        svSearchLayout = findViewById(R.id.search_layout);
        rcvCategories = findViewById(R.id.rcv_categories);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvCategories.setLayoutManager(gridLayoutManager);
    }

    private void callApiGetCategories(String token){
        CategoryApi categoryApi = RetrofitClient.getRetrofitInstance().create(CategoryApi.class);
        Call<List<Category>> call = categoryApi.getAllCategory("Bearer " + token);
        call.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mListCategory = response.body();
                CategoryAdapter adapter = new CategoryAdapter(mListCategory);
                rcvCategories.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void callApiGetAllFood(String token){
        AllFoodApi allFoodApi = RetrofitClient.getRetrofitInstance().create(AllFoodApi.class);
        Call<List<Food>> call = allFoodApi.getAllFood("Bearer " + token);
        call.enqueue(new Callback<List<Food>>() {

            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                mFoodList = response.body();
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}