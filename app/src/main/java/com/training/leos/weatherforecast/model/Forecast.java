package com.training.leos.weatherforecast.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Leo on 25/11/2017.
 */

public class Forecast {
    private Currently currently;
    private ArrayList<Daily> dailies;

    public Forecast(){}

    public Forecast(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        this.currently = getCurrentlyForecast(jsonObject);
        this.dailies = getDailyForecast(jsonObject);
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public ArrayList<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(ArrayList<Daily> dailies) {
        this.dailies = dailies;
    }

    private Currently getCurrentlyForecast(JSONObject jsonObject) throws JSONException {
        String timezone = jsonObject.getString("timezone");
        JSONObject currentlyObject = jsonObject.getJSONObject("currently");

        Currently currently = new Currently(currentlyObject);
        currently.setTimezone(timezone);
        return currently;
    }

    private ArrayList<Daily> getDailyForecast(JSONObject jsonObject) throws JSONException {
        String timezone = jsonObject.getString("timezone");

        JSONObject dailyObject = jsonObject.getJSONObject("daily");
        JSONArray dailyArray = dailyObject.getJSONArray("data");

        ArrayList<Daily> dailies = new ArrayList<>();

        for (int i = 0; i < dailyArray.length(); i++) {
            JSONObject dailyTemp = dailyArray.getJSONObject(i);
            Daily daily = new Daily(dailyTemp);
            daily.setTimezone(timezone);

            dailies.add(daily);
        }
        return dailies;
    }
}
