package com.practice.giuakiretrofit.api;
import com.practice.giuakiretrofit.login.ForgotRequestUsernameOrEmail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ForgotApi {
    @POST("auth/forgot")
    Call<String> forgot(@Body ForgotRequestUsernameOrEmail forgotRequestUsernameOrEmail);
}
