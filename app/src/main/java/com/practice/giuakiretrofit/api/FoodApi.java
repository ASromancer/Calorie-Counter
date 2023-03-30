package com.practice.giuakiretrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import com.practice.giuakiretrofit.model.Food;

//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface FoodApi {
    @GET("foods/all/category/{cateId}")
    Call<List<Food>> getAllFoodByCategoryId(
            @Path("cateId") int categoryId,
            @Header("Authorization") String token);
}

