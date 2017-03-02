package com.devices1.com.myseries.addSeries;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.devices1.com.myseries.R;
import com.devices1.com.myseries.model.SeriesData;
import com.devices1.com.myseries.model.SeriesModel;
import com.devices1.com.myseries.mySeries.IMySeriesView;

import java.util.List;

public class AddSeriesActivity extends AppCompatActivity implements IAddSeriesView {

    public static final String SERIES_TITLE = "seriesTitle";
    private ListView series_list;
    private ProgressBar progress_bar;
    private AddSeriesPresenter presenter;
    private TextView notFoundText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        series_list = (ListView) findViewById(R.id.listViewSeries);
        series_list.setEmptyView(findViewById(R.id.not_found_text));
        progress_bar = (ProgressBar) findViewById(R.id.progressBar);
        notFoundText = (TextView) findViewById(R.id.not_found_text);

        SeriesModel model = SeriesModel.getInstance(this);
        presenter = new AddSeriesPresenter(this, model);


        Intent intent = getIntent();
        String title = intent.getStringExtra(SERIES_TITLE);

        presenter.setSearchedTitle(title);

    }

    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    public void showSearchInProgress() {
        series_list.setVisibility(View.GONE);
        notFoundText.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideSearchInProgress() {
        series_list.setVisibility(View.VISIBLE);
        notFoundText.setVisibility(View.GONE);
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        series_list.setVisibility(View.GONE);
        notFoundText.setVisibility(View.VISIBLE);
        progress_bar.setVisibility(View.GONE);
    }

    /*@Override
    public void seriesFound(List<SeriesData> series) {


    }*/

}
