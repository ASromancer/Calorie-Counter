package com.practice.giuakiretrofit.activity;

import static android.app.ProgressDialog.show;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.login.LoginRequest;
import com.practice.giuakiretrofit.login.LoginResponse;
import com.practice.giuakiretrofit.api.LoginApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.model.Account;
import com.practice.giuakiretrofit.model.User;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Account account = new Account();
    User user = new User();

    ImageView ivProfileImage;
    TextView tvProfileName, tvProfileDob, tvProfileGender, tvProfileWeight, tvProfileHeight, tvProfileEmail;
    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnLogin;

    private TextView btnForgot, btnSignup;

    public static String token = "";
    public static String username = "";

    public static int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setInitial();
        setControl();
    }

    private void setInitial(){
        edtUserName = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgot = findViewById(R.id.btn_forgot);
        btnSignup = findViewById(R.id.signupText);
        edtUserName.setText("hiep");
        edtPassword.setText("31100102");
    }

    private void setControl(){
        setBtnForgotPassword();
        setBtnSignup();
        btnLogin.setOnClickListener(v -> {
            LoginApi loginApi = RetrofitClient.getRetrofitInstance().create(LoginApi.class);
            LoginRequest loginRequest = new LoginRequest(edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim());
            Call<LoginResponse> call = loginApi.login(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 200) {
                        LoginResponse loginResponse = response.body();
                        String accessToken = loginResponse.getAccessToken();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        username = edtUserName.getText().toString().trim();
                        token = accessToken;
                        callApiGetAccount(username,token);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,"Sai tài khoản hoặc mật khẩu, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Error", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void setBtnForgotPassword(){
        btnForgot.setOnClickListener(v -> {
            Intent intentForgot = new Intent(LoginActivity.this, ForgotActivity.class);
            intentForgot.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intentForgot);
        });
    }

    private void setBtnSignup(){
        btnSignup.setOnClickListener(v -> {
            Intent intentSignup = new Intent(LoginActivity.this, SignUpActivity.class);
            intentSignup.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intentSignup);
        });
    }
    private void callApiGetAccount(String username, String token){
        AccountApi accountApi = RetrofitClient.getRetrofitInstance().create(AccountApi.class);
        Call<Account> call = accountApi.getAccountByUsername(username,"Bearer " + token);
        call.enqueue(new Callback<Account>() {

            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                account  = response.body();
                user = account.getUser();
                userId = user.getId();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}