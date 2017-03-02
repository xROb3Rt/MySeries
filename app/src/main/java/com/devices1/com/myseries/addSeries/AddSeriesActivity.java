package com.devices1.com.myseries.addSeries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devices1.com.myseries.R;
import com.devices1.com.myseries.mySeries.IMySeriesView;

public class AddSeriesActivity extends AppCompatActivity implements IAddSeriesView {

    public static final String SERIES_TITLE = "seriesTitle";
    private ListView series_list;
    private ProgressBar progress_bar;
    private AddSeriesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        series_list = (ListView) findViewById(R.id.listViewSeries);
        series_list.setEmptyView(findViewById(R.id.not_found_text));
        progress_bar = (ProgressBar) findViewById(R.id.progressBar);

        presenter = new AddSeriesPresenter();

        Intent intent = getIntent();
        String title = intent.getStringExtra(SERIES_TITLE);

        presenter.setSearchedTitle(title);

    }
}
