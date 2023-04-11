package com.practice.giuakiretrofit.client;

import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practice.giuakiretrofit.utils.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
