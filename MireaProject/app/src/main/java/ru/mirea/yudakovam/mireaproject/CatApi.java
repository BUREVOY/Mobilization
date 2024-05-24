package ru.mirea.yudakovam.mireaproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatApi {
    @GET("v1/images/search?limit=1&category_ids=5")
    Call<List<Cat>> getCat();
}
