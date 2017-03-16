package com.devices1.com.myseries.mySeries;

import android.content.Context;

import com.devices1.com.myseries.model.ISeriesModel;

public class MySeriesPresenter {

    IMySeriesView view;
    ISeriesModel model;

    public MySeriesPresenter(IMySeriesView view, ISeriesModel model) {
        this.view = view;
        this.model = model;
    }

    public void onAddSeriesRequested(String name) {

        view.switchToAddSeries(name);

    }


    public void onViewRestarted() {

        model.getAllSeries();


    }
}
