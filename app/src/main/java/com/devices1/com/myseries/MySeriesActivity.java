package com.devices1.com.myseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.devices1.com.myseries.addSeries.AddSeriesActivity;
import com.devices1.com.myseries.model.SeriesModel;
import com.devices1.com.myseries.mySeries.IMySeriesView;
import com.devices1.com.myseries.mySeries.MySeriesPresenter;
import com.devices1.com.myseries.series.SeriesActivity;

import java.util.List;

public class MySeriesActivity extends AppCompatActivity implements AskNameDialog.INameListener, IMySeriesView {

    private MySeriesPresenter presenter;
    private ListView seriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        seriesList = (ListView) findViewById(R.id.listViewSeries);
        seriesList.setEmptyView(findViewById(R.id.textNoStored));

        SeriesModel model = SeriesModel.getInstance(this);
        presenter = new MySeriesPresenter(this, model);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskNameDialog dialog = new AskNameDialog();
                dialog.show(getFragmentManager(), "AskName");
            }
        });

    }

    @Override
    public void onNameChosen(String name) {
        presenter.onAddSeriesRequested(name);
    }

    @Override
    public void switchToAddSeries(String name) {

        Intent intent = new Intent(this, AddSeriesActivity.class);
        intent.putExtra(AddSeriesActivity.SERIES_TITLE, name);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewRestarted();
    }

    @Override
    public void displayTitles(List<String> titleList){
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList);

        seriesList.setAdapter(adapter);
        seriesList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void
                        onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            presenter.onViewSeriesRequested(i);
                        }
                    });
        }


    @Override
    public void switchToSeries(Integer index){
        Intent intent = new Intent(this, SeriesActivity.class);
        intent.putExtra(SeriesActivity.SERIE_ID,index);
        startActivity(intent);
    }
}
