package com.practice.giuakiretrofit.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

}
