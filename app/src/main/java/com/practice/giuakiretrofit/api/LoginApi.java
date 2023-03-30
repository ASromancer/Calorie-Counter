package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.login.LoginRequest;
import com.practice.giuakiretrofit.login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
