package com.training.leos.weatherforecast.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.leos.weatherforecast.R;
import com.training.leos.weatherforecast.data.model.Data;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private static final String TAG = "ListViewForecastAdapter";
    private Context context;
    private ArrayList<Data> data = new ArrayList<>();

    public ArrayList<Data> getDailyData() {
        return data;
    }

    public void setDailyData(ArrayList<Data> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ForecastAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_weather, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Data daily = getDailyData().get(position);

        holder.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, daily.getIconId()));
        holder.tvDay.setText(daily.getFormatedDay());
        holder.tvTemperature.setText(daily.getTemperature() + "");
    }

    @Override
    public int getItemCount() {
        return getDailyData().size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvDay;
        private TextView tvTemperature;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.iv_item_icon);
            tvDay = (TextView) itemView.findViewById(R.id.tv_item_day);
            tvTemperature = (TextView) itemView.findViewById(R.id.tv_item_temperature);
        }
    }
}
