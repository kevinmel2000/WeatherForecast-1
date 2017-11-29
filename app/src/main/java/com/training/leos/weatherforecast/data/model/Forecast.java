package com.training.leos.weatherforecast.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 26/11/2017.
 */

public class Forecast {
    @SerializedName("latitude") private String latitude;
    @SerializedName("longitude") private String longitude;
    @SerializedName("timezone") private String Timezone;

    @SerializedName("currently") private Currently currently;
    @SerializedName("daily") private Daily daily;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return Timezone;
    }

    public void setTimezone(String timezone) {
        Timezone = timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }
}
