package com.devices1.com.myseries.series;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.devices1.com.myseries.R;


public class EpisodeRowAdapter extends ArrayAdapter {

    private final IViewedChanged listener;
    public interface IViewedChanged{
        void onViewedChanged(int episode, boolean viewed);
    }

    public EpisodeRowAdapter(Context context, EpisodeData[] rows, IViewedChanged listener) {
        super(context, R.layout.episode_row, R.id.title_view, rows);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        View view;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.episode_row, parent, false);
        }else{
            view = convertView;
        }
        EpisodeData episodeData = (EpisodeData)getItem(position);
        TextView titleView =  (TextView) view.findViewById(R.id.title_view);
        titleView.setText(episodeData.getTitle());
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.viewed_checkbox);
        checkBox.setChecked(episodeData.getViewed());

        return  view;     }
}
