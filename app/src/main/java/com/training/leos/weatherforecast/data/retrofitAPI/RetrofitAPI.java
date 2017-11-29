package com.training.leos.weatherforecast.data.retrofitAPI;

import com.training.leos.weatherforecast.data.DaoContract;
import com.training.leos.weatherforecast.data.model.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI implements DaoContract {
    private static String baseUrl = "https://api.darksky.net/";
    private RetrofitInterface retrofitInterface;


    public RetrofitAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    @Override
    public Forecast fetchForecast(String location) {
        final Forecast[] forecast = {new Forecast()};
        Call<Forecast> weatherForecast = retrofitInterface.getForecast(location);
        weatherForecast.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                forecast[0] = response.body();
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
            }
        });
        return forecast[0];
    }
}
