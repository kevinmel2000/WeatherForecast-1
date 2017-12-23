package com.training.leos.weatherforecast.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.training.leos.weatherforecast.presenter.MainPresenter;
import com.training.leos.weatherforecast.ui.adapter.ForecastAdapter;
import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.data.model.Currently;
import com.training.leos.weatherforecast.data.model.Daily;
import com.training.leos.weatherforecast.MainContract;
import com.training.leos.weatherforecast.ui.adapter.SuggestionsAdapter;
import com.training.leos.weatherforecast.util.ItemClickSupport;
import com.training.leos.weatherforecast.util.RxObseravableSearchView;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_weather) RecyclerView rvWeather;
    @BindView(R.id.rv_Suggestions) RecyclerView rvSuggestion;
    @BindView(R.id.fl_suggestion) FrameLayout flSuggestion;
    @BindView(R.id.rl_suggestion_divider) RelativeLayout rlSuggestDivider;

    @BindView(R.id.tv_main_temperature) TextView tvTemperature;
    @BindView(R.id.tv_main_summary) TextView tvSummary;
    @BindView(R.id.iv_main_icon) ImageView imgIcon;
    @BindView(R.id.main_act_toolbar) Toolbar toolbar;
    @BindView(R.id.material_searchview) MaterialSearchView searchView;

    private MainContract.Presenter presenter;
    private SuggestionsAdapter suggestionsAdapter;
    private ForecastAdapter forecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        forecastAdapter = new ForecastAdapter(this);
        suggestionsAdapter = new SuggestionsAdapter(this);

        rvWeather.setAdapter(forecastAdapter);
        rvSuggestion.setAdapter(suggestionsAdapter);

        NetworkInfo networkInfo = ((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null) {
            presenter = new MainPresenter(this);
            presenter.onInitialize();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchItem);
        searchView.setTextColor(ContextCompat.getColor(this, R.color.colorWhiteText));
        searchView.setVoiceSearch(true);
        searchView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        searchView.clearAnimation();

        searchView.findViewById(R.id.action_empty_btn).setOnClickListener(this);
        searchView.findViewById(R.id.action_up_btn).setOnClickListener(this);
        rlSuggestDivider.setOnClickListener(this);


        RxObseravableSearchView.fromView(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !s.isEmpty();
                    }
                })
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        Log.w(TAG, "onSubscribe: " + disposable.toString());
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        presenter.onCheckRecomendation(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.w(TAG, "onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.w(TAG, "onComplete: ");
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_empty_btn :
                ((EditText) searchView.findViewById(R.id.searchTextView)).setText("");
                flSuggestion.setVisibility(View.GONE);
                break;
            case R.id.action_up_btn :
                if (searchView.isSearchOpen()){
                    searchView.closeSearch();
                }
                flSuggestion.setVisibility(View.GONE);
                break;
            case R.id.rl_suggestion_divider :
                flSuggestion.setVisibility(View.GONE);
                searchView.closeSearch();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            if (flSuggestion.getVisibility() == View.VISIBLE){
                flSuggestion.setVisibility(View.GONE);
            }
        } else {
            super.onBackPressed();
        }
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
        getSupportActionBar().setTitle(currently.getTimezone());
        getSupportActionBar().setSubtitle(currently.getFormatedDate());

        tvTemperature.setText(currently.getTemperature() + "");
        tvSummary.setText(currently.getSummary());
        imgIcon.setImageResource(currently.getIconId());
    }

    @Override
    public void showDailyWeather(final Daily daily) {
        forecastAdapter.setDailyData(daily.getDailies());

        ItemClickSupport.addTo(rvWeather).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, android.view.View v) {
                DetailActivity.startActivity(MainActivity.this, daily.getDailies().get(position));
            }
        });
    }

    @Override
    public void showRecomendationResult(final ArrayList<String> locations) {
        flSuggestion.setVisibility(View.VISIBLE);
        suggestionsAdapter.setSuggestions(locations);

        ItemClickSupport.addTo(rvSuggestion).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, android.view.View v) {
                Toast.makeText(MainActivity.this, locations.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
