package com.devices1.com.myseries.mySeries;

public class MySeriesPresenter {

    IMySeriesView view;

    public MySeriesPresenter(IMySeriesView view) {
        this.view = view;
    }

    public void onAddSeriesRequested(String name) {

        view.switchToAddSeries(name);

    }
}
