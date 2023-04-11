package com.practice.giuakiretrofit.api;

import com.practice.giuakiretrofit.dto.FoodTrackingHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrackingApi {
    @GET("/api/v1/tracking/user/{userId}")
    Call<List<FoodTrackingHistory>> getTrackingList(
            @Path("userId") int userId,
            @Header("Authorization") String token);

    @POST("/api/v1/tracking/add/{userId}/{foodId}/{consumedGram}")
    Call<Void> addTracking(
            @Path("userId")  int userId,
            @Path("foodId") int foodId,
            @Path("consumedGram") Double consumedGram,
            @Header("Authorization") String token);

    @DELETE("/api/v1/tracking/remove/{trackingId}")
    Call<Void> deleteTracking(
            @Path("trackingId") int trackingId,
            @Header("Authorization") String token);
}
