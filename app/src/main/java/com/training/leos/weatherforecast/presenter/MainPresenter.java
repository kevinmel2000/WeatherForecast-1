package com.training.leos.weatherforecast.presenter;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.training.leos.weatherforecast.model.Forecast;
import org.json.JSONException;
import cz.msebera.android.httpclient.Header;

public class MainPresenter implements InterfaceMVP.MvpMainPresenter {
    private InterfaceMVP.MvpMainActivity mainView;

    public MainPresenter(InterfaceMVP.MvpMainActivity mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onInitialize() {
        new LoadDataForecast().execute();
    }

    @Override
    public void onRequestFailure(String msg) {
        mainView.showToast(msg);
    }

    public class LoadDataForecast extends AsyncTask<Void, Void, Void> {
        private Forecast forecast;
        String API_KEY = "eb22e617fa693ad59b19c4e92c0e2ef1";
        String latitude = "6.1751";
        String longitude = "106.8650";
        String forecastUrl = "https://api.darksky.net/forecast/" + API_KEY + "/" +
                latitude + "," + longitude;

        @Override
        protected Void doInBackground(Void... params) {
            SyncHttpClient client = new SyncHttpClient();
            client.get(forecastUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    setUseSynchronousMode(true);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    try {
                        forecast = new Forecast(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    onRequestFailure(error.getMessage());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mainView.showCurrentWeather(forecast.getCurrently());
            mainView.showDailyWeather(forecast.getDailies());
        }
    }
}
