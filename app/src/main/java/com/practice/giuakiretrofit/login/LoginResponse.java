package com.practice.giuakiretrofit.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

}
