package com.practice.giuakiretrofit.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;
public interface DeleteFoodFromFavoriteApi {
    @DELETE("favfoods/delete/{userId}/{foodId}")
    Call<Void> deleteFoodFromFavorite(
            @Path("userId") int userId,
            @Path("foodId") int foodId,
            @Header("Authorization") String token);
}
