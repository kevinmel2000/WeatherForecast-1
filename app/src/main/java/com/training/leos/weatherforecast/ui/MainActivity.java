package com.training.leos.weatherforecast.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.training.leos.weatherforecast.adapter.ListViewForecastAdapater;
import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.model.Currently;
import com.training.leos.weatherforecast.model.Daily;
import com.training.leos.weatherforecast.presenter.InterfaceMVP;
import com.training.leos.weatherforecast.presenter.MainPresenter;
import com.training.leos.weatherforecast.util.ItemClickSupport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements InterfaceMVP.MvpMainActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_weather) RecyclerView rv_weather;
    @BindView(R.id.tv_main_location) TextView tvLocation;
    @BindView(R.id.tv_main_current_date) TextView tvDate;
    @BindView(R.id.tv_main_temperature) TextView tvTemperature;
    @BindView(R.id.tv_main_summary) TextView tvSummary;
    @BindView(R.id.iv_main_icon) ImageView imgIcon;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this);
        mainPresenter.onInitialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCurrentWeather(Currently currently) {
        tvLocation.setText(currently.getTimezone());
        tvDate.setText(currently.getFormatedDate());
        tvTemperature.setText(currently.getTemperature() + "");
        tvSummary.setText(currently.getSummary());
        imgIcon.setImageResource(currently.getIconId());
    }

    @Override
    public void showDailyWeather(final ArrayList<Daily> dailies) {
        ListViewForecastAdapater listViewForecastAdapater = new ListViewForecastAdapater(this);
        listViewForecastAdapater.setDailyData(dailies);
        rv_weather.setAdapter(listViewForecastAdapater);

        ItemClickSupport.addTo(rv_weather).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DAILY, dailies.get(position));
                startActivity(intent);
            }
        });
    }

    // ??????
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isActive = false;
        if (networkInfo != null) {
            isActive = true;
        }
        return isActive;
    }
}
