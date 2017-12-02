package com.training.leos.weatherforecast.data.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {
    private final String PREF_NAME = "alarmPef";
    private final String KEY_DATE = "date";
    private final String KEY_TIME = "time";
    private final String KEY_MESSAGE= "message";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ReminderPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDate(String date){
        editor.putString(KEY_DATE, date);
        editor.commit();
    }
    public String getDate(){
        return sharedPreferences.getString(KEY_DATE, null);
    }

    public void setTime(String date){
        editor.putString(KEY_TIME, date);
        editor.commit();
    }
    public String getTime(){
        return sharedPreferences.getString(KEY_TIME, null);
    }
    public void setMessage(String date){
        editor.putString(KEY_MESSAGE, date);
        editor.commit();
    }
    public String getMessage(){
        return sharedPreferences.getString(KEY_MESSAGE, null);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

}
