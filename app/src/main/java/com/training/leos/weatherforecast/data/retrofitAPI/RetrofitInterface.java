package com.training.leos.weatherforecast.data.retrofitAPI;

import com.training.leos.weatherforecast.data.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("forecast/eb22e617fa693ad59b19c4e92c0e2ef1/{location}")
    Call<Forecast> getForecast(@Path("location") String location);
}
