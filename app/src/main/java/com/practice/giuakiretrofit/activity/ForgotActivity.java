package com.practice.giuakiretrofit.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.login.ForgotRequestUsernameOrEmail;
import com.practice.giuakiretrofit.api.*;
import com.practice.giuakiretrofit.login.LoginRequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {


    private EditText edtEmail;

    private Button btnResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        setInitial();
        setControl();
    }

    private void setControl() {
        resetPassword();
    }

    private void setInitial() {
        edtEmail = findViewById(R.id.edt_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);
    }

    private void resetPassword(){
        btnResetPassword.setOnClickListener(v -> {
            ForgotApi forgotApi = RetrofitClient.getRetrofitInstance().create(ForgotApi.class);
            ForgotRequestUsernameOrEmail forgotRequestUsernameOrEmail = new ForgotRequestUsernameOrEmail(edtEmail.getText().toString().trim());
            Call<String> call = forgotApi.forgot(forgotRequestUsernameOrEmail);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code() == 200){
                        Toast.makeText(ForgotActivity.this,response.body(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }
}