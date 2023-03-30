package com.practice.giuakiretrofit.dto;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

}
