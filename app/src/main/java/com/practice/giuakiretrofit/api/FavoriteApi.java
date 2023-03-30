package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.model.Favorite;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FavoriteApi {

    @GET("favfoods/{userId}")
    Call<List< Favorite >> getFavoriteFoodByUserId(
            @Path("userId") int userId,
            @Header("Authorization") String token);
}
