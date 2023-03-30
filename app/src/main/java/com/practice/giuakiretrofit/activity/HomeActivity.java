package com.practice.giuakiretrofit.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.giuakiretrofit.R;
import com.practice.giuakiretrofit.adapter.CategoryAdapter;
import com.practice.giuakiretrofit.api.CategoryApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.model.Category;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.giuakiretrofit.api.ReportApi;
import com.practice.giuakiretrofit.client.RetrofitClient;
import com.practice.giuakiretrofit.dto.FoodTrackingHistory;
import com.practice.giuakiretrofit.dto.ReportResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rcvHomeFood;
    private List<Category> mListCategory;

    private ProgressBar progressBar;
    private TextView progressText;

    private Intent intent;
    private BarChart barChart;
    private TextView txtDateTime, txtMaxVal, txtMinVal, txtAverageVal, txtTotalVal, txtViewHistory;
    private Calendar calendar;
    private Spinner reportTypeSpn;
    private List<String> reportTypes;
    private int dayOfMonth, month, year;
    private List<FoodTrackingHistory> trackingHistory; // Lưu lại consumed history list từ response trả về mỗi lần call api tạo chart
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setInitial();
        setControl();

    }

    private void setInitial() {
        // initial view components
        rcvHomeFood = findViewById(R.id.rcv_home_food);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        rcvHomeFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        progressText.setText("69" + "%");
        progressBar.setProgress(69);

        barChart = findViewById(R.id.barChart);
        txtDateTime = findViewById(R.id.txtDateTime);
        reportTypeSpn = findViewById(R.id.reportTypeSpn);
        txtMaxVal = findViewById(R.id.txtMaxVal);
        txtMinVal = findViewById(R.id.txtMinVal);
        txtAverageVal = findViewById(R.id.txtAverageVal);
        txtTotalVal = findViewById(R.id.txtTotalVal);

        txtViewHistory = findViewById(R.id.btnViewHistory);

        intent = getIntent(); // Lấy thông tin kẹp trong LoginActivity sau khi chuyển hướng
        calendar = Calendar.getInstance(); // Khởi tạo đối tượng Calendar

        // Nạp data cho Report type dropdown
        prepareOptionsForSpinner();

        //Bottom bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                // Xử lý khi chọn menu_home
                                return true;
                            case R.id.food:
                                Intent intentFood = new Intent(HomeActivity.this, CategoryActivity.class);
                                intentFood.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFood);
                                // Xử lý khi chọn menu_search
                                return true;
                            case R.id.add:
                                // Xử lý khi chọn menu_notifications
                                return true;
                            case R.id.favorite:
                                Intent intentFavorite = new Intent(HomeActivity.this, FavoriteActivity.class);
                                intentFavorite.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentFavorite);
                                // Xử lý khi chọn menu_profile
                                return true;
                            case R.id.profile:
                                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                                intentProfile.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentProfile);

                                // Xử lý khi chọn menu_profile
                                return true;
                        }
                        return false;
                    }
                });
        callApiGetCategories(LoginActivity.token);
    }

    private void setControl() {
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        // set current date for textView date
        txtDateTime.setText(String.format("%d/%d/%d", dayOfMonth, month, year));

        // set item on click for spinner
        reportTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedReportType = reportTypes.get(position);
                // Sẽ gọi 1 lần đầu tiên khi chạy app
                callReportApi(LoginActivity.userId, dayOfMonth, month, year, selectedReportType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Choose datetime
        txtDateTime.setOnClickListener(v -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    txtDateTime.setText(String.format("%d/%d/%d", d, m + 1, y));
                    dayOfMonth = d;
                    month = m + 1;
                    year = y;
                    callReportApi(
                            1,
                            dayOfMonth,
                            month,
                            year,
                            reportTypeSpn.getSelectedItem().toString()
                    );
                }
            }, year, month, dayOfMonth);

            datePickerDialog.show();
        });

        // View consumed food history
        txtViewHistory.setOnClickListener(view -> {
            if(trackingHistory == null || trackingHistory.isEmpty()) {
                Toast.makeText(this, "No history data", Toast.LENGTH_SHORT).show();
                return;
            }
            HistoryBottomSheet historyBottomSheet = new HistoryBottomSheet(trackingHistory);
            historyBottomSheet.show(getSupportFragmentManager(), historyBottomSheet.getTag());
        });
    }

    private void prepareOptionsForSpinner() {
        reportTypes = new ArrayList<>();
        reportTypes.add("DAY");
        reportTypes.add("WEEK");
        reportTypes.add("MONTH");
        reportTypes.add("YEAR");

        ArrayAdapter<String> reportTypesAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_report_type_item,
                reportTypes);
        reportTypeSpn.setAdapter(reportTypesAdapter);
    }
    private void callReportApi(int userId, int dayOfMonth, int month, int year, String reportType) {
        List<FoodTrackingHistory> trackingHistoryOfResponse;
        ReportApi reportApi = RetrofitClient.getRetrofitInstance().create(ReportApi.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
        String dateTime = ldt.format(formatter);
        Log.i("call report api (dateTime & reportType)", dateTime + " " + reportType);
        String token = LoginActivity.token;

        Call<ReportResponse> call = reportApi.getReport("Bearer ".concat(token), userId, dateTime, reportType);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                ReportResponse reportResponse = response.body();

                // Lưu lại consumed history để view nếu cần
                if (reportResponse != null)
                    trackingHistory = reportResponse.getConsumedHistory();
                else trackingHistory = null;
                // reportResponse cái này có thể null nên phải check trước khi xử lý gì đó
                createChart(reportResponse);
                createSummary(reportResponse);
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void createChart(ReportResponse reportResponse) {
        List<String> labels = new ArrayList<>();
        List<BarEntry> entries = new ArrayList<>();

        if (reportResponse != null) {
            Map<String, Float> columnData = reportResponse.getColumnData();
            int i = 0;
            for (String label : columnData.keySet()) {
                labels.add(label);
                BarEntry barEntry = new BarEntry(i++, columnData.get(label));
                entries.add(barEntry);
            }
            BarDataSet barDataSet = new BarDataSet(entries, "Consumed Calories");
            barDataSet.setGradientColor(Color.BLUE, Color.MAGENTA);
            //barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(14);

            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.75f);
            barChart.animateY(500);
            barChart.setFitBars(true);
            barChart.getDescription().setEnabled(false);
            barChart.setData(barData);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setDrawAxisLine(false); // không vẽ trục x
            xAxis.setDrawGridLines(false); // không vẽ grid
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            xAxis.setTextSize(9f); // text size cho labels
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setLabelRotationAngle(-20);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels.toArray(new String[0]))); // thêm labels cho trục x
            barChart.getAxisLeft().setDrawAxisLine(false); // k vẽ trục y left
            barChart.getAxisRight().setDrawAxisLine(false); // k vẽ trục y right
//            barChart.offsetTopAndBottom(40);
//            barChart.zoom(1.85f, 1.0f, 2, 2);
            barChart.invalidate(); // refresh
        }
        else {
            barChart.clear();
            barChart.setNoDataText("No data");
        }
    }
    private void createSummary(ReportResponse reportResponse) {
        String maxCal, minCal, averageCal, totalCal;
        if(reportResponse != null) {
            maxCal = (reportResponse.getMax() != null) ? String.format("%.1f", reportResponse.getMax()) : "No data";
            minCal = (reportResponse.getMin() != null) ? String.format("%.1f", reportResponse.getMin()) : "No data";
            averageCal = (reportResponse.getAverage() != null) ? String.format("%.1f", reportResponse.getAverage()) : "No data";
            totalCal = (reportResponse.getTotal() != null) ? String.format("%.1f", reportResponse.getTotal()) : "No data";
            txtMaxVal.setText(maxCal);
            txtMinVal.setText(minCal);
            txtAverageVal.setText(averageCal);
            txtTotalVal.setText(totalCal);
        } else {
            txtMaxVal.setText("No data");
            txtMinVal.setText("No data");
            txtAverageVal.setText("No data");
            txtTotalVal.setText("No data");
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    private void callApiGetCategories(String token){
        CategoryApi categoryApi = RetrofitClient.getRetrofitInstance().create(CategoryApi.class);
        Call<List<Category>> call = categoryApi.getAllCategory("Bearer " + token);
        call.enqueue(new Callback<List<Category>>() {

            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mListCategory = response.body();
                for (Category category : mListCategory) {
                    Log.i(TAG, category.getName() + "");
                }
                CategoryAdapter adapter = new CategoryAdapter(mListCategory);
                rcvHomeFood.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}