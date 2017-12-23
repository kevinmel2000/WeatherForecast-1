package com.training.leos.weatherforecast.data.retrofitAPI;

import android.util.Log;

import com.training.leos.weatherforecast.data.DaoContract;
import com.training.leos.weatherforecast.data.model.Forecast;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI implements DaoContract {
    private static final String TAG = "RetrofitAPI";
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
        try {
            //syncronous
            Response<Forecast> weatherForecast = retrofitInterface.getForecast(location).execute();

            Log.w(TAG, "fetchForecast: " + weatherForecast.message());
            return weatherForecast.body();
        } catch (IOException e) {
            return null;
        }
    }
}
