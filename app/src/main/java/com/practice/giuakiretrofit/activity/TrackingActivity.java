package com.practice.giuakiretrofit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.adapter.TrackingListAdapter;
import com.practice.giuakiretrofit.api.ReportApi;
import com.practice.giuakiretrofit.api.TrackingApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.FoodTrackingHistory;
import com.practice.giuakiretrofit.dto.ReportResponse;
import com.practice.giuakiretrofit.model.Account;
import com.practice.giuakiretrofit.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingActivity extends AppCompatActivity {

    User user = new User();
    Account account = new Account();

    private Double total ;
    List<FoodTrackingHistory> foodTrackingHistories;
    private RecyclerView rcvTrackingList;
    private ImageView icon;
    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DATE);

    private TextView textDate;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);



        // lấy ngày tháng năm hiện tại
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DATE);

        // Set ngày hiện tại lên TextView

        setInitial();
        setControl();
    }

    public void setControl(){
        String currentDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        textDate.setText(currentDate);
        getTrackingHistory("Bearer " + LoginActivity.token, LoginActivity.userId, dayOfMonth, month + 1, year, "day");
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackingActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                Intent intentHome = new Intent(TrackingActivity.this, HomeActivity.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentHome);
                                // Xử lý khi chọn menu_home
                                return true;
                            case R.id.food:
                                Intent intentFood = new Intent(TrackingActivity.this, CategoryActivity.class);
                                intentFood.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFood);
                                // Xử lý khi chọn menu_search
                                return true;
                            case R.id.add:
                                recreate();
                                // Xử lý khi chọn menu_notifications
                                return true;
                            case R.id.favorite:
                                Intent intentFavorite = new Intent(TrackingActivity.this, FavoriteActivity.class);
                                intentFavorite.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFavorite);
                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                // Xử lý khi chọn menu_profile
                                Intent intentProfile = new Intent(TrackingActivity.this, ProfileActivity.class);
                                intentProfile.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentProfile);
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void setInitial() {
        icon = findViewById(R.id.ic_add_tracking);
        textDate = findViewById(R.id.textDate);
        rcvTrackingList = findViewById(R.id.rcv_trackinglist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvTrackingList.setLayoutManager(linearLayoutManager);
    }

    public void getTrackingHistory(String token, int userId, int dayOfMonth, int month, int year, String reportType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
        String dateTime = ldt.format(formatter);
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);
        Call<ReportResponse> call = reportApi.getReport("Bearer " + LoginActivity.token, LoginActivity.userId, dateTime, reportType);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if(response.code() == 200){
                    ReportResponse reportResponse = response.body();
                    if (reportResponse != null) {
                        foodTrackingHistories = reportResponse.getConsumedHistory();
                        total = reportResponse.getTotal();
                        TextView textView = findViewById(R.id.text_progress_bar);
                        textView.setText(total + "/2100");
                        int percent = Double.valueOf((total/2100)*100).intValue();
                        progressBar = findViewById(R.id.progress_bar);
                        progressBar.setIndeterminate(false);
                        progressBar.setProgress(percent);
                        progressBar.invalidate();
                        for(FoodTrackingHistory tlh : foodTrackingHistories) {
                            TrackingListAdapter adapter = new TrackingListAdapter(foodTrackingHistories);
                            rcvTrackingList.setAdapter(adapter);
                        }
                    }
                    else {
                        progressBar = findViewById(R.id.progress_bar);
                        progressBar.setProgress(0);
                        progressBar.invalidate();
                        TextView textView = findViewById(R.id.text_progress_bar);
                        textView.setText("0/2100");
                        TrackingListAdapter adapter = new TrackingListAdapter(new ArrayList<>());
                        rcvTrackingList.setAdapter(adapter);
                    }
                }
                else {
                    try {
                        Log.i("ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Toast.makeText(TrackingActivity.this, "Get Tracking Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTracking(){
        if(TrackingListAdapter.mSelectedPosition == -1) {
            Toast.makeText(TrackingActivity.this, "Please select tracking item", Toast.LENGTH_SHORT).show();
        }
        else {
            TrackingApi trackingApi = RetrofitClient.getRetrofitInstance().create(TrackingApi.class);
            Call<Void> call = trackingApi.deleteTracking(TrackingListAdapter.trackingId, "Bearer " + LoginActivity.token);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("Ket qua xoa: " + response);
                    Toast.makeText(TrackingActivity.this, "Remove Tracking Successfully", Toast.LENGTH_SHORT).show();
                    foodTrackingHistories.remove(TrackingListAdapter.mSelectedPosition);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        getTrackingHistory("Bearer " + LoginActivity.token, LoginActivity.userId, dayOfMonth, month + 1, year, "day");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(TrackingActivity.this, "Remove Tracking Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this tracking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTracking();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}