package com.devices1.com.myseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.devices1.com.myseries.addSeries.AddSeriesActivity;
import com.devices1.com.myseries.model.SeriesModel;
import com.devices1.com.myseries.mySeries.IMySeriesView;
import com.devices1.com.myseries.mySeries.MySeriesPresenter;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_series, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNameChosen(String name) {
        presenter.onAddSeriesRequested(name);
    }

    @Override
    public void switchToAddSeries(String name) {
        // Create the Intent
        Intent intent = new Intent(this, AddSeriesActivity.class);
        // Add the parameters to AddSeriesActivity
        intent.putExtra(AddSeriesActivity.SERIES_TITLE, name);
        // Start the activity:
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewRestarted();
    }
}
