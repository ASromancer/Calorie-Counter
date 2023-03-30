package com.practice.giuakiretrofit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.api.ForgotApi;
import com.practice.giuakiretrofit.api.SignupApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.UserInfo;
import com.practice.giuakiretrofit.login.ForgotRequestUsernameOrEmail;
import com.practice.giuakiretrofit.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtUsername, edtFirstname, edtEmail, edtPassword, edtLastname;
    String username, firstname, email, password, lastname;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        setIntitial();
        setControl();
    }

    private void setControl() {
        btnSignup.setOnClickListener(v -> {
                callApiSignup();
        });

    }

    private void setIntitial() {
        edtUsername = findViewById(R.id.edt_username);
        edtFirstname = findViewById(R.id.edt_firstname);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignup = findViewById(R.id.btn_sign_up);
        edtLastname = findViewById(R.id.edt_lastname);
        username = edtUsername.getText().toString().trim();
        firstname = edtFirstname.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
    }

    private void callApiSignup() {
        SignupApi signupApi = RetrofitClient.getRetrofitInstance().create(SignupApi.class);
        UserInfo userInfo = new UserInfo(username, firstname, email, password, lastname, AppConstants.ROLE_USER);
        Call<Void> call = signupApi.signup(userInfo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(SignUpActivity.this,"Sign Up Success!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this,"Sign Up Failed!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}