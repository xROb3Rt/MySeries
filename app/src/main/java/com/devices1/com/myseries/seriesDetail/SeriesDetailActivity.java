package com.devices1.com.myseries.seriesDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.devices1.com.myseries.R;
import com.devices1.com.myseries.model.SeriesModel;


public class SeriesDetailActivity extends AppCompatActivity implements ISeriesDetailView {

    public static final String SERIES_ID = "MyID";
    private SeriesDetailPresenter presenter;

    TextView textViewSummary;
    TextView textViewFirstAired;
    TextView textViewNetwork;
    TextView textViewStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);

        textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewFirstAired = (TextView) findViewById(R.id.textViewFirstAired);
        textViewNetwork = (TextView) findViewById(R.id.textViewNetwork);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        presenter = new SeriesDetailPresenter(this, SeriesModel.getInstance(this));
        Intent intent = getIntent();
        int id = intent.getIntExtra(SERIES_ID,0);
        presenter.setSeries(id);
    }
    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }
    @Override
    public void showSummary(String summary) {
        textViewSummary.setText(summary);
    }
    @Override
    public void showFirstAired(String firstAired) {
        textViewFirstAired.setText(firstAired);
    }
    @Override
    public void showNetwork(String network) {
        textViewNetwork.setText(network);
    }
    @Override
    public void showStatus(String status) {
        textViewStatus.setText(status);
    }

}
