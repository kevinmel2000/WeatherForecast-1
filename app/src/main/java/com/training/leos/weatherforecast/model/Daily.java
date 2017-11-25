package com.training.leos.weatherforecast.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.training.leos.weatherforecast.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Leo on 29/10/2017.
 */

public class Daily implements Parcelable {
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipProbability;
    private String summary;
    private String timezone;

    public Daily() {
    }

    public Daily(JSONObject jsonObject) throws JSONException {
        this.icon = jsonObject.getString("icon");
        this.time = jsonObject.getLong("time");
        this.temperature = jsonObject.getDouble("temperatureLow");
        this.humidity = jsonObject.getDouble("humidity");
        this.precipProbability = jsonObject.getDouble("precipProbability");
        this.summary = jsonObject.getString("summary");
    }

    public String getIcon() {
        return icon;
    }
    public int getIconId(){
        int iconId = R.drawable.clear_day;
        switch (icon) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
            default:
                break;
        }
        return iconId;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }
    public String getFormatedDate(){
        Date date = new Date(getTime()*1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        String formatedDate = dateFormat.format(date);
        return formatedDate;
    }
    public String getFormatedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        String formatedTime = dateFormat.format(new Date(getTime() * 1000));
        return formatedTime;
    }
    public String getFormatedDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        String formatedDay = dateFormat.format(new Date(getTime() * 1000));
        return formatedDay;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTemperature() {
        return (int) Math.round(temperature);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getPrecipProbability() {
        return (int) Math.round(precipProbability * 100);
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeLong(this.time);
        dest.writeDouble(this.temperature);
        dest.writeDouble(this.humidity);
        dest.writeDouble(this.precipProbability);
        dest.writeString(this.summary);
        dest.writeString(this.timezone);
    }

    protected Daily(Parcel in) {
        this.icon = in.readString();
        this.time = in.readLong();
        this.temperature = in.readDouble();
        this.humidity = in.readDouble();
        this.precipProbability = in.readDouble();
        this.summary = in.readString();
        this.timezone = in.readString();
    }

    public static final Parcelable.Creator<Daily> CREATOR = new Parcelable.Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
