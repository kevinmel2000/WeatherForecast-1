package com.training.leos.weatherforecast.presenter;

import android.os.AsyncTask;

import com.training.leos.weatherforecast.data.DataContract;
import com.training.leos.weatherforecast.data.DataManager;
import com.training.leos.weatherforecast.data.model.Forecast;
import com.training.leos.weatherforecast.data.retrofitAPI.RetrofitAPI;

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.MainActivity mainView;
    private DataContract.ClientAPI clientManager;

    public MainPresenter(MainContract.MainActivity mainView) {
        this.mainView = mainView;
        clientManager = new DataManager(new RetrofitAPI());
    }

    @Override
    public void onInitialize() {
        String location = "6.1751,106.8650";
        new LoadForecast().execute(location);

    }

    @Override
    public void onShowCurrently(Forecast forecast) {
        String timezone = forecast.getTimezone();
        forecast.getCurrently().setTimezone(timezone);
        mainView.showCurrentWeather(forecast.getCurrently());
    }

    @Override
    public void onShowDaily(Forecast forecast) {
        int n = forecast.getDaily().getDailies().size();
        String timezone = forecast.getTimezone();
        for (int i = 0; i < n; i++) {
            forecast.getDaily().getDailies().get(i).setTimezone(timezone);
        }
        mainView.showDailyWeather(forecast.getDaily());
    }

    @Override
    public void onRequestFailure(String msg) {
        mainView.showToast(msg);
    }

    public class LoadForecast extends AsyncTask<String, Void, Forecast>{
        @Override
        protected Forecast doInBackground(String... params) {
            return clientManager.getForecast(String.valueOf(params));
        }
        @Override
        protected void onPostExecute(Forecast forecast) {
            if (forecast != null){
                onShowCurrently(forecast);
                onShowDaily(forecast);
            }else {
                onRequestFailure("No data fetched");
            }
        }
    }
}