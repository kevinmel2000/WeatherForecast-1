package com.training.leos.weatherforecast.data;

import com.training.leos.weatherforecast.data.model.Forecast;

/**
 * Created by Leo on 29/11/2017.
 */

public interface DataContract {
    interface ClientAPI{
        public Forecast getForecast(String location);
    }
    interface LocalDatabase{
        public Forecast getForecast(String location);
    }
    interface SharedPreference{
        public void setReminderTime(String time);
        public String getReminderTime();
        public void clearReminderTime();
    }
}
