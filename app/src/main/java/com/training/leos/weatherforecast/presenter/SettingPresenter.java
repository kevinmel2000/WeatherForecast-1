package com.training.leos.weatherforecast.presenter;

import android.content.Context;

import com.training.leos.weatherforecast.data.DataContract;
import com.training.leos.weatherforecast.data.DataManager;
import com.training.leos.weatherforecast.util.broadcast.ReminderReceiver;
import com.training.leos.weatherforecast.data.sharedPreference.ReminderPreference;

public class SettingPresenter implements SettingContract.SettingPresenter{
    //this actually unnecessary..yep
    private DataContract.SharedPreference preferenceManager;

    private ReminderReceiver reminderReceiver;
    private Context context;

    public SettingPresenter(Context context){
        this.context = context;
        if (preferenceManager == null){
            preferenceManager = new DataManager(new ReminderPreference(context));
        }
        if (reminderReceiver == null){
            reminderReceiver = new ReminderReceiver();
        }
    }

    @Override
    public void onSetDailyReminder(String time) {
        preferenceManager.setReminderTime(time);
        reminderReceiver.setReminderAlarm(context, time);
    }

    @Override
    public void cancelDailyReminder() {
        if (preferenceManager != null){
            preferenceManager.clearReminderTime();
            reminderReceiver.cancelReminderAlarm(context);
        }
    }
}
