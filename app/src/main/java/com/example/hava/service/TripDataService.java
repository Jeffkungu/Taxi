package com.example.hava.service;

import com.example.hava.model.TripsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TripDataService {
    @GET("recent.json")
    Call<TripsResponse> getTrips();
}
