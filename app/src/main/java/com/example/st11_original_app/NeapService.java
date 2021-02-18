package com.example.st11_original_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NeapService {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

}
