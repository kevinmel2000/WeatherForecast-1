package com.training.leos.weatherforecast.data;

import com.training.leos.weatherforecast.data.model.Forecast;
import com.training.leos.weatherforecast.data.retrofitAPI.RetrofitAPI;


public class DataManager {
    private DaoContract daoContract;

    public DataManager(){
        daoContract = new RetrofitAPI();
    }

    public Forecast getForecast(String location){
        return daoContract.fetchForecast(location);
    }
}
