package com.practice.giuakiretrofit.api;
import com.practice.giuakiretrofit.model.User;
import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserApi {
    @GET("users/{id}")
    Call<User> getUserById(
            @Header("Authorization") String token,
            @Path("id") int id);

    @PUT("users/update")
    @Multipart
    Call<Void> putUserData(
            @Header("Authorization") String token,
            @Part("user") RequestBody userJson
    );

    @Multipart
    @PUT("users/update")
    Call<Void> uploadAvatar(@Header("Authorization") String token,
                            @Part("user") RequestBody userJson,
                            @Part MultipartBody.Part image);

}
