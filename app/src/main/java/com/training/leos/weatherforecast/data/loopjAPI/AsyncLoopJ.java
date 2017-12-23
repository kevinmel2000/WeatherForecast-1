package com.training.leos.weatherforecast.data.loopjAPI;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.training.leos.weatherforecast.data.DaoContract;
import com.training.leos.weatherforecast.data.model.Currently;
import com.training.leos.weatherforecast.data.model.Daily;
import com.training.leos.weatherforecast.data.model.Data;
import com.training.leos.weatherforecast.data.model.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncLoopJ implements DaoContract{
    private static final String TAG = "AsyncLoopJ";
    private SyncHttpClient client;

    public AsyncLoopJ(){
        this.client = new SyncHttpClient();
    }

    @Override
    public Forecast fetchForecast(String location) {
        final Forecast forecast = new Forecast();
        String url = "https://api.darksky.net/" + "forecast/eb22e617fa693ad59b19c4e92c0e2ef1/" + location;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.w(TAG, "onSuccess: " + responseBody.toString());
                String responseString = responseBody.toString();
                try {
                    forecast.setTimezone(getCurrently(responseString).getTimezone());
                    forecast.setCurrently(getCurrently(responseString));
                    forecast.setDaily(getDaily(responseString));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.w(TAG, "onFailure: " + error.getMessage());
            }
        });
        return forecast;
    }
    private Currently getCurrently(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);

        Currently currently = new Currently();
        currently.setTimezone(jsonObject.getString("timezone"));
        currently.setTime(jsonObject.getJSONObject("currently").getLong("time"));
        currently.setHumidity(jsonObject.getJSONObject("currently").getDouble("humidity"));
        currently.setIcon(jsonObject.getJSONObject("currently").getString("icon"));
        currently.setTemperature(jsonObject.getJSONObject("currently").getDouble("temperature"));
        currently.setSummary(jsonObject.getJSONObject("currently").getString("summary"));
        currently.setPrecipProbability(jsonObject.getJSONObject("currently").getDouble("precipProbability"));

        return currently;
    }

    private Daily getDaily(String response) throws JSONException {
        ArrayList<Data> dailies = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONObject("daily").getJSONArray("data");
        Log.w(TAG, "getDaily: " + jsonArray.length() );

        Data data;
        for (int i = 0; i < jsonArray.length(); i++){
             data = new Data();
            JSONObject object = jsonArray.getJSONObject(i);
            Log.w(TAG, "getDaily: " + object.toString() );
            data.setTime(object.getLong("time"));
            data.setHumidity(object.getDouble("humidity"));
            data.setIcon(object.getString("icon"));
            data.setTemperature(object.getDouble("temperatureHigh"));
            data.setSummary(object.getString("summary"));
            data.setPrecipProbability(object.getDouble("precipProbability"));

            dailies.add(data);
        }
        Daily daily = new Daily();
        daily.setDailies(dailies);
        return daily;
    }
}
