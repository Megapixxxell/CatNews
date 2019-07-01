package com.example.data.api;

import com.example.domain.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("everything")
    Single<NewsResponse> getNews (
            @Query ("q") String keyword,
            @Query("apiKey") String apiKey,
            @Query("from") String date,
            @Query("SortBy") String sortBy,
            @Query("pageSize") int size,
            @Query("page") int page);
}
