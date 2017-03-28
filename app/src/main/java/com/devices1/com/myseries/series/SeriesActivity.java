package com.devices1.com.myseries.series;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.devices1.com.myseries.R;
import com.devices1.com.myseries.model.SeriesModel;
import com.devices1.com.myseries.seriesDetail.SeriesDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity implements ISeriesView {

    public static final String SERIE_ID = "serieID";

    private ListView listEpisodes;
    private ProgressBar progressBar;
    private Spinner spinnerSeason;
    private TextView noEpisodes;
    private TextView noSeason;
    private List<String> titles = new ArrayList<>();
    private List<EpisodeData> episodeData;


    private SeriesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerSeason = (Spinner) findViewById(R.id.spinnerSeason);
        listEpisodes = (ListView) findViewById(R.id.listViewEpisodes);
        listEpisodes.setEmptyView(findViewById(R.id.textNoEpisodes));
        progressBar = (ProgressBar) findViewById(R.id.progressBarSeries);
        noEpisodes = (TextView) findViewById(R.id.textNoEpisodes);
        noSeason = (TextView) findViewById(R.id.textNoSeason);

        SeriesModel model = SeriesModel.getInstance(this);
        presenter = new SeriesPresenter(this, model);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddSeasonRequested();
            }
        });

        spinnerSeason.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void
                    onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        presenter.setSeason(i + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        Intent intent = getIntent();
        int serieID = intent.getIntExtra(SERIE_ID, 0);
        presenter.setSeries(serieID);

    }

    @Override
    public void showTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    public void showSeasons(Integer numberSeasons) {

        if(numberSeasons == 0){
            spinnerSeason.setVisibility(View.GONE);
            listEpisodes.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            noEpisodes.setVisibility(View.GONE);
            noSeason.setVisibility(View.VISIBLE);
        }
        else{
            spinnerSeason.setVisibility(View.VISIBLE);
            listEpisodes.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            noEpisodes.setVisibility(View.GONE);
            noSeason.setVisibility(View.GONE);


            }

        List<String> seasons = new ArrayList<>();
        for (int i = 0; i< numberSeasons; i++){
            seasons.add("Season " + i+1);

            SpinnerAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, seasons);
            spinnerSeason.setAdapter(adapter);

        }
    }

    @Override
    public void showEpisodes(EpisodeData[] episodeData) {

        EpisodeRowAdapter episodeRowAdapter = new EpisodeRowAdapter(getApplicationContext(), episodeData, new EpisodeRowAdapter.IViewedChanged()
        {
            @Override
            public void onViewedChanged(int episode, boolean viewed) {
                presenter.onEpisodeViewedChanged(episode, viewed);
            }
        });

    }

    @Override
    public void hideSearchInProgress(){
        spinnerSeason.setVisibility(View.VISIBLE);
        listEpisodes.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        noEpisodes.setVisibility(View.GONE);
        noSeason.setVisibility(View.GONE);
    }

    @Override
    public void showSearchInProgress(){
        progressBar.setVisibility(View.VISIBLE);
        spinnerSeason.setVisibility(View.GONE);
        listEpisodes.setVisibility(View.GONE);
        noEpisodes.setVisibility(View.GONE);
        noSeason.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_series), error, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_series,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_details:
                presenter.onDetailsRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void switchToSeriesDetails(int currentSerie) {
        int id = currentSerie;
        Intent intent = new Intent(this, SeriesDetailActivity.class);
        intent.putExtra(SeriesDetailActivity.SERIES_ID, id);
        startActivity(intent);
    }



}
