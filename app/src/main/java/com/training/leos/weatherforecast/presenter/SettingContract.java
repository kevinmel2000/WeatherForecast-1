package com.training.leos.weatherforecast.presenter;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by Leo on 30/11/2017.
 */

public interface SettingContract {
    public interface SettingActivity{

    }
    public interface SettingPresenter{
        public void onSetDailyReminder(String time);
        public void cancelDailyReminder();
    }
}
