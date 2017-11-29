package com.training.leos.weatherforecast.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.data.model.Data;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DAILY = "extra_daily";

    @BindView(R.id.tv_detail_location) TextView tvLocation;
    @BindView(R.id.tv_detail_current_date) TextView tvCurrentDate;
    @BindView(R.id.tv_detail_temperature) TextView tvTemperature;
    @BindView(R.id.tv_detail_humidity_value) TextView tvHumidityValue;
    @BindView(R.id.tv_detail_precip_value) TextView tvPrecipValue;
    @BindView(R.id.tv_detail_summary) TextView tvSummary;
    @BindView(R.id.iv_detail_icon) ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Data data = getIntent().getParcelableExtra(EXTRA_DAILY);
        tvLocation.setText(data.getTimezone());
        tvCurrentDate.setText(data.getFormatedDate());
        imgIcon.setImageDrawable(ContextCompat.getDrawable(this, data.getIconId()));
        tvTemperature.setText(data.getTemperature() + "");
        tvHumidityValue.setText(data.getHumidity() + "");
        tvPrecipValue.setText(data.getPrecipProbability() + "%");
        tvSummary.setText(data.getSummary());
    }

    public static void startActivity(Context context, Data data){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_DAILY, data);
        context.startActivity(intent);
    }

}
