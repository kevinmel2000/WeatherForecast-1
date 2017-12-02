package com.training.leos.weatherforecast.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.training.leos.weatherforecast.presenter.MainPresenter;
import com.training.leos.weatherforecast.ui.adapter.ListViewForecastAdapater;
import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.data.model.Currently;
import com.training.leos.weatherforecast.data.model.Daily;
import com.training.leos.weatherforecast.presenter.MainContract;
import com.training.leos.weatherforecast.util.ItemClickSupport;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_weather) RecyclerView rv_weather;
    @BindView(R.id.tv_main_temperature) TextView tvTemperature;
    @BindView(R.id.tv_main_summary) TextView tvSummary;
    @BindView(R.id.iv_main_icon) ImageView imgIcon;
    @BindView(R.id.main_act_toolbar) Toolbar toolbar;

    private MainContract.MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        NetworkInfo networkInfo = ((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null) {
            mainPresenter = new MainPresenter(this);
            mainPresenter.onInitialize();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCurrentWeather(Currently currently) {
        toolbar.setTitle(currently.getTimezone());
        toolbar.setSubtitle(currently.getFormatedDate());
        setSupportActionBar(toolbar);

        tvTemperature.setText(currently.getTemperature() + "");
        tvSummary.setText(currently.getSummary());
        imgIcon.setImageResource(currently.getIconId());
    }

    @Override
    public void showDailyWeather(final Daily daily) {
        ListViewForecastAdapater listViewForecastAdapater = new ListViewForecastAdapater(this);
        listViewForecastAdapater.setDailyData(daily.getDailies());
        rv_weather.setAdapter(listViewForecastAdapater);

        ItemClickSupport.addTo(rv_weather).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                DetailActivity.startActivity(MainActivity.this, daily.getDailies().get(position));
            }
        });
    }
}
