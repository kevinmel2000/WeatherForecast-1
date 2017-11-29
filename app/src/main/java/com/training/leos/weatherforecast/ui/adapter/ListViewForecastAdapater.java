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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leo on 29/10/2017.
 */

public class ListViewForecastAdapater extends RecyclerView.Adapter<ListViewForecastAdapater.ListViewHolder>{
    private Context context;
    private ArrayList<Data> data = new ArrayList<>();

    public ArrayList<Data> getDailyData() {
        return data;
    }

    public void setDailyData(ArrayList<Data> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ListViewForecastAdapater(Context context){
        this.context = context;
    }
    @Override
    public ListViewForecastAdapater.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_weather, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Data daily = getDailyData().get(position);
        holder.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, daily.getIconId()));
        holder.tvDay.setText(daily.getFormatedDay());
        holder.tvTemperature.setText(daily.getTemperature() + "");
    }

    @Override
    public int getItemCount() {
        return getDailyData().size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_icon) ImageView imgIcon;
        @BindView(R.id.tv_item_day) TextView tvDay;
        @BindView(R.id.tv_item_temperature) TextView tvTemperature;
        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
