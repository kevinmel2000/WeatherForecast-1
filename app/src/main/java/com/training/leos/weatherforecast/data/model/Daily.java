package com.training.leos.weatherforecast.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily{
    @SerializedName("summary") private String summary;
    @SerializedName("icon") private String icon;
    @SerializedName("data") private ArrayList<Data> dailies;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<Data> getDailies() {
        return dailies;
    }

    public void setDailies(ArrayList<Data> dailies) {
        this.dailies = dailies;
    }
}
