package com.training.leos.weatherforecast.presenter;

import com.training.leos.weatherforecast.data.DataManager;
import com.training.leos.weatherforecast.data.model.Forecast;

public class MainPresenter implements MainContract.MvpMainPresenter {
    private MainContract.MvpMainActivity mainView;
    private DataManager dataManager;

    public MainPresenter(MainContract.MvpMainActivity mainView) {
        this.mainView = mainView;
        dataManager = new DataManager();
    }

    @Override
    public void onInitialize() {
        String location = "6.1751,106.8650";
        Forecast forecast = dataManager.getForecast(location);
        onShowCurrently(forecast);
        onShowDaily(forecast);
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
}