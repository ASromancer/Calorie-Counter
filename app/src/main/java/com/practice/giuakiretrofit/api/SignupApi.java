package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.dto.UserInfo;
import com.practice.giuakiretrofit.login.LoginRequest;
import com.practice.giuakiretrofit.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignupApi {
    @POST("auth/register")
    Call<Void> signup(@Body UserInfo userInfo);
}
