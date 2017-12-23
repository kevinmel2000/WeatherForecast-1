package com.training.leos.weatherforecast.data;

import com.training.leos.weatherforecast.data.fakeAPI.FakeForecastAPI;
import com.training.leos.weatherforecast.data.fakeAPI.FakeLocationAPI;
import com.training.leos.weatherforecast.data.model.Forecast;

import java.util.ArrayList;

public class DataManager implements DataContract.Manager {
    private DaoContract daoContract;
    private FakeLocationAPI fakeLocationAPI;

    // client API method
    public DataManager(){
        this.daoContract = new FakeForecastAPI();
        this.fakeLocationAPI = new FakeLocationAPI();
    }

    @Override
    public Forecast getForecast(String location) {
        return daoContract.fetchForecast(location);
    }

    @Override
    public ArrayList<String> getRecomendation(String query){
        return fakeLocationAPI.fetchResult(query);
    }
}
