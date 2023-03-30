package com.practice.giuakiretrofit.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.practice.giuakiretrofit.adapter.*;
import com.practice.giuakiretrofit.model.*;

import android.os.Bundle;
import android.util.Log;

import com.practice.giuakiretrofit.R;

import java.util.List;

public class FoodFilteredActivity extends AppCompatActivity {
    private RecyclerView rcvFoodFiltered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_filtered);
        setInitial();
        setControl();


    }

    private void setControl() {
    }

    private void setInitial() {
        rcvFoodFiltered = findViewById(R.id.rcv_food_filtered);
        List<Food> mFoodFilteredList = (List<Food>) getIntent().getSerializableExtra("mFoodFilteredList");
        for (Food food : mFoodFilteredList) {
            Log.d(TAG, "Food name: " + food.getName());
        }
        FoodFilteredAdapter filteredAdapter = new FoodFilteredAdapter(mFoodFilteredList);
        rcvFoodFiltered.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvFoodFiltered.setAdapter(filteredAdapter);
    }
}