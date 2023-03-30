package com.practice.giuakiretrofit.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import com.practice.giuakiretrofit.model.Food;

//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface AllFoodApi {
    @GET("foods/all")
    Call<List<Food>> getAllFood(
            @Header("Authorization") String token);
}
