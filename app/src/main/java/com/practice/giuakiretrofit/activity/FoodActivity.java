package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.adapter.FoodAdapter;
import com.practice.giuakiretrofit.api.FoodApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.model.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {

    private List<Food> mListFoods;

    private RecyclerView rcvFoods;
    private int cateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        setInitial();
        setControl();
    }

    private void setControl() {
        callListFoodApi(cateId, LoginActivity.token);
    }

    private void setInitial() {
        Intent intent = getIntent();
        cateId = intent.getExtras().getInt("cateId");
        mListFoods = new ArrayList<>();rcvFoods = findViewById(R.id.rcv_foods);
        LinearLayoutManager layoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);
        rcvFoods.setLayoutManager(layoutManager);
    }

    private void callListFoodApi(int cateId, String token) {
        FoodApi foodApi = RetrofitClient.getRetrofitInstance().create(FoodApi.class);
        Call<List<Food>> call = foodApi.getAllFoodByCategoryId(cateId, "Bearer " + token);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                mListFoods = response.body();
                for (Food food : mListFoods) {
                    Log.i(TAG, food.getName() + " ");
                }
                FoodAdapter adapter = new FoodAdapter(mListFoods);
                rcvFoods.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}