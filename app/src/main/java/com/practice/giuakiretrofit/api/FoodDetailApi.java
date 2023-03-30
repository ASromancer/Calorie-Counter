package com.practice.giuakiretrofit.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import com.practice.giuakiretrofit.model.Food;

//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface FoodDetailApi {
    @GET("foods/{id}")
    Call<Food> getFoodDetailById(
            @Path("id") int foodId,
            @Header("Authorization") String token);
}


