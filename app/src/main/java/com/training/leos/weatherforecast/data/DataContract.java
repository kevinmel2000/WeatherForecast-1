package com.training.leos.weatherforecast.data;

import com.training.leos.weatherforecast.data.model.Forecast;

import java.util.ArrayList;

/**
 * Created by Leo on 29/11/2017.
 */

public interface DataContract {
    interface Manager {
        public Forecast getForecast(String location);

        ArrayList<String> getRecomendation(String query);
    }
}
