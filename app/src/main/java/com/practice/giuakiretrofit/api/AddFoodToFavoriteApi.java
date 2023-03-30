package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface AddFoodToFavoriteApi {
    @POST("favfoods/add/{userId}/{foodId}")
    Call<Void> addFoodToFavorite(
            @Path("userId") int userId,
            @Path("foodId") int foodId,
            @Header("Authorization") String token);
}
