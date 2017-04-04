package com.devices1.com.myseries.mySeries;


import com.devices1.com.myseries.model.ISeriesModel;

import java.util.ArrayList;
import java.util.List;

public class MySeriesPresenter {

    IMySeriesView view;
    ISeriesModel model;

    private List<Integer> seriesIds;


    public MySeriesPresenter(IMySeriesView view, ISeriesModel model) {
        this.view = view;
        this.model = model;
    }

    public void onAddSeriesRequested(String name) {

        view.switchToAddSeries(name);

    }


    public void onViewRestarted() {

        seriesIds = model.getAllSeries();
        List<String> titles = new ArrayList<>();
        for (int id:seriesIds){
            titles.add(model.getSeriesTitle(id));
        }
        view.displayTitles(titles);
    }

    public void onViewSeriesRequested(int i){

        Integer index = seriesIds.get(i);
        view.switchToSeries(index);
    }


}
