package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.activity.LoginActivity;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.api.AddFoodToFavoriteApi;
import com.practice.giuakiretrofit.api.DeleteFoodFromFavoriteApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.model.Account;
import com.practice.giuakiretrofit.model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Activity {
    Account account = new Account();
    User user = new User();

    ImageView ivProfileImage;
    TextView tvProfileName, tvProfileDob, tvProfileGender, tvProfileWeight, tvProfileHeight, tvProfileEmail;

    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setInitial();
        setControl();
    }

    private void setControl() {
        callApiGetAccount(LoginActivity.username, LoginActivity.token);
        logOut();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                Intent intentHome = new Intent(ProfileActivity.this, HomeActivity.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentHome);
                                // Xử lý khi chọn menu_home
                                return true;
                            case R.id.food:
                                Intent intentFood = new Intent(ProfileActivity.this, CategoryActivity.class);
                                intentFood.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFood);
                                // Xử lý khi chọn menu_search
                                return true;
                            case R.id.add:
                                // Xử lý khi chọn menu_notifications
                                return true;
                            case R.id.favorite:
                                Intent intentFavorite = new Intent(ProfileActivity.this, FavoriteActivity.class);
                                intentFavorite.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFavorite);
                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                // Xử lý khi chọn menu_profile
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void setInitial() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        ivProfileImage = (ImageView) findViewById(R.id.profile_image);
        tvProfileName = (TextView) findViewById(R.id.profile_name);
        tvProfileDob = (TextView) findViewById(R.id.profile_dob);
        tvProfileGender = (TextView) findViewById(R.id.profile_gender);
        tvProfileWeight = (TextView) findViewById(R.id.profile_weight);
        tvProfileHeight = (TextView) findViewById(R.id.profile_height);
        tvProfileEmail = (TextView) findViewById(R.id.profile_email);
        btnLogout = findViewById(R.id.btn_logout);

    }

    private void callApiGetAccount(String username, String token){
        AccountApi accountApi = RetrofitClient.getRetrofitInstance().create(AccountApi.class);
        Call<Account> call = accountApi.getAccountByUsername(username,"Bearer " + token);
        call.enqueue(new Callback<Account>() {

            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                account  = response.body();
                user = account.getUser();

                Picasso.get().load(user.getImage()).into(ivProfileImage);
                tvProfileName.setText(user.getFirstName() + " " + user.getLastName());
                tvProfileDob.setText(user.getDob());
                tvProfileWeight.setText(String.valueOf(user.getWeight()));
                tvProfileHeight.setText(String.valueOf(user.getHeight()));
                tvProfileEmail.setText(user.getEmail());
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void logOut(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentLogout = new Intent(ProfileActivity.this, LoginActivity.class);
                intentLogout.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentLogout);
            }
        });
    }


}
