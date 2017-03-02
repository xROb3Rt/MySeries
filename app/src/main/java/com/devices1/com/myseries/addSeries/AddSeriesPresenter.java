package com.devices1.com.myseries.addSeries;


import com.devices1.com.myseries.model.ISeriesModel;
import com.devices1.com.myseries.model.SeriesData;
import com.devices1.com.myseries.model.network.ResponseReceiver;

import java.util.List;

public class AddSeriesPresenter {

    private String searchedTitle;
    private IAddSeriesView view;
    private ISeriesModel model;


    public AddSeriesPresenter(IAddSeriesView view, ISeriesModel model) {
        this.view = view;
        this.model = model;
    }

    public void setSearchedTitle(String searchedTitle) {
        this.searchedTitle = searchedTitle;
        view.showTitle(searchedTitle);
        view.showSearchInProgress();
        model.findSeries(searchedTitle,
                new ResponseReceiver<List<SeriesData>>() {
                    @Override
                    public void onResponseReceived(List<SeriesData> series) {
                        view.hideSearchInProgress();
                        //seriesFound(series);
                    }

                    @Override
                    public void onErrorReceived(String message) {
                        view.hideSearchInProgress();
                        view.showError(message);
                        }
                });

    }




}
