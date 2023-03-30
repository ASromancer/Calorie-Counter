package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.dto.ReportResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ReportApi {
    @GET("tracking/report")
    Call<ReportResponse> getReport(
            @Header("Authorization") String token,
            @Query("userId") Integer userId,
            @Query("dateTime") String dateTime,
            @Query("reportType") String reportType);
}
