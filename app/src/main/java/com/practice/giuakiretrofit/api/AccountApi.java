package com.practice.giuakiretrofit.api;


import com.practice.giuakiretrofit.model.*;

import com.practice.giuakiretrofit.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;


//https://calories--tracking--app.herokuapp.com/api/v1/categories/all
public interface AccountApi {


    @GET("accounts/{username}")
    Call<Account> getAccountByUsername(
            @Path("username") String username,
            @Header("Authorization") String token);
}
