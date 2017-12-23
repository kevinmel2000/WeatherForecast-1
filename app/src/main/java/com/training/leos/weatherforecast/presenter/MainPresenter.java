package com.training.leos.weatherforecast.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.training.leos.weatherforecast.MainContract;
import com.training.leos.weatherforecast.data.DataContract;
import com.training.leos.weatherforecast.data.DataManager;
import com.training.leos.weatherforecast.data.model.Forecast;

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";
    private MainContract.View view;
    private DataContract.Manager dataManager;

    public MainPresenter(MainContract.View mainView) {
        this.view = mainView;
        dataManager = new DataManager();
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
        view.showCurrentWeather(forecast.getCurrently());
    }

    @Override
    public void onShowDaily(Forecast forecast) {
        int n = forecast.getDaily().getDailies().size();
        String timezone = forecast.getTimezone();
        for (int i = 0; i < n; i++) {
            forecast.getDaily().getDailies().get(i).setTimezone(timezone);
        }
        view.showDailyWeather(forecast.getDaily());
    }

    @Override
    public void onRequestFailure(String msg) {
        view.showToast(msg);
    }

    @Override
    public void onCheckRecomendation(String query) {
        view.showRecomendationResult(dataManager.getRecomendation(query));
    }

    public class LoadForecast extends AsyncTask<String, Void, Forecast>{
        @Override
        protected Forecast doInBackground(String... params) {
            return dataManager.getForecast(String.valueOf(params));
        }
        @Override
        protected void onPostExecute(Forecast forecast) {
            Log.w(TAG, "onPostExecute: " + forecast.getDaily().getDailies().size());
            if (forecast != null){
                onShowCurrently(forecast);
                onShowDaily(forecast);
            }else {
                onRequestFailure("No data fetched");
            }
        }
    }
}