package com.training.leos.weatherforecast.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.leos.weatherforecast.R;

import java.util.ArrayList;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder> {
    private static final String TAG = "SuggestionAdapter";
    private Context context;
    private ArrayList<String> suggestions = new ArrayList<>();

    public ArrayList<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<String> suggestions) {
        Log.w(TAG, "setSuggestions: ");
        this.suggestions = suggestions;
        notifyDataSetChanged();
    }

    public SuggestionsAdapter(Context context) {
        Log.w(TAG, "SuggestionsAdapter: ");
        this.context = context;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.w(TAG, "onCreateViewHolder: ");
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {
        Log.w(TAG, "onBindViewHolder: " + getSuggestions().toString());
        holder.tvSuggestionName.setText(getSuggestions().get(position));
    }

    @Override
    public int getItemCount() {
        return getSuggestions().size();
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSuggestionName;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
            tvSuggestionName = (TextView) itemView.findViewById(R.id.tv_item_suggestion_location);
        }
    }
}
