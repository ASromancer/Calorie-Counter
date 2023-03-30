package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;



//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface CategoryApi {
    @GET("categories/all")
    Call<List<Category>> getAllCategory(@Header("Authorization") String token);
}
