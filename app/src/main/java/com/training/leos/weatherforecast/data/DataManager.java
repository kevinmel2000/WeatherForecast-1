package com.training.leos.weatherforecast.data;

import com.training.leos.weatherforecast.data.model.Forecast;
import com.training.leos.weatherforecast.data.sharedPreference.ReminderPreference;

public class DataManager implements DataContract.ClientAPI, DataContract.SharedPreference{
    private DaoContract daoContract;

    // client API method
    public DataManager(DaoContract daoContract){
        this.daoContract = daoContract;
    }

    @Override
    public Forecast getForecast(String location) {
        return daoContract.fetchForecast(location);
    }


    // sharedPreferenced method
    private ReminderPreference reminderPreference;

    public DataManager(ReminderPreference reminderPreference){
        this.reminderPreference = reminderPreference;
    }

    @Override
    public void setReminderTime(String time) {
        reminderPreference.setTime(time);
    }

    @Override
    public String getReminderTime() {
        return reminderPreference.getTime();
    }

    @Override
    public void clearReminderTime() {
        reminderPreference.clear();
    }
}
