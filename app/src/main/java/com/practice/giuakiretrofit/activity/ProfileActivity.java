package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.activity.LoginActivity;
import com.practice.giuakiretrofit.api.AccountApi;
import com.practice.giuakiretrofit.api.AddFoodToFavoriteApi;
import com.practice.giuakiretrofit.api.DeleteFoodFromFavoriteApi;
import com.practice.giuakiretrofit.api.UserApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.UserAvatar;
import com.practice.giuakiretrofit.modal.ChangePasswordModal;
import com.practice.giuakiretrofit.modal.EditProfileModal;
import com.practice.giuakiretrofit.model.Account;
import com.practice.giuakiretrofit.model.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    Account account = new Account();
    User user = new User();
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivProfileImage;
    private TextView tvProfileFirstname, tvProfileLastname, tvProfileDob, tvProfileGender, tvProfileWeight, tvProfileHeight, tvProfileEmail;
    private LinearLayout btnLogout;
    private ImageView btnChangePassword;
    private TextView btnEditProfile;
    String[] permissions = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES
    };
    boolean is_storage_image_permitted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setInitial();
        setControl();
    }

    private void setControl() {
        callApiGetAccount(LoginActivity.username, LoginActivity.token);
        changePassword();
        changeAvatar();
        editProfile();
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
                                Intent intentAdd = new Intent(ProfileActivity.this, TrackingActivity.class);
                                intentAdd.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentAdd);
                                return true;
                            case R.id.favorite:
                                Intent intentFavorite = new Intent(ProfileActivity.this, FavoriteActivity.class);
                                intentFavorite.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFavorite);
                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                recreate();
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
        tvProfileFirstname = (TextView) findViewById(R.id.profile_firstname);
        tvProfileLastname = (TextView) findViewById(R.id.profile_lastname);
        tvProfileDob = (TextView) findViewById(R.id.profile_dob);
        tvProfileGender = (TextView) findViewById(R.id.profile_gender);
        tvProfileWeight = (TextView) findViewById(R.id.profile_weight);
        tvProfileHeight = (TextView) findViewById(R.id.profile_height);
        tvProfileEmail = (TextView) findViewById(R.id.profile_email);
        btnLogout = findViewById(R.id.btn_logout);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
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
                tvProfileFirstname.setText(user.getFirstName());
                tvProfileLastname.setText(user.getLastName());
                tvProfileDob.setText(user.getDob());
                if(user.getGender() == true){
                    tvProfileGender.setText("Male");
                }
                else {
                    tvProfileGender.setText("Female");
                }
                tvProfileWeight.setText(String.valueOf(user.getWeight()) + " kg");
                tvProfileHeight.setText(String.valueOf(user.getHeight()) + " cm");
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

    private void changePassword(){
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("msg", "click");
                ChangePasswordModal modal = new ChangePasswordModal();
                modal.show(getSupportFragmentManager(), "change_password_modal");
            }
        });
    }

    private void changeAvatar(){
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!permissionResultCheck()){
                    requestPermissionStorageImages();
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            File imageFile = new File(getPathFromUri(imageUri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
            //user
            String firstName = String.valueOf(tvProfileFirstname.getText());
            String lastName = String.valueOf(tvProfileLastname.getText());
            String email = String.valueOf(tvProfileEmail.getText());
            UserAvatar userAvatar = new UserAvatar(lastName, firstName, email, LoginActivity.userId);
            Gson gson = new Gson();
            String userJson = gson.toJson(userAvatar);
            RequestBody userBody = RequestBody.create(MediaType.parse("application/json"), userJson);
            Log.i("user", userJson);
            //request
            UserApi userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
            Call<Void> call = userApi.uploadAvatar("Bearer "+ LoginActivity.token, userBody, imagePart);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200){
                        recreate();
                    }
                    else {
                        try {
                            Log.i("CODE ", response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Handle response
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    // Handle error
                }
            });
        }
    }

    private String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }



    private void editProfile(){
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditProfileModal modal = new EditProfileModal();
                modal.show(getSupportFragmentManager(), "edit_profile_modal");
            }
        });
    }

    public boolean permissionResultCheck(){
        return is_storage_image_permitted;
    }

    public void requestPermissionStorageImages(){
        if(ContextCompat.checkSelfPermission(ProfileActivity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Log.i("TAG", "GRANTED");
            is_storage_image_permitted = true;
            if(!permissionResultCheck()){
                requestPermissionStorageImages();
            }
        }
        else {
            request_permission_launcher_storage_images.launch(permissions[0]);
        }
    }

    private ActivityResultLauncher<String> request_permission_launcher_storage_images =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted->{
                        if(isGranted){
                            Log.i("TAG", "GRANTED");
                            is_storage_image_permitted = true;
                        }
                        else {
                            Log.i("TAG", "NOT GRANTED");
                            is_storage_image_permitted = false;
                            sendToSettingDialog();
                        }
                    });

    public void sendToSettingDialog(){
        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Alert for permission")
                .setMessage("Go to setting for permission")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent rintent = new Intent();
                        rintent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        rintent.setData(uri);
                        startActivity(rintent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }




}
