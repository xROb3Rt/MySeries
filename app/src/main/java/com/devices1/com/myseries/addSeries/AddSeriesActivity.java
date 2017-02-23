package com.devices1.com.myseries.addSeries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devices1.com.myseries.R;
import com.devices1.com.myseries.mySeries.IMySeriesView;

public class AddSeriesActivity extends AppCompatActivity {

    public static final String SERIES_TITLE = "seriesTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);
    }
}
