package com.training.leos.weatherforecast.ui;


import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.presenter.SettingContract;
import com.training.leos.weatherforecast.presenter.SettingPresenter;


public class SettingsActivity extends AppCompatPreferenceActivity {
    public static final String TAG = SettingsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GeneralPreferenceFragment());
        fragmentTransaction.commit();

    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), "List View"));
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        private SwitchPreference switchPreferenceDailyReminder, switchPreferenceAutoUpdate;
        private SettingContract.SettingPresenter settingPresenter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            settingPresenter = new SettingPresenter(getActivity());

            switchPreferenceDailyReminder = (SwitchPreference) findPreference("daily_reminder");
            switchPreferenceDailyReminder.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SwitchPreference switchPreference = (SwitchPreference) preference;
                    if (switchPreference.isChecked()){
                        settingPresenter.cancelDailyReminder();
                    }else {
                        String time = "06:00";
                        settingPresenter.onSetDailyReminder(time);
                    }
                    return true;
                }
            });
        }
    }
}
